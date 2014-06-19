package com.aimprosoft.slick2.service

import com.aimprosoft.slick2.model.{User, Glossary}
import com.aimprosoft.slick2.service.impl.{UserServiceImpl, GlossaryServiceImpl}
import scala.language.reflectiveCalls

trait BaseCrudService[T <: {val id: Option[ID]}, ID] {

  def list(startRow: Int = 0, pageSize: Int = -1): Seq[T]

  def exists(id: ID): Boolean

  def count: Int

  def getFirst: Option[T]

  def getById(id: ID): Option[T]

  def add(entity: T): Unit

  def update(entity: T): Unit

  def remove(entity: T): Unit

  def removeById(id: ID): Unit
}

trait GlossaryService extends BaseCrudService[Glossary, Long]

trait UserService extends BaseCrudService[User, Long]{

  def getByEmail(username: String): Option[User]

  def countByRole(role: String): Int

}

object GlossaryService extends GlossaryServiceImpl

object UserService extends UserServiceImpl