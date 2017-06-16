
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-json4s" % "0.3.0"

scalaVersion := "2.12.2"

libraryDependencies += "co.fs2" %% "fs2-io" % "0.9.6" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "com.lihaoyi" %% "ammonite-ops" % "1.0.0-RC7" % "test"

libraryDependencies += "io.circe" %% "circe-yaml" % "0.6.1"
