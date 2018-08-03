package controllers.api.common

import javax.inject._
import play.api.mvc._
import services.common.services.GoogleOauthService

import scala.concurrent.ExecutionContext

class LoginLogoutController @Inject()
  (googleOauthService : GoogleOauthService)
  (cc: ControllerComponents)
  (implicit ec:ExecutionContext)
  extends AbstractController(cc){

  def login = Action {
    val requestApiUrl = googleOauthService.createRequestApiUrl
    //val state = new BigInteger(130, new SecureRandom()).toString(32)
    Redirect(requestApiUrl)
  }

  def googleOauth= Action.async { implicit request =>
    googleOauthService.auth(request).map {result =>
      if(result) Redirect("/")
      else BadRequest("user not found")
    }
  }

}
