package enums

import play.api.libs.json._

object SampleField {
  case object IMPLEMENTATION_DATE extends SampleField(1,"健診実施年月日")
  case object AUTHORITY_NUMBER extends SampleField(2,"健診機関番号")
  case object AUTHORITY_NAME extends SampleField(3,"健診機関名称")

  val values = Array(IMPLEMENTATION_DATE,AUTHORITY_NUMBER,AUTHORITY_NAME)

  def valueOf(code:Int) : SampleField = {
    code match {
      case IMPLEMENTATION_DATE.code => IMPLEMENTATION_DATE
      case AUTHORITY_NUMBER.code => AUTHORITY_NUMBER
      case AUTHORITY_NAME.code => AUTHORITY_NAME
      case _ => IMPLEMENTATION_DATE
    }
  }

  implicit val format = Format[SampleField](Reads[SampleField]{
    case JsString("IMPLEMENTATION_DATE") => JsSuccess(IMPLEMENTATION_DATE)
    case JsString("AUTHORITY_NUMBER") => JsSuccess(AUTHORITY_NUMBER)
    case JsString("AUTHORITY_NAME") => JsSuccess(AUTHORITY_NAME)
    case _ => JsSuccess(IMPLEMENTATION_DATE)
  },new Writes[SampleField] {
    def writes(entity:SampleField) :JsValue ={
   Json.obj(
     "code" ->entity.code,
      "name" -> entity.name
   )
  }})
}

sealed abstract class SampleField(_code:Int, _name:String) {
  val code = _code
  val name = _name
}