import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "base64 root project"

val scala212 = "2.12.17"
val scala213 = "2.13.10"
val scala3 = "3.2.1"

lazy val root = project.in(file("."))
  .aggregate(JVM, JS/*, Native*/)
  .settings(commonSettings)
  .settings(publish / skip := true)

lazy val base64 = crossProject(JVMPlatform, JSPlatform, NativePlatform) // NativePlatform manually
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(commonSettings)
  .settings(
    libraryDependencies +=
      "com.lihaoyi" %%% "utest" % "0.8.1" % Test,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    name := "Base64",
    organization := "com.github.marklister",
    version := "0.3.0",
    homepage := Some(url("https://github.com/marklister/base64")),
    startYear := Some(2013),
    description := "Small, idiomatic base64 implementation",
    licenses += ("BSD Simplified", url("http://opensource.org/licenses/BSD-SIMPLIFIED")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/marklister/base64"),
        "scm:git:git@github.com:marklister/base64.git"
      )
    ),
    developers := List(
      Developer(
        id = "marklister",
        name = "Mark lister",
        email = "mark.p.lister@gmail.com",
        url = url("https://github.com/marklister")
      )
    ),
    description := "Base64 encoder / decoder",
    licenses := List("BSD" -> new URL("https://opensource.org/licenses/BSD-2-Clause")),
    homepage := Some(url("https://github.com/marklister/base64")),
    pomIncludeRepository := { _ => false },
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    } ,
    publishMavenStyle := true,

//    pomExtra := (
//      <scm>
//        <url>git@github.com:marklister/base64.git</url>
//        <connection>scm:git:git@github.com:marklister/base64.git</connection>
//      </scm>
//        <developers>
//          <developer>
//            <id>marklister</id>
//            <name>Mark Lister</name>
//            <url>https://github.com/marklister</url>
//          </developer>
//        </developers>),
    doc / scalacOptions ++= Opts.doc.title("Base64"),
    Compile / scalacOptions ++= Opts.doc.title("Base64"),
    Compile / scalacOptions --= Seq("-doc-title")
  )

  .jvmSettings(
    console / initialCommands := """import com.github.marklister.base64.Base64._"""
  )
  .nativeSettings(
    scalaVersion := scala212,
    crossScalaVersions := Seq(scala212),  //TODO +publish tries to publish n times.
    nativeLinkStubs := true
  )

val commonSettings = Seq(
  scalaVersion := scala212,
  crossScalaVersions := Seq(scala212, scala213, scala3)
)
lazy val JVM = base64.jvm
lazy val JS = base64.project js
lazy val Native = base64.native
