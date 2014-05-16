package com.aimprosoft.slick2

import scala.slick.jdbc.JdbcBackend

package object config {

  def DB: JdbcBackend#Database = Config.DB

}
