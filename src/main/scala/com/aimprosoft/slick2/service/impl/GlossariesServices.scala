package com.aimprosoft.slick2.service.impl

import com.aimprosoft.slick2.config.DB
import com.aimprosoft.slick2.config.Config.driver.simple._
import com.aimprosoft.slick2.model.{Glossary, User}
import com.aimprosoft.slick2.persistence.Persistence
import com.aimprosoft.slick2.persistence.impl.{UserPersistence, GlossaryPersistence}
import com.aimprosoft.slick2.service.{UserService, GlossaryService, BaseCrudService}
import org.mindrot.jbcrypt.BCrypt
import scala.language.reflectiveCalls

trait SlickTransactional {

  def readOnly[T]: (Session => T) => T = DB.withSession

  def transactional[T]: (Session => T) => T = DB.withTransaction

}

trait BaseCrudServiceImpl[T <: {val id: Option[Long]}, P <: Persistence[T, Long]] extends BaseCrudService[T]
with SlickTransactional{

  def persistence: P

  def list(startRow: Int = -1, pageSize: Int = -1): Seq[T] = readOnly {
    implicit session: Session => {
      //list of entities
      persistence.list(startRow, pageSize)
    }
  }

  def exists(id: Long): Boolean = readOnly {
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

  def getById(id: Long): Option[T] = readOnly {
    implicit session: Session =>
      persistence.get(id)
  }

  def add(entity: T): Unit = transactional {
    implicit session: Session =>
      persistence.insert(entity)
  }

  def update(entity: T): Unit = transactional {
    implicit session: Session =>
      persistence.update(entity)
  }

  def remove(entity: T): Unit = entity.id foreach { id =>
    removeById(id)
  }

  def removeById(id: Long): Unit = transactional {
    implicit session: Session =>
      persistence.delete(id)
  }

}

class GlossaryServiceImpl extends GlossaryService
with BaseCrudServiceImpl[Glossary, GlossaryPersistence] {
  def persistence = GlossaryPersistence
}

class UserServiceImpl extends UserService
with BaseCrudServiceImpl[User, UserPersistence] {

  def persistence = UserPersistence

  override def add(user: User): Unit = {
    //hash plain text password
    val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())

    super.add(user.copy(password = hashedPassword))
  }

  def getByEmail(email: String): Option[User] = transactional {
    implicit session: Session =>
      UserPersistence.findByEmail(email)
  }

  override def countByRole(role: String): Long = readOnly {
    implicit session: Session =>
      UserPersistence.countByRole(role)
  }
}