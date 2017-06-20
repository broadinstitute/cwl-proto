resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-json4s" % "0.3.0"
libraryDependencies ++= Seq(
  "eu.timepit" %% "refined"            % "0.8.2",
  "com.github.benhutchison" %% "mouse" % "0.8"
  )

scalaVersion := "2.12.2"

libraryDependencies += "co.fs2" %% "fs2-io" % "0.9.6" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "com.lihaoyi" %% "ammonite-ops" % "1.0.0-RC7" % "test"

libraryDependencies += "io.circe" %% "circe-yaml" % "0.6.1"
val circeVersion = "0.6.1"
val enumeratumVersion = "1.5.12"
val enumeratumCirceVersion = "1.5.14"

libraryDependencies ++= Seq(
    "com.beachape" %% "enumeratum-circe" % enumeratumCirceVersion
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-yaml",
  "io.circe" %% "circe-shapes",
  "io.circe" %% "circe-refined",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
    "com.beachape" %% "enumeratum" % enumeratumVersion
)

