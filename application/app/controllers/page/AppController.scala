package controllers.page

import com.google.inject.Provider
import controllers.AssetsFinder
import javax.inject._
import play.api.Application
import play.api.mvc._

class AppController @Inject()(appProvider: Provider[Application], implicit val assetsFinder: AssetsFinder) extends InjectedController  {
  implicit def app: Application = appProvider.get()

  def index_param(name:String) = Action{implicit  request =>
    Ok(views.html.main())
  }

  def index = Action { implicit request =>
    Ok(views.html.main())
  }

  def error = Action {implicit  request =>
    BadRequest("不正なリクエスト")
  }

  def error_query(name:String) = Action {implicit  request =>
    BadRequest("不正なリクエスト")
  }
}
