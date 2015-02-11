name := "base64 root project"

scalaVersion:="2.11.5"

lazy val root = project.in(file("."))
  .aggregate(base64JS, base64JVM)
  .settings(
  publish := {},
  publishLocal := {}
)

lazy val base64 = crossProject.in(file(".")).
  settings(
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0",
    testFrameworks += new TestFramework("utest.runner.Framework"),
    scalaVersion := "2.11.5",
    crossScalaVersions := Seq("2.11.5", "2.10.4"),
    name := "Base64",
    organization :="com.github.marklister",
    version := "0.1.1",
    scalaVersion := "2.11.5",
    homepage := Some(url("https://github.com/marklister/base64")),
    startYear := Some(2013),
    description := "Tiny, idiomatic but not fast base64 implementation",
    licenses += ("BSD Simplified", url("http://opensource.org/licenses/BSD-SIMPLIFIED")),

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
    scalacOptions in (Compile, doc) ++= Opts.doc.title("Base64"),
    scalacOptions in (Compile, doc) ++= Seq("-implicits")
  )
  //.settings(bintraySettings:_*)  //REMOVE FROM PUBLISHED build.sbt

  .jvmSettings(
  )
  .jsSettings(
      
  )

lazy val base64JVM = base64.jvm
lazy val base64JS = base64.js
