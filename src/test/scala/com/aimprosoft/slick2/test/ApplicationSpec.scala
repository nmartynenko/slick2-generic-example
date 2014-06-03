package com.aimprosoft.slick2.test

import com.aimprosoft.slick2.Example
import com.aimprosoft.slick2.service.{GlossaryService, UserService}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  //call startup action
  Example.createTestEnvironment()

  "Service layer of Application" should {

    "User Service" should {

      "return positive count of users, present in DB" in {
        UserService.count must beGreaterThanOrEqualTo(0)
      }

    }

    "Glossary Service" should {

      "return positive count of glossaries, present in DB" in {
        GlossaryService.count must beGreaterThanOrEqualTo(0)
      }

    }

  }

}