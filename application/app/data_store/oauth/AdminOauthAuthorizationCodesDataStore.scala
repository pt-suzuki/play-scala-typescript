package data_store.oauth

import java.sql.Timestamp

import com.google.inject.Inject
import models.InHouseTables.AdminOauthAuthorizationCodes
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class AdminOauthAuthorizationCodesDataStore @Inject()
                                            (@NamedDatabase("in_house") inHouseDbConfigProvider:DatabaseConfigProvider)
                                            (implicit ec: ExecutionContext)  {
  val dbConfig = inHouseDbConfigProvider.get[JdbcProfile]

  def findByCode(code: String): Future[Option[AdminOauthAuthorizationCodes#TableElementType]] = {
    val expireAt = new Timestamp(new DateTime().minusMinutes(30).getMillis)
    dbConfig.db.run(AdminOauthAuthorizationCodes.filter(_.code == code).filter(_.createdAt == expireAt).result)
      .map(_.headOption)
  }

  def delete(code: String): Future[Int] = dbConfig.db.run(AdminOauthAuthorizationCodes.filter(_.code === code).delete)
}
