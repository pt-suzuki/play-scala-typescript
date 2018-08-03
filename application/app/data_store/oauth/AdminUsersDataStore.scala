package data_store.oauth

import java.security.MessageDigest

import com.google.inject.Inject
import models.AdminUsersId
import models.InHouseTables.AdminUsers
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

class AdminUsersDataStore @Inject()
                          (@NamedDatabase("in_house") inHouseDbConfigProvider:DatabaseConfigProvider)
                          (implicit ec: ExecutionContext)  {
  val dbConfig = inHouseDbConfigProvider.get[JdbcProfile]

  private def digestString(s: String): String = {
    val md = MessageDigest.getInstance("SHA-1")
    md.update(s.getBytes)
    md.digest.foldLeft("") { (s, b) =>
      s + "%02x".format(if (b < 0) b + 256 else b)
    }
  }

  def authenticate(email: String, password: String): Future[Option[AdminUsers#TableElementType]] = {
    val hashedPassword = digestString(password)
    dbConfig.db.run(AdminUsers.filter(_.password == hashedPassword).filter(_.email == email).result.headOption)
  }

  def findById(id:AdminUsersId): Future[Option[AdminUsers#TableElementType]] ={
    dbConfig.db.run(AdminUsers.filter(id == _.id).result).map(_.headOption)
  }
}
