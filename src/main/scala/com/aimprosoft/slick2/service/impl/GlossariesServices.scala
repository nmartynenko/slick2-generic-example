package com.aimprosoft.slick2.service.impl

import com.aimprosoft.slick2.config.Config.driver.simple._
import com.aimprosoft.slick2.config.DB
import com.aimprosoft.slick2.model.{Glossary, User}
import com.aimprosoft.slick2.persistence._
import com.aimprosoft.slick2.persistence.impl.{GlossaryPersistence, UserPersistence}
import com.aimprosoft.slick2.service.{BaseCrudService, GlossaryService, UserService}
import org.mindrot.jbcrypt.BCrypt

import scala.language.reflectiveCalls

trait SlickTransactional {

  def readOnly[T]: (Session => T) => T = DB.withSession

  def transactional[T]: (Session => T) => T = DB.withTransaction

}

trait BaseCrudServiceImpl[T <: Identifiable[ID], ID] extends BaseCrudService[T, ID]
with SlickTransactional{

  def persistence: Persistence[T, ID]

  def list(startRow: Int = 0, pageSize: Int = -1): Seq[T] = readOnly {
    implicit session: Session => {
      //list of entities
      persistence.list(startRow, pageSize)
    }
  }

  def exists(id: ID): Boolean = readOnly {
    implicit session: Session =>
      persistence.exists(id)
  }

  def count: Int = readOnly {
    implicit session: Session =>
      persistence.count
  }

  def getFirst: Option[T] = readOnly {
    implicit session: Session =>
      persistence.list(0, 1).headOption
  }

  def getById(id: ID): Option[T] = readOnly {
    implicit session: Session =>
      persistence.get(id)
  }

  def add(entity: T): ID = transactional {
    implicit session: Session =>
      persistence.insert(entity)
  }

  def update(entity: T): Boolean = transactional {
    implicit session: Session =>
      persistence.update(entity)
  }

  def remove(entity: T): Boolean = {
    entity.id.fold(false) { id =>
      removeById(id)
    }
  }

  def removeById(id: ID): Boolean = transactional {
    implicit session: Session =>
      persistence.delete(id)
  }

}

class GlossaryServiceImpl extends GlossaryService
with BaseCrudServiceImpl[Glossary, Long] {
  def persistence = GlossaryPersistence
}

class UserServiceImpl extends UserService
with BaseCrudServiceImpl[User, Long] {

  def persistence = UserPersistence

  override def add(user: User): Long = {
    //hash plain text password
    val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())

    super.add(user.copy(password = hashedPassword))
  }

  def getByEmail(email: String): Option[User] = transactional {
    implicit session: Session =>
      persistence.findByEmail(email)
  }

  def countByRole(role: String): Int = readOnly {
    implicit session: Session =>
      persistence.countByRole(role)
  }
}