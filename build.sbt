//project settings

name := "slick2-generic-example"

version := "0.1"

//dependencies resolvers

resolvers += "Sonatype" at "http://search.maven.org/remotecontent?filepath="

//Scala's compiler and runtime settings

//default scala version
scalaVersion := "2.10.4"

crossScalaVersions := Seq("2.10.4", "2.11.1")

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

//dependencies settings

libraryDependencies ++= Seq(
  //test dependencies
  "junit" % "junit" % "4.11" % "test",
  "org.specs2" %% "specs2-junit" % "2.3.12",
  //logging
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "org.slf4j" % "slf4j-log4j12" % "1.7.7",
  "log4j" % "log4j" % "1.2.17",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  //security
  "org.mindrot" % "jbcrypt" % "0.3m",
  //runtime dependencies
//  "com.typesafe.slick" %% "slick" % "2.0.2",
  "com.typesafe.slick" %% "slick" % "2.1.0-M2",
  "org.hsqldb" % "hsqldb" % "2.3.2",
  "mysql" % "mysql-connector-java" % "5.1.23",
  "com.mchange" % "c3p0" % "0.9.2.1"
)