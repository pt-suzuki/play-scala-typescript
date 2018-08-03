package controllers.api.in_house

import enums.{HealthCareField, SampleField, MiningBaseCriteria, ReceiptField}
import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc._


class EnumsApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def mining_base_criteria = Action{
    val result = MiningBaseCriteria.values
    Ok(Json.toJson(result))
  }

  def mining_criteria = Action{request =>
    val map = request.queryString.map { case (k,v) => k -> v.mkString }
    val base_id = map.get("base_id");

    base_id match{
      case Some("1")  =>Ok(Json.toJson(SampleField.values))
      case _  => Ok(Json.toJson(SampleField.values))
    }
  }

}
