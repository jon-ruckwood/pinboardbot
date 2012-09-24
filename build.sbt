name := "Pinboard Bot"

version := "1.0"

scalaVersion := "2.9.2"

resolvers ++= Seq(
	"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka-actor" % "2.0.3",
	"com.typesafe.akka" % "akka-slf4j" % "2.0.3",
	"org.twitter4j" % "twitter4j-core" % "2.2.5",
	"org.scalaj" %% "scalaj-http" % "0.3.2",
	"ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
	"com.typesafe.akka" % "akka-testkit" % "2.0.3" % "test",
	"org.scalatest" %% "scalatest" % "1.7.1" % "test",
	"org.mockito" % "mockito-all" % "1.9.0" % "test"
)

fork in run := true

javaOptions += "-Dconfig.file=../deploy/local/pinboardbot.conf"

javaOptions += "-Dlogback.configurationFile=../deploy/local/logback.xml"

scalacOptions ++= Seq("-unchecked", "-deprecation")
