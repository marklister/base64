name := "base64"

libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "1.14" % "test")

initialCommands in console := """
  import io.github.marklister.base64.Base64._
  """