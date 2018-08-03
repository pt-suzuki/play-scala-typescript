package services.second

import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import com.github.tototoshi.play.json.generic._

import scala.concurrent.{ExecutionContext, Future}

class InjuriesAndDiseasesService @Inject()(data_store:InjuriesAndDiseasesDataStore)(implicit ec: ExecutionContext)
  extends HListWrites{
  def getSearchResultJson(criteriaMap:Map[String,Any]) :Future[JsValue] ={
    val resultInjuriesAndDisease = data_store.getSearchResult(criteriaMap)

    resultInjuriesAndDisease.map{result=>
      Json.obj("result"->Json.toJson(result))
    }
  }
}
