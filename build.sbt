val scala3Version = "3.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",
    scalaVersion := scala3Version,

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test",
    libraryDependencies += "org.scalatestplus" %% "scalacheck-1-15" % "3.2.9.0" % "test",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    libraryDependencies += "junit" % "junit" % "4.13.2" % Test,
    libraryDependencies += "io.cucumber" % "cucumber-junit" % "7.2.3" % Test,
    libraryDependencies += "io.cucumber" % "cucumber-scala_3" % "8.2.2" % Test
    )
