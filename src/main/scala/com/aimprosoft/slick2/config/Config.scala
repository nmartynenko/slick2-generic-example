package com.aimprosoft.slick2.config

import scala.slick.driver._
import scala.slick.jdbc.JdbcBackend

class Config {
  val defaultName = "default"

  /** Extend this to add driver or change driver mapping */
  protected def driverByName: String => Option[JdbcDriver] = Map(
    "org.apache.derby.jdbc.EmbeddedDriver" -> DerbyDriver,
    "org.h2.Driver" -> H2Driver,
    "org.hsqldb.jdbcDriver" -> HsqldbDriver,
    //alternative name for HsqlDbDriver
    "org.hsqldb.jdbc.JDBCDriver" -> HsqldbDriver,
    "com.mysql.jdbc.Driver" -> MySQLDriver,
    "org.postgresql.Driver" -> PostgresDriver,
    "org.sqlite.JDBC" -> SQLiteDriver
  ).get

  //can be loaded from config file
  def driverName: String = "org.hsqldb.jdbc.JDBCDriver"

  //can be loaded from config file
  def driverUrl: String = "jdbc:hsqldb:mem:test"

  //can be loaded from config file
  def driverUsername: String = "sa"

  //can be loaded from config file
  def driverPassoword: String = ""

  def driver(name: String = defaultName) = {
    driverByName(name).getOrElse(sys.error("No suitable driver was found"))
  }
}

object Config extends Config {

  lazy val driver: JdbcDriver = driver(driverName)

  //TODO switch it with DB connection pool
  lazy val DB: JdbcBackend#Database = driver.simple.Database.forURL(url = driverUrl, user = driverUsername, password = driverPassoword)
}
