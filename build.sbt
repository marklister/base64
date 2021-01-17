import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "base64 root project"

lazy val root = project.in(file("."))
  .aggregate(JVM, JS/*, Native*/)
  .settings(commonSettings)
  .settings(skip in publish := true)

lazy val base64 = crossProject(JVMPlatform, JSPlatform, NativePlatform) // NativePlatform manually
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(commonSettings)
  .settings(
    libraryDependencies +=
      "com.lihaoyi" %%% "utest" % "0.7.5" % Test,
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
    scalacOptions in(Compile, doc) ++= Opts.doc.title("Base64"),
    scalacOptions in(Compile, doc) ++= Seq("-implicits")
  )

  .jvmSettings(
    initialCommands in console := """import com.github.marklister.base64.Base64._"""
  )
  .nativeSettings(
    scalaVersion := "2.12.12",
    crossScalaVersions := Seq("2.12.12"),  //TODO +publish tries to publish n times.
    nativeLinkStubs := true
  )

val commonSettings = Seq(
  scalaVersion := "2.12.12",
  crossScalaVersions := Seq("2.12.12", "2.13.4")
)
lazy val JVM = base64.jvm
lazy val JS = base64.project js
lazy val Native = base64.native
