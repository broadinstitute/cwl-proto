name := "cwl-scala"

organization := "org.broadinstitute"

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalaVersion := "2.12.2"

val circeVersion = "0.6.1"
val enumeratumVersion = "1.5.12"
val enumeratumCirceVersion = "1.5.14"

libraryDependencies ++= Seq(
  "eu.timepit" %% "refined"            % "0.8.2",
  "com.github.benhutchison" %% "mouse" % "0.8",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.lihaoyi" %% "ammonite-ops" % "1.0.0-RC7" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "co.fs2" %% "fs2-io" % "0.9.6" % "test",

  "com.beachape" %% "enumeratum" % enumeratumVersion,
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

val buildTimestamp = System.currentTimeMillis() / 1000

def artifactoryResolver(isSnapshot: Boolean): Resolver = {
  val repoType = if (isSnapshot) "snapshot" else "release"
  val repoUrl =
    s"https://broadinstitute.jfrog.io/broadinstitute/libs-$repoType-local;build.timestamp=$buildTimestamp"
  val repoName = "artifactory-publish"
  repoName at repoUrl
}

val artifactoryCredentials: Credentials = {
  val username = sys.env.getOrElse("ARTIFACTORY_USERNAME", "")
  val password = sys.env.getOrElse("ARTIFACTORY_PASSWORD", "")
  Credentials("Artifactory Realm", "broadinstitute.jfrog.io", username, password)
}

def publishingSettings: Seq[Setting[_]] =
  Seq(publishTo := Option(artifactoryResolver(isSnapshot.value)), credentials += artifactoryCredentials)

