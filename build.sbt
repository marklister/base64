import sbt.Keys._

name := "base64 root project"

scalaVersion := "2.11.7"

lazy val root = project.in(file("."))
  .aggregate(JS, JVM)
  .settings()

lazy val base64 = crossProject.in(file(".")).
  settings(
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.1",
    testFrameworks += new TestFramework("utest.runner.Framework"),
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.11.7"),
    name := "Base64",
    organization := "com.github.marklister",
    version := "0.2.1",
    homepage := Some(url("https://github.com/marklister/base64")),
    startYear := Some(2013),
    description := "Tiny, idiomatic but not fast base64 implementation",
    licenses +=("BSD Simplified", url("http://opensource.org/licenses/BSD-SIMPLIFIED")),

    pomExtra := (
      <scm>
        <url>git@github.com:marklister/base64.git</url>
        <connection>scm:git:git@github.com:marklister/base64.git</connection>
      </scm>
        <developers>
          <developer>
            <id>marklister</id>
            <name>Mark Lister</name>
            <url>https://github.com/marklister</url>
          </developer>
        </developers>),
    scalacOptions in(Compile, doc) ++= Opts.doc.title("Base64"),
    scalacOptions in(Compile, doc) ++= Seq("-implicits")
  )
  
  .jvmSettings(
  initialCommands in console := """import com.github.marklister.base64.Base64._"""
)
  .jsSettings(
    scalaJSUseRhino in Global := false
  )

lazy val JVM = base64.jvm
lazy val JS = base64.js
