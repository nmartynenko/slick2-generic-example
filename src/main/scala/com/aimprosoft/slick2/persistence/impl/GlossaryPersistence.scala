package com.aimprosoft.slick2.persistence.impl

import com.aimprosoft.slick2.config.Config.driver.simple._
import com.aimprosoft.slick2.model.{Glossary, User}
import com.aimprosoft.slick2.persistence.Persistence

//application DAO
trait GlossaryPersistence extends Persistence[Glossary, Long]

trait UserPersistence extends Persistence[User, Long] {
  def findByEmail(email: String)(implicit session: Session): Option[User]
}

//application DAO objects
object GlossaryPersistence extends SlickGlossariesPersistence

object UserPersistence extends SlickUsersPersistence

//DB Table descriptions
class SlickGlossaries(tag: Tag) extends SlickBaseTable[Glossary, Long](tag, "glossary") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def description = column[String]("description", O.Nullable)

  def * = (id.?, name, description.?) <> (Glossary.tupled, Glossary.unapply)
}

class SlickUsers(tag: Tag) extends SlickBaseTable[User, Long](tag, "glossary_user") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def email = column[String]("email", O.NotNull)

  def password = column[String]("password", O.Nullable)

  def name = column[String]("name", O.NotNull)

  def role = column[String]("role")

  def * = (id.?, email, password, name, role) <> (User.tupled, User.unapply)

}

//concrete persistence services
class SlickUsersPersistence extends SlickBasePersistence[User, Long, SlickUsers]
with UserPersistence{

  //Macro expansion implementation
  val tableQuery = TableQuery[SlickUsers]

  def findByEmail(email: String)(implicit session: Session): Option[User] = {
    tableQuery.filter(_.email === email).firstOption
  }

  def countByRole(role: String)(implicit session: Session): Long = {
    tableQuery.filter(_.role === role).length.run
  }

}

class SlickGlossariesPersistence extends SlickBasePersistence[Glossary, Long, SlickGlossaries]
with GlossaryPersistence {

  //Macro expansion implementation
  val tableQuery = TableQuery[SlickGlossaries]

}