package genarator

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import slick.codegen.SourceCodeGenerator
import slick.model.Model


class SlickModelGeneratorImpl(model: Model, idColumnNameSet: Set[String]) extends SourceCodeGenerator(model) {

  def implicitIdMapper(name: String): String = {
    val idName = s"${name.toCamelCase}"
    val uncapitalizedIdName = idName.head.toLower + idName.tail
    s"implicit val ${uncapitalizedIdName}Mapper = MappedColumnType.base[${idName}, Int](_.value, ${idName}.apply)"
  }

  // add some custom imports
  override def code =

    s"""|import com.github.tototoshi.slick.MySQLJodaSupport._
        |import org.joda.time.DateTime
        |
       |${(idColumnNameSet.map(implicitIdMapper)).mkString("\n")}
        |
       |""".stripMargin +
      super.code


  override def Table = new Table(_) {
    override def autoIncLast = true

    override def Column = new Column(_) {
      override def asOption = autoInc
      override def rawType = model.tpe match {
        case "java.sql.Timestamp" => "DateTime"
        case "java.sql.Date" => "DateTime"
        case _ if idColumnNameSet.contains(model.name) => model.name.toCamelCase
        case _ if model.name == "id" => s"""${TableValue.name}Id"""
        case _ => super.rawType
      }

      override def default = rawName match {
        case "createdAt" | "updatedAt" => Some("Some(DateTime.now)")
        case _ => super.default
      }
    }


    class EntityCompanionDef extends EntityTypeDef {
      override def doc = ""

      override def code =
        s"""object ${rawName} {
           |  import play.api.libs.json._
           |  import custom_modules.JodaWrites
           |  import custom_modules.JodaReads
           |
           |  val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
           |
           |  implicit val dateTimeWriter = JodaWrites.jodaDateWrites(dateFormat)
           |  implicit val dateTimeJsReader = JodaReads.jodaDateReads(dateFormat)
           |}
           |""".stripMargin
    }

    override def definitions = {
      val companion = new EntityCompanionDef
      Seq[Def](EntityType, companion, PlainSqlMapper, TableClass, TableValue)
    }

    override def code = {
      super.code.toList.map(_.replaceAll(s"${EntityType.name}.tupled", s"(${EntityType.name}.apply _).tupled"))
    }
  }
}


class CustomIDGenerator(model: Model) extends SourceCodeGenerator(model) {

  override def code = tables.map(_.code.mkString("\n")).mkString("\n\n")

  override def packageCode(profile: String, pkg: String, container: String, parentType: Option[String]): String =
    s"""package models
       |
       |import play.api.libs.json._
       |
       |${code}
       |""".stripMargin

  override def Table = new Table(_) {

    class IDDef extends EntityTypeDef {
      override def doc = ""

      override def code = {
        val name = TableValue.name
        val idName = s"""${name}Id"""
        val packageName = "models"
        val idType = "Int"

        s"""class ${idName} private (private[${packageName}] val value: ${idType}) extends AnyVal {
           |  override def toString = value.toString
           |}
           |object ${idName} {
           |  private[models] def apply(value: ${idType}) = new ${idName}(value)
           |  private[models] def unapply(id: ${idName}) = Some(id.value)
           |  implicit val jsonWrites = Json.writes[${idName}]
           |  implicit val jsonReads = Json.reads[${idName}]
           |  def fromString(str: String): Either[Throwable, ${idName}] = {
           |    try {
           |      Right(${idName}(str.to${idType}))
           |    } catch {
           |      case e: Throwable => Left(e)
           |    }
           |  }
           |}
           |""".stripMargin
      }
    }

    override def definitions = {
      Seq[Def](new IDDef)
    }
  }
}

class PathBindableGenerator(model: Model) extends SourceCodeGenerator(model) {

  override def code = tables.map(_.code.mkString("\n")).mkString("\n\n")

  override def packageCode(profile: String, pkg: String, container: String, parentType: Option[String]): String =
    s"""package models
       |
       |import play.api.mvc.PathBindable
       |
       |object ${container} {
       |  ${indent(code)}
       |}
       |""".stripMargin

  override def Table = new Table(_) {

    class PathBindamleDef extends EntityTypeDef {
      override def doc = ""

      override def code = {
        val name = TableValue.name
        val idName = s"""${name}Id"""
        val uncapitalizedIdName = idName.head.toLower + idName.tail
        val implicitName = s"""${uncapitalizedIdName}PathBindable"""

        val packageName = "models"

        s"""implicit def ${implicitName} = new PathBindable[${idName}] {
           |  override def bind(key: String, value: String): Either[String, ${idName}] = {
           |    ${idName}.fromString(value).left.map(_.getMessage)
           |  }
           |  override def unbind(key: String, ${uncapitalizedIdName}: ${idName}): String = {
           |    ${uncapitalizedIdName}.toString
           |  }
           |}
           |""".stripMargin
      }
    }

    override def definitions = {
      Seq[Def](new PathBindamleDef)
    }
  }
}