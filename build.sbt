name := "openshift-spray-mongodb"

version := "1.0.0"

scalaVersion := "2.11.2"

resolvers ++= Seq(
  "snapshots"           at "https://oss.sonatype.org/content/repositories/snapshots",
  "releases"            at "https://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repo releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Repo snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "spray repo"          at "http://repo.spray.io"
)

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

tomcat()

artifact in packageWar <<= moduleName(n => Artifact("example", "war", "war"))

libraryDependencies ++= {
  val sprayVersion = "1.3.1"
  val akkaVersion = "2.3.5"
  Seq(
    "org.reactivemongo"       %% "reactivemongo"    % "0.10.5.akka23-SNAPSHOT",
    "org.slf4j"               %   "slf4j-api"       % "1.7.7",
    "ch.qos.logback"          %   "logback-core"    % "1.1.2",
    "ch.qos.logback"          %   "logback-classic" % "1.1.2",
    "io.spray"                %%  "spray-servlet"   % sprayVersion,
    "io.spray"                %%  "spray-routing"   % sprayVersion,
    "io.spray"                %%  "spray-testkit"   % sprayVersion % "test",
    "io.spray"                %%  "spray-httpx"     % sprayVersion,
    "io.spray"                %%  "spray-json"      % "1.2.6",
    "org.json4s"              %%  "json4s-native"   % "3.2.10",
    "joda-time"               %   "joda-time"       % "2.4",
    "org.joda"                %   "joda-convert"    % "1.7",
    "com.typesafe"            %   "config"          % "1.2.1",
    "com.typesafe.akka"       %%  "akka-actor"      % akkaVersion,
    "com.typesafe.akka"       %%  "akka-slf4j"      % akkaVersion,
    "com.typesafe.akka"       %%  "akka-testkit"    % akkaVersion % "test",
    "org.scalatest"           %%  "scalatest"       % "2.2.1" % "test",
    "javax.servlet"           %   "javax.servlet-api" % "3.0.1" % "provided"
  )
}
