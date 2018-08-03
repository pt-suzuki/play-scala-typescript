package data_store.oauth

/*
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.{CanBeQueryCondition, TableQuery}

import scala.concurrent.Future

import slick.jdbc.MySQLProfile.api._


trait AbstractBaseDAO[T,A,K] {
  def insert(row : A): Future[Long]
  def insert(rows : Seq[A]): Future[Seq[Long]]
  def update(row : K): Future[Int]
  def update(rows : Seq[A]): Future[Unit]
  def findById(id : K): Future[Option[A]]
  def findByFilter[C : CanBeQueryCondition](f: (T) => C): Future[Seq[A]]
  def deleteById(id : K): Future[Int]
  def deleteById(ids : Seq[K]): Future[Int]
  def deleteByFilter[C : CanBeQueryCondition](f:  (T) => C): Future[Int]
}

abstract class BaseDAO[T <: BaseTable[A], A <: BaseEntity, K <: ]()(implicit val tableQ: TableQuery[T]) extends AbstractBaseDAO[T,A,K] with HasDatabaseConfigProvider[JdbcProfile] {

  def insert(row : A): Future[Long] ={
    insert(Seq(row)).map(_.head)
  }

  def insert(rows : Seq[A]): Future[Seq[Long]] ={
    db.run(tableQ returning tableQ.map(_.id) ++= rows.filter(_.isValid))
  }

  def update(row : A): Future[Int] = {
    db.run(tableQ.filter(_.id === row.id).update(row))
  }

  def update(rows : Seq[A]): Future[Unit] = {
    db.run(DBIO.seq((rows.filter(_.isValid).map(r => tableQ.filter(_.id === r.id).update(r))): _*))
  }

  def findById(id : Long): Future[Option[A]] = {
    db.run(tableQ.filter(_.id === id).result.headOption)
  }

  def findByFilter[C : CanBeQueryCondition](f: (T) => C): Future[Seq[A]] = {
    db.run(tableQ.withFilter(f).result)
  }

  def deleteById(id : Long): Future[Int] = {
    deleteById(Seq(id))
  }

  def deleteById(ids : Seq[Long]): Future[Int] = {
    db.run(tableQ.filter(_.id.inSet(ids)).delete)
  }

  def deleteByFilter[C : CanBeQueryCondition](f:  (T) => C): Future[Int] = {
    db.run(tableQ.withFilter(f).delete)
  }

}
*/