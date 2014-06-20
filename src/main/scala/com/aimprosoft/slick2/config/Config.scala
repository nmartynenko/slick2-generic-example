package com.aimprosoft.slick2.config

import java.util.Properties
import javax.sql.DataSource

import com.mchange.v2.c3p0.ComboPooledDataSource
import com.typesafe.scalalogging.slf4j.StrictLogging

import scala.slick.driver._
import scala.slick.jdbc.JdbcBackend

class Config extends StrictLogging {

  protected lazy val configProps = {
    val props = new Properties
    val reader = io.Source.fromURL(getClass.getResource("/db.properties")).bufferedReader()
    props.load(reader)
    reader.close()
    props
  }

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

  def driverClassName: String = configProps.getProperty("jdbc.driverClassName")

  def driverUrl: String = configProps.getProperty("jdbc.url")

  def driverUsername: String = configProps.getProperty("jdbc.username")

  def driverPassword: String = configProps.getProperty("jdbc.password")

  def driver(name: String = defaultName) = {
    driverByName(name).getOrElse(sys.error("No suitable driver was found"))
  }
}

object Config extends Config {

  private lazy val ds: DataSource = {
    //create datasource
    val ds = new ComboPooledDataSource()

    //set common values
    ds.setDriverClass(driverClassName)
    ds.setJdbcUrl(driverUrl)
    ds.setUser(driverUsername)
    ds.setPassword(driverPassword)

    //set c3p0 properties
    ds.setProperties(configProps)

    ds
  }

  lazy val driver: JdbcDriver = driver(driverClassName)

  //create single instance of DB
  lazy val DB: JdbcBackend#Database = driver.simple.Database.forDataSource(ds)
}
