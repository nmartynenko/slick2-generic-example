package com.aimprosoft.slick2.test

import com.aimprosoft.slick2.config.DB
import com.aimprosoft.slick2.config.Config.driver.simple._
import com.aimprosoft.slick2.persistence.impl.{GlossaryPersistence, UserPersistence}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  //create DB schema
  DB withSession {implicit session =>
    (UserPersistence.tableQuery.ddl ++ GlossaryPersistence.tableQuery.ddl).create
  }

  //TODO cover with tests
  "Service layer of Application" should {

    "User Service" should {

      "Dummy" in {
        true mustEqual true
      }

    }

    "Glossary Service" should {

      "Dummy" in {
        true mustEqual true
      }

    }

  }

}