name := "base64"

libraryDependencies += "com.lihaoyi" %% "utest" % "0.2.4"

testFrameworks += new TestFramework("utest.runner.JvmFramework")

//libraryDependencies += "commons-codec" % "commons-codec" % "1.9" //only for microbenchmark  you can get rid of this


initialCommands in console := """
  import io.github.marklister.base64.Base64._
  """

version := "0.1.0"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.11.4","2.10.4")

homepage := Some(url("https://github.com/marklister/product-collections"))

startYear := Some(2013)

description := "Tiny simple idiomatic Base64 encoder and decoder"

licenses += ("BSD Simplified", url("http://opensource.org/licenses/BSD-SIMPLIFIED"))

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
  </developers>)

