package com.aimprosoft.slick2

import com.aimprosoft.slick2.config.DB
import com.aimprosoft.slick2.config.Config.driver.simple._
import com.aimprosoft.slick2.persistence.impl.{GlossaryPersistence, UserPersistence}
import com.aimprosoft.slick2.service.UserService
import com.aimprosoft.slick2.model.User

object Main extends App {
  //create DB schema
  DB withSession {implicit session =>
    (UserPersistence.tableQuery.ddl ++ GlossaryPersistence.tableQuery.ddl).create
  }

  UserService.add(
    User(
      email = "cool@email.com",
      name = "Cool Man"
    )
  )

  println(UserService.list())//should be one

  //TODO few more examples
}
