package models

import play.api.libs.json._

object JsonText{
  implicit val writes: Writes[JsonText] = new Writes[JsonText]{
    override def writes(o: JsonText): JsValue = {
      val propsJs = JsObject(
        o.props.map{kvp =>
          kvp._1 -> (kvp._2 match {
            case x: String => JsString(x)
            case x: Int => JsNumber(x)
            case _ => JsNull // Do whatever you want here.
          })
        }
      )
      JsObject(Map("item" -> propsJs))
    }
  }
}

case class JsonText(props :Map[String,Any])
