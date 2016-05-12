name := """mnist-detector-server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "org.nd4j" % "nd4j-x86" % "0.4-rc3.8"

libraryDependencies += "org.deeplearning4j" % "dl4j-spark" % "0.4-rc3.8"

libraryDependencies += "org.nd4j" % "nd4j-api" % "0.4-rc3.8"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
