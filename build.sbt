//project settings

name := "slick2-generic-example"

version := "0.3"

//dependencies resolvers

resolvers += "Sonatype" at "http://search.maven.org/remotecontent?filepath="

//Scala's compiler and runtime settings

//default scala version
scalaVersion := "2.11.1"

crossScalaVersions := Seq("2.10.4", "2.11.1")

scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation"
)

//dependencies settings

libraryDependencies ++= {
  val log4j2version = "2.0"
  Seq(
    //test dependencies
    "junit" % "junit" % "4.11" % "test",
    "org.specs2" %% "specs2-junit" % "2.3.13",
    //logging
    "org.slf4j" % "slf4j-api" % "1.7.7",
    "org.apache.logging.log4j" % "log4j-core" % log4j2version,
    "org.apache.logging.log4j" % "log4j-api" % log4j2version,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4j2version,
    "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
    //security
    "org.mindrot" % "jbcrypt" % "0.3m",
    //runtime dependencies
    "com.typesafe.slick" %% "slick" % "2.1.0-RC2",
    "org.hsqldb" % "hsqldb" % "2.3.2",
    "com.mchange" % "c3p0" % "0.9.2.1"
  )
}