name := "Pinboard Bot"

version := "1.0"

scalaVersion := "2.9.1"

resolvers ++= Seq(
	"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)


libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka-actor" % "2.0",
	"org.twitter4j" % "twitter4j-core" % "2.2.5",
	"com.typesafe.config" % "config" % "0.3.0",
	"org.scalatest" %% "scalatest" % "1.7.1" % "test",
	"org.mockito" % "mockito-all" % "1.9.0" % "test"
)

fork in run := true

javaOptions += "-Dconfig.file=../deploy/local/pinboardbot.properties"

javaOptions += "-Dlogback.configurationFile=../deploy/local/logback.xml"

scalacOptions ++= Seq("-unchecked", "-deprecation")
