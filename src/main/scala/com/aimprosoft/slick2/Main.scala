package com.aimprosoft.slick2

import com.aimprosoft.slick2.config.Config.driver.simple._
import com.aimprosoft.slick2.config.DB
import com.aimprosoft.slick2.model.{Glossary, User}
import com.aimprosoft.slick2.persistence.impl.{GlossaryPersistence, UserPersistence}
import com.aimprosoft.slick2.service.{GlossaryService, UserService}

object Main extends App {
  Example.createTestEnvironment()

  println(UserService.list())//should be two

  //TODO few more examples
}

object Example {
  def createTestEnvironment(){
    //create DB schema
    DB withSession {implicit session =>
      (UserPersistence.tableQuery.ddl ++ GlossaryPersistence.tableQuery.ddl).create
    }

    //added some data for User table
    Seq(
      User(
        email = "cool@email.com",
        name = "Cool Man"
      ),
      User(
        email = "john.doe@example.com",
        name = "John Doe"
      )
    ) foreach UserService.add

    //added some data for Glossary table
    Seq(
      Glossary(
        name = "First glossary",
        description = Some("Long description")
      ),
      Glossary(
        name = "Second glossary",
        description = Some("Other long description")
      )
    ) foreach GlossaryService.add
  }
}

