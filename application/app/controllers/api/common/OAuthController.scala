package controllers.api.common

import java.sql.Date

import com.google.inject.Inject
import data_store.oauth.{AdminOauthAccessTokensDataStore, AdminOauthAuthorizationCodesDataStore, AdminOauthClientsDataStore, AdminUsersDataStore}
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import scalaoauth2.provider._
import models.InHouseTables._
import scalaoauth2.provider.OAuth2ProviderActionBuilders

import scala.concurrent.{ExecutionContext, Future}

class OAuthController  @Inject()(cc: ControllerComponents)
                                (adminUsersDataStore: AdminUsersDataStore,
                                 adminOauthAuthorizationCodesDataStore: AdminOauthAuthorizationCodesDataStore,
                                 adminOauthAccessTokensDataStore: AdminOauthAccessTokensDataStore,
                                 adminOauthClientsDataStore: AdminOauthClientsDataStore
                                ) (implicit ec: ExecutionContext)extends AbstractController(cc) with OAuth2Provider with OAuth2ProviderActionBuilders {

  implicit val authInfoWrites = new Writes[AuthInfo[AdminUsers#TableElementType]] {
    def writes(authInfo: AuthInfo[AdminUsers#TableElementType]) = {
      Json.obj(
        "account" -> Json.obj(
          "email" -> authInfo.user.email
        ),
        "clientId" -> authInfo.clientId,
        "redirectUri" -> authInfo.redirectUri
      )
    }
  }

  override val tokenEndpoint = new TokenEndpoint {
    override val handlers = Map(
      OAuthGrantType.AUTHORIZATION_CODE -> new AuthorizationCode(),
      OAuthGrantType.REFRESH_TOKEN -> new RefreshToken(),
      OAuthGrantType.CLIENT_CREDENTIALS -> new ClientCredentials(),
      OAuthGrantType.PASSWORD -> new Password()
    )
  }

  def accessToken = Action.async { implicit request =>
    issueAccessToken(new MyDataHandler())
  }

  def resources = AuthorizedAction[AdminUsers#TableElementType](new MyDataHandler()) {
    request =>
    Ok(Json.toJson(request.authInfo))
  }

  class MyDataHandler extends DataHandler[AdminUsers#TableElementType] {

    override def validateClient(maybeCredential: Option[ClientCredential], request: AuthorizationRequest): Future[Boolean] =  {
      maybeCredential.fold(Future.successful(false))(clientCredential =>
          adminOauthClientsDataStore.validate(clientCredential.clientId,
          clientCredential.clientSecret.getOrElse(""), request.grantType))
    }

    override def getStoredAccessToken(authInfo: AuthInfo[AdminUsers#TableElementType]): Future[Option[AccessToken]] = {
      adminOauthAccessTokensDataStore.findByAuthorized(authInfo.user.id.getOrElse(AdminUsers(0)), authInfo.clientId.getOrElse("")).map(_.map(toAccessToken))
    }

    private val accessTokenExpireSeconds = 3600
    private def toAccessToken(accessToken: AdminOauthAccessTokens#TableElementType) = {
      AccessToken(
        accessToken.accessToken,
        Some(accessToken.refreshToken),
        None,
        Some(accessTokenExpireSeconds),
        accessToken.createdAt.get.toDate
      )
    }
    override def createAccessToken(authInfo: AuthInfo[AdminUsers#TableElementType]): Future[AccessToken] = {
      authInfo.clientId.fold(Future.failed[AccessToken](new InvalidRequest()))
      { clientId =>
        (for {
          clientOpt <- adminOauthClientsDataStore.findByClientId(clientId)
          toAccessToken <-  adminOauthAccessTokensDataStore.create(authInfo.user.id.get, clientOpt.get.id.get).map(toAccessToken) if clientOpt.isDefined
        } yield toAccessToken).recover{case _ => throw new InvalidRequest()}
      }
    }



    override def findUser(maybeCredential: Option[ClientCredential], request: AuthorizationRequest): Future[Option[AdminUsers#TableElementType]] =
      request match {
        case request: PasswordRequest =>
          adminUsersDataStore.authenticate(request.username, request.password)
        case request: ClientCredentialsRequest =>
          maybeCredential.fold(Future.failed[Option[AdminUsers#TableElementType]](new InvalidRequest())){ clientCredential =>
            for {
              maybeAccount <- adminOauthClientsDataStore.findClientCredentials(
                clientCredential.clientId,
                clientCredential.clientSecret.getOrElse("")
              )
            } yield maybeAccount
          }
        case _ =>
          Future.successful(None)
      }

    override def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[AdminUsers#TableElementType]]] = {
      adminOauthAccessTokensDataStore.findByRefreshToken(refreshToken).flatMap {
        case Some(accessToken) =>
          for {
            account <- adminUsersDataStore.findById(accessToken.adminUsersId)
            client <-  adminOauthClientsDataStore.findById(accessToken.adminOauthClientsId)
          } yield {
            Option(AuthInfo(
              user = account.get,
              clientId = Some(client.get.clientId),
              scope = None,
              redirectUri = None
            ))
          }
        case None => Future.failed(new InvalidRequest())
      }
    }

    override def refreshAccessToken(authInfo: AuthInfo[AdminUsers#TableElementType], refreshToken: String): Future[AccessToken] = {
      authInfo.clientId.fold(Future.failed[AccessToken](new InvalidRequest()))
      { clientId => (for {
        clientOpt <- adminOauthClientsDataStore.findByClientId(clientId)
        toAccessToken <- adminOauthAccessTokensDataStore.refresh(authInfo.user.id.get, clientOpt.get.id.get).map(toAccessToken) if clientOpt.isDefined
      } yield toAccessToken).recover { case _ => throw new InvalidClient()}}
    }

    override def findAuthInfoByCode(code: String): Future[Option[AuthInfo[AdminUsers#TableElementType]]] = {
      adminOauthAuthorizationCodesDataStore.findByCode(code).flatMap {
        case Some(code) =>
          for {
            account <- adminUsersDataStore.findById(code.adminUsersId)
            client <-  adminOauthClientsDataStore.findById(code.adminOauthClientsId)
          } yield {
            Some(AuthInfo(
              user = account.get,
              clientId = Some(client.get.clientId),
              scope = None,
              redirectUri = None
            ))
          }
        case None => Future.failed(new InvalidRequest())
      }
    }

    override def deleteAuthCode(code: String): Future[Unit] = adminOauthAuthorizationCodesDataStore.delete(code).map( _ => {})

    override def findAccessToken(token: String): Future[Option[AccessToken]] =
      adminOauthAccessTokensDataStore.findByAccessToken(token).map(_.map(toAccessToken))

    override def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[AdminUsers#TableElementType]]] = {
      adminOauthAccessTokensDataStore.findByAccessToken(accessToken.token).flatMap {
        case Some(accessToken) =>
          for {
            account <- adminUsersDataStore.findById(accessToken.adminUsersId)
            client <-  adminOauthClientsDataStore.findById(accessToken.adminOauthClientsId)
          } yield {
            Some(AuthInfo(
              user = account.get,
              clientId = Some(client.get.clientId),
              scope = None,
              redirectUri = None
            ))
          }
        case None => Future.failed(new InvalidRequest())
      }
    }
  }
}
