package data_store.oauth

import java.security.SecureRandom

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import models.InHouseTables._
import models._
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

import slick.jdbc.MySQLProfile.api._

class AdminOauthAccessTokensDataStore  @Inject()(@NamedDatabase("in_house") inHouseDbConfigProvider:DatabaseConfigProvider)
                                         (adminOauthClientsDataStore: AdminOauthClientsDataStore)
                                         (implicit ec: ExecutionContext) {

  val dbConfig = inHouseDbConfigProvider.get[JdbcProfile]

  def create(adminUsersId: AdminUsersId, clientId: AdminOauthClientsId): Future[AdminOauthAccessTokens#TableElementType] = {
    def randomString(length: Int) = new Random(new SecureRandom()).alphanumeric.take(length).mkString
    val accessToken = randomString(40)
    val refreshToken = randomString(40)

    dbConfig.db.run(AdminOauthAccessTokens returning AdminOauthAccessTokens += AdminOauthAccessTokensRow(adminUsersId, clientId, accessToken, refreshToken))
  }

  def delete(adminUsersId: AdminUsersId, adminOauthClientsId: AdminOauthClientsId): Future[Int] = {
    dbConfig.db.run(AdminOauthAccessTokens.filter(client => client.adminUsersId == adminUsersId && client.adminOauthClientsId == adminOauthClientsId).delete)
  }

  def refresh(adminUserId: AdminUsersId, adminOauthClientsId: AdminOauthClientsId): Future[AdminOauthAccessTokens#TableElementType] = {
    delete(adminUserId, adminOauthClientsId)
    create(adminUserId, adminOauthClientsId)
  }

  def findByAuthorized(adminUsersId: AdminUsersId , clientId: String): Future[Option[AdminOauthAccessTokens#TableElementType]] = {
    val query = for {
        token <-  AdminOauthAccessTokens.join(AdminOauthClients).on(_.adminOauthClientsId === _.id)
          .filter(_._2.clientId == clientId).filter(_._2.adminUsersId == adminUsersId).result
    } yield token.map(_._1)
    dbConfig.db.run(query).map(_.headOption)
  }

  def findByAccessToken(accessToken: String): Future[Option[AdminOauthAccessTokens#TableElementType]] = {
    dbConfig.db.run(AdminOauthAccessTokens.filter(_.accessToken === accessToken).result).map(_.headOption)
  }

  def findByRefreshToken(refreshToken: String): Future[Option[AdminOauthAccessTokens#TableElementType]] = {
    val expireAt = new DateTime().minusMonths(1)
    dbConfig.db.run(AdminOauthAccessTokens.filter(_.refreshToken === refreshToken)
                          .filter(_.createdAt  == expireAt).result).map(_.headOption)
  }
}