//project settings

name := "generic-slick-example"

version := "0.1"

//dependencies resolvers

resolvers += "Sonatype" at "http://search.maven.org/remotecontent?filepath="

//Scala's compiler and runtime settings

scalaVersion := "2.10.4"

scalaBinaryVersion := "2.10"

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

//dependencies settings

libraryDependencies ++= Seq(
  //test dependencies
  "junit" % "junit" % "4.11" % "test",
  "org.specs2" %% "specs2-junit" % "2.3.12",
  //security
  "org.mindrot" % "jbcrypt" % "0.3m",
  //runtime dependencies
  "com.typesafe.slick" %% "slick" % "2.0.2",
  "org.hsqldb" % "hsqldb" % "2.3.2"
)