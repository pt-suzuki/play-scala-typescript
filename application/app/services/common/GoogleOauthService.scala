package services.common

package services


import javax.inject.Inject
import models.GoogleOauthConfig
import play.api.libs.ws.WSClient
import play.api.mvc.{AnyContent, Request}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class GoogleOauthService @Inject()(
                                    config: GoogleOauthConfig,
                                     wSClient: WSClient
                                  )(implicit ec: ExecutionContext){

  def createRequestApiUrl: String = {
    s"${config.apiUrl}?" +
      s"client_id=${config.clientId}" +
      s"&response_type=${config.responseType}" +
      s"&scope=${config.scope}" +
      s"&redirect_uri=${config.redirectUri}"
  }

  def createRequestTokenInfoApiUrl(id_token: String) : String = {
    s"${config.tokenInfoEndpoint}?" +
      s"id_token=${id_token}"
  }

  def auth(request: Request[AnyContent]): Future[Boolean] = {
    /*for {
      //_       <- confirmState(request)
      code    <- obtainCode(request)
      idToken <- exchangeCodeForToken(code)
      id   <- obtainEmail(idToken)
      //bool    <- existsUser(id)
      bool <- true
    } yield bool*/
    Future(true)
  }


  /** sessionに保存したstateとparameterに付与したstateの値が一致するか確認する
    *
    * @param request
    * @return Success(true)  stateの値が一致する場合
    *         Success(false) stateの値が一致しない場合
    */
  private def confirmState(request: Request[AnyContent]): Future[Boolean] = {
    {
      println(request.getQueryString("state"))
      println(request.session.get("state"))
      for {
        paramState   <- request.getQueryString("state")
        sessionState <- request.session.get("state")
      } yield paramState == sessionState
    } match {
      case Some(s) => Future.successful(s)
      case None    => Future.failed(new Exception("state do not match"))
    }
  }

  /** codeパラメータを取得する
    *
    * @param request
    * @return Success(code) 取得したcodeパラメータの値
    *         Failure       codeパラメータが取得できない場合
    */
  private def obtainCode(request: Request[AnyContent]): Future[String] = {
    request.getQueryString("code") match {
      case Some(c) => Future.successful(c)
      case None    => Future.failed(new Exception("code is not found"))
    }
  }

  /** codeからidTokenを取得する
    *
    * @param code
    * @return
    */
  private def exchangeCodeForToken(code: String): Future[String] = {
    Try {
      val request =
        wSClient
          .url(config.tokenEndpoint)
          .withHttpHeaders("Accept" -> "application/json")
          .withRequestTimeout(config.requestTimeOut.millis)
      val body = Map(
        "code" -> Seq(code),
        "client_id" -> Seq(config.clientId),
        "client_secret" -> Seq(config.clientSecret),
        "redirect_uri" -> Seq(config.redirectUri),
        "grant_type" -> Seq(config.grantType)
      )

      val f = request.post(body)
      Await.ready(f, Duration.Inf)
      f.value.get
    } match {
      case Success(res) => Future.successful((res.get.json \ "id_token").as[String])
      case Failure(e) => Future.failed(e)
    }
  }

  /** idTokenからidを取得する
    *
    * @param idToken
    * @return
    */
  private def obtainEmail(idToken: String): Future[String] = {
    Try{
      val request =
        wSClient
          .url(createRequestTokenInfoApiUrl(idToken))
          .withHttpHeaders("Accept" -> "application/json")
          .withRequestTimeout(config.requestTimeOut.millis)
      val f = request.get()
      Await.ready(f, Duration.Inf)
      println(f.value.get.get.json)
      f.value.get
    }match {
      case Success(res) => Future.successful((res.get.json \ "user_id").as[String])
      case Failure(e) => Future.failed(new Exception("failed to obtain id", e))
    }
  }

  /* EmailがDBに登録されているか確認する
    *
    * @param auth_id
    * @return true   ユーザがDBに存在する場合
    *         false  ユーザがDBに存在しない場合
    */
  /*private def existsUser(auth_id: String) : Future[Boolean]= {
    println(auth_id)

    adminUserDataStore.findByAuthId(auth_id,"3").map {result =>
      result match {
        case Success(admin_user) => admin_user match {
          case Some(u) =>
            println(s"user found. id = ${u.id}, name = ${u.`type`}")
            true
          case None => false
        }
        case Failure(e) => false
      }
    }
  }*/
}

