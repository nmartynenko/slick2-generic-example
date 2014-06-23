package com.aimprosoft.slick2.service

import com.aimprosoft.slick2.model.{Glossary, User}
import com.aimprosoft.slick2.persistence._
import com.aimprosoft.slick2.service.impl.{GlossaryServiceImpl, UserServiceImpl}

import scala.language.reflectiveCalls

trait BaseCrudService[T <: Identifiable[ID], ID] {

  def list(startRow: Int = 0, pageSize: Int = -1): Seq[T]

  def exists(id: ID): Boolean

  def count: Int

  def getFirst: Option[T]

  def getById(id: ID): Option[T]

  def add(entity: T): ID

  def update(entity: T): Boolean

  def remove(entity: T): Boolean

  def removeById(id: ID): Boolean
}

trait GlossaryService extends BaseCrudService[Glossary, Long]

trait UserService extends BaseCrudService[User, Long]{

  def getByEmail(username: String): Option[User]

  def countByRole(role: String): Int

}

object GlossaryService extends GlossaryServiceImpl

object UserService extends UserServiceImpl