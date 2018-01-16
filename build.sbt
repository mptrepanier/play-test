name := "play-test"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-test" % "2.6.11" % Test,
  "com.typesafe.play" %% "play-json" % "2.6.8"
)
