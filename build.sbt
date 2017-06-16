resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-json4s" % "0.3.0"
libraryDependencies ++= Seq(
  "eu.timepit" %% "refined"            % "0.8.2"
  )

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

scalaVersion := "2.12.2"

