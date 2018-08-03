package data_store.oauth

import com.google.inject.Inject
import models.{AdminOauthClientsId, AdminUsersId}
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import models.InHouseTables._

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

class AdminOauthClientsDataStore @Inject()
                                (@NamedDatabase("in_house") inHouseDbConfigProvider:DatabaseConfigProvider)
                                (adminUsersDataStore: AdminUsersDataStore)
                                (implicit ec: ExecutionContext)  {
  val dbConfig = inHouseDbConfigProvider.get[JdbcProfile]


  def validate(clientId: String, clientSecret: String, grantType: String): Future[Boolean] = {
    dbConfig.db.run(AdminOauthClients.filter(_.clientId == clientId).filter(_.clientSecret == clientSecret).result).map(
      _.headOption.map(_.grantType == grantType || grantType == "refresh_token").getOrElse(false))
  }

  def findClientCredentials(clientId: String, clientSecret: String): Future[Option[AdminUsers#TableElementType]] = {
    for {
      accountId <- dbConfig.db.run(AdminOauthClients.filter(oauthClient => oauthClient.clientId === clientId && oauthClient.clientSecret === clientSecret).result).map(_.headOption.map(_.adminUsersId))
      account <- adminUsersDataStore.findById(accountId.getOrElse(AdminUsersId(0)))
    } yield account
  }

  def findByClientId(clientId: String): Future[Option[AdminOauthClients#TableElementType]] = {
    dbConfig.db.run(AdminOauthClients.filter(_.clientId === clientId).result).map(_.headOption)
  }

  def findById(id: AdminOauthClientsId):Future[Option[AdminOauthClients#TableElementType]] ={
    dbConfig.db.run(AdminOauthClients.filter(_.id == id).result).map(_.headOption)
  }
}

