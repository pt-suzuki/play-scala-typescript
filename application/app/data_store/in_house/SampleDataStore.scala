package data_store.in_house

import javax.inject.Inject
import models.{JsonText, MovieId}
import models.InHouseTables._
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json._
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class SampleDataStore @Inject()
    (@NamedDatabase("in_house") inHouseDbConfigProvider:DatabaseConfigProvider)
    (implicit ec: ExecutionContext) {
  val dbConfig = inHouseDbConfigProvider.get[JdbcProfile]

  def getSearchResult(criteriaMap: Map[String,Any])  =  {
    val query = for{
      movies <- criteriaMap.get("key").fold(
        Sample.joinLeft(SampleIndex).on(_.id === _.sampleId).result
      ) { key =>
        Sample
          .joinLeft(SampleIndex.filter{ t =>t.title like s"%${key}"}).on(_.id === _.sampleId)
          .result
      }
    } yield movies.map { case (f, s) =>
      f
    }

    dbConfig.db.run(query)
  }

  def getContent(id :Int) = {
    SampleId.fromString(id.toString).right map{ f =>
      val query = for{
        movie <- Sample.filter(_.id === f).result
      } yield movie
      dbConfig.db.run(query)
    }
  }

  def create(contentMap : Map[String,Any])  = {
    val transaction = (for{

      movieId <- Sample returning Sample.map(_.id) += SampleRow(
        Json.toJson(JsonText(contentMap)).toString()
      )

      _ <- SampleIndex returning SampleIndex.map(_.id) += SampleIndexRow(
        movieId.value,
        contentMap.get("title").toString(),
        DateTime.parse(contentMap.get("release_Date").toString()),
        DateTime.parse(contentMap.get("end_Date").toString())
      )
    } yield movieId).transactionally

    dbConfig.db.run(transaction)
  }

}





