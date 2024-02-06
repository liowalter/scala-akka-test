import Dependencies.{akka, alpakka, scalaTest}
import sbt.Keys.libraryDependencies


ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "data-test",
    resolvers ++= Seq(
      "Typesafe" at "https://repo.typesafe.com/typesafe/releases/",
      "Restlet Repository" at "https://maven.restlet.talend.com",
      "Akka library repository" at "https://repo.akka.io/maven",
    ),
    libraryDependencies ++= Seq(
      scalaTest,
      alpakka,
      akka,
    ),
  )
