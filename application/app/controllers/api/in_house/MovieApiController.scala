package controllers.api.in_house

import enums.ErrorCode
import javax.inject.Inject
import play.api.mvc._
import services.in_house.MovieApiService

import scala.concurrent.ExecutionContext

class MovieApiController @Inject()(cc: ControllerComponents)(service : MovieApiService)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {


  def search = Action.async {implicit request=>
    val criteriaMap = request.queryString.map { case (k,v) => k -> v.mkString }
    service.getSearchResultJson(criteriaMap).map{ result =>
      Ok(result)
    }
  }

  def show_detail(id : String) = Action.async{implicit  request =>
    service.getContentJson(id.toInt).map{result =>
     Ok(result)
    }
  }

  def create = Action{implicit request=>
    request.body.asJson.map{json =>
      Ok("test")
    }.getOrElse{
      BadRequest(ErrorCode.BAD_REQUEST.messageJson.toString())
    }
  }
}
