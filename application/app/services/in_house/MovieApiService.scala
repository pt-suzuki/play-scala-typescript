package services.in_house

import data_store.in_house.SampleDataStore
import javax.inject.Inject
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

class MovieApiService @Inject()(data_store:SampleDataStore)(implicit ec: ExecutionContext){
  def getSearchResultJson(criteriaMap: Map[String,Any]) :Future[JsValue] =  {
    val resultingMovies = data_store.getSearchResult(criteriaMap)

    resultingMovies.map{ result =>
      Json.toJson(Json.obj("result" -> result.map{ movie =>
          (Json.parse(movie.jsonText).as[JsObject]
            + ("id" -> Json.toJson(movie.id.get.toString()))
            + ("created_at" -> Json.toJson(movie.createdAt.get.toString("yyyy/MM/dd HH:mm:ss")))
            + ("updated_at" -> Json.toJson(movie.updatedAt.get.toString("yyyy/MM/dd HH:mm:ss")))
            )
      }))
    }
  }

  def getContentJson(id :Int) :Future[JsValue] =  {
    val resultingMovies = data_store.getContent(id)

    resultingMovies.right.get.map{ result =>
      Json.toJson(Json.obj("result" -> result.map{ movie =>
        (Json.parse(movie.jsonText).as[JsObject]
          + ("id" -> Json.toJson(movie.id.get.toString()))
          + ("created_at" -> Json.toJson(movie.createdAt.get.toString("yyyy/MM/dd HH:mm:ss")))
          + ("updated_at" -> Json.toJson(movie.updatedAt.get.toString("yyyy/MM/dd HH:mm:ss")))
          )
      }))
    }
  }
}
