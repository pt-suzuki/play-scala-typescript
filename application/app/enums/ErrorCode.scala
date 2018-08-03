package enums

import enums.HealthCareField.{HEARTBEAT_NUMBER, STEPS, WEIGHT}
import play.api.libs.json._

object ErrorCode {
  case object BAD_REQUEST extends ErrorCode("001","不正なリクエストです")

  val values = Array(BAD_REQUEST,HEARTBEAT_NUMBER,WEIGHT)

  def valueOf(code:String) : ErrorCode = {
    code match {
      case BAD_REQUEST.code => BAD_REQUEST
      case _ => BAD_REQUEST
    }
  }

  implicit val format = Format[ErrorCode](Reads[ErrorCode]{
    case JsString("BAD_REQUEST") => JsSuccess(BAD_REQUEST)
    case _ => JsSuccess(BAD_REQUEST)
  },new Writes[ErrorCode] {
    def writes(entity:ErrorCode) :JsValue ={
      Json.obj(
        "code" ->entity.code,
        "message" -> entity.message
      )
    }})
}

sealed abstract class ErrorCode(_code:String, _message:String) {
  val code = _code
  val message = _message

  def messageJson : JsValue ={
    Json.toJson(Json.obj("status"->"error","code"->code,"message"->message))
  }
}
