package com.aimprosoft.slick2.persistence

import com.aimprosoft.slick2.config.Config.driver.simple._
import scala.language.reflectiveCalls

//abstract persistence trait
trait Persistence[T, ID] {

  def exists(id: ID)(implicit session: Session): Boolean

  def get(id: ID)(implicit session: Session): Option[T]

  def list(implicit session: Session): Seq[T]

  def list(startRow: Int = -1, pageSize: Int = -1)(implicit session: Session): Seq[T]

  def insert(entity: T)(implicit session: Session): ID

  def insertAll(entities: T*)(implicit session: Session): Unit

  def update(entity: T)(implicit session: Session): Unit

  def delete(id: ID)(implicit session: Session): Unit

  def count(implicit session: Session): Int
}

//abstract table entity
abstract class SlickBaseTable[T, ID](tag: Tag, tableName: String) extends Table[T](tag, tableName){
  def id: Column[ID]
}

abstract class SlickBasePersistence[T <: {val id: Option[ID]}, ID: BaseColumnType, TQ <: SlickBaseTable[T, ID]]
  extends Persistence[T, ID] {

  //Macro expansion value
  val tableQuery: TableQuery[TQ] /* = TableQuery[TQ] */

  //helper methods
  def byId(id: ID)(implicit session: Session): Query[TQ, T] = tableQuery.filter(_.id === id)

  def byId(idOpt: Option[ID])(implicit session: Session): Query[TQ, T] = {
    idOpt match {
      case Some(id) => byId(id)
      case _ => throw new IllegalArgumentException("ID option should not be None")
    }
  }

  protected def autoInc = tableQuery returning tableQuery.map(_.id)

  //base methods
  def exists(id: ID)(implicit session: Session): Boolean = byId(id).length.run > 0

  def get(id: ID)(implicit session: Session): Option[T] = byId(id).firstOption

  def list(implicit session: Session): Seq[T] = {
    list(-1, -1)
  }

  def list(startRow: Int, pageSize: Int)(implicit session: Session): Seq[T] = {
    //create query for retrieving of all entities
    var q = tableQuery.map(e => e)

    //if it needs to be started from certain row
    if (startRow > 0) {
      q = q.drop(startRow)
    }

    //if we need to get only certain number of rows
    if (pageSize >= 0) {
      q = q.take(pageSize)
    }

    //perform query
    q.list()
  }

  def insert(entity: T)(implicit session: Session): ID = {
    autoInc.insert(entity)
  }

  def insertAll(entities: T*)(implicit session: Session) {
    autoInc.insertAll(entities: _*)
  }

  def update(entity: T)(implicit session: Session) {
    byId(entity.id).update(entity)
  }

  def delete(id: ID)(implicit session: Session) {
    byId(id).delete
  }

  def count(implicit session: Session): Int = {
    tableQuery.length.run
  }

}