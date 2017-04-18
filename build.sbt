name := """play-scala-isolated-slick-example"""

version := "1.1-SNAPSHOT"

scalaVersion := "2.11.11"

lazy val flyway = (project in file("modules/flyway"))
  .enablePlugins(FlywayPlugin)

lazy val api = (project in file("modules/api"))
  .settings(Common.projectSettings)

lazy val slick = (project in file("modules/slick"))
  .settings(Common.projectSettings)
  .aggregate(api)
  .dependsOn(api)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .aggregate(slick)
  .dependsOn(slick)

TwirlKeys.templateImports += "com.example.user.User"

libraryDependencies += "com.h2database" % "h2" % "1.4.192"

// Automatic database migration available in testing
fork in Test := true
libraryDependencies += "org.flywaydb" % "flyway-core" % "4.0" % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test

