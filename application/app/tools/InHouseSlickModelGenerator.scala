package tools

import genarator.{CustomIDGenerator, PathBindableGenerator, SlickModelGeneratorImpl}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object InHouseSlickModelGenerator extends App {
  // 接続先
  val url = "jdbc:mysql://54.248.173.224/kkapp?useSSL=false&nullNamePatternMatchesAll=true"
  // 出力するディレクトリ
  val outputDir = "app"
  // 出力するパッケージ
  val pkg = "models"
  // traitの名前
  val topTraitName = "Tables"
  // ファイル名
  val scalaFileName = "Tables.scala"
  // 生成するテーブルを指定、今回は全テーブルModelを作成するのでNone
  val tableNames: Option[Seq[String]] = None

  val slickProfile = "slick.jdbc.MySQLProfile"
  val profile = slick.jdbc.MySQLProfile
  val db = profile.api.Database.forURL(url, driver = "com.mysql.jdbc.Driver", user = "gitaadmin", password = "gitaadmin0#")

  import scala.concurrent.ExecutionContext.Implicits.global

  val modelFuture = db.run(profile.createModel(None, false))
  val f = modelFuture.map(model => {
    val idColumnNameSet = (for {
      t <- model.tables
    } yield s"${t.name.table}_id").toSet

    new SlickModelGeneratorImpl(model, idColumnNameSet).writeToFile(slickProfile, outputDir, pkg, "InHouseTables", "InHouseTables.scala")
    new CustomIDGenerator(model).writeToFile(slickProfile, outputDir, pkg, "InHouseIDs", "InHouseIDs.scala")
    new PathBindableGenerator(model).writeToFile(slickProfile, outputDir, pkg, "InHousePathBindableImplicits", "InHousePathBindableImplicits.scala")
  })

  Await.result(f, Duration.Inf)
}
