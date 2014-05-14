package com.aimprosoft.slick2.service

import com.aimprosoft.slick2.model.{User, Glossary}
import com.aimprosoft.slick2.service.impl.{UserServiceImpl, GlossaryServiceImpl}

trait BaseCrudService[T] {

  def list(startRow: Int = -1, pageSize: Int = -1): Seq[T]

  def exists(glossaryId: Long): Boolean

  def count: Int

  def getFirst: Option[T]

  def getById(glossaryId: Long): Option[T]

  def add(glossary: T): Unit

  def update(glossary: T): Unit

  def remove(glossary: T): Unit

  def removeById(glossaryId: Long): Unit
}

trait GlossaryService extends BaseCrudService[Glossary]

trait UserService extends BaseCrudService[User]{

  def getByEmail(username: String): Option[User]

  def countByRole(role: String): Long

}

object GlossaryService extends GlossaryServiceImpl

object UserService extends UserServiceImpl