package controllers.api.in_house

import javax.inject._
import play.api.libs.json._
import play.api.mvc._

class SettingsApiController  @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def account_list_search = Action{
    val result = Map("id" -> "1", "name" -> "test")

    Ok(Json.toJson(result))
  }
}