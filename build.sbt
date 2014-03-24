name := "Pinboard Bot"

version := "1.0"

scalaVersion := "2.10.3"

resolvers ++= Seq(
	"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.3.0",
	"com.typesafe.akka" %% "akka-slf4j" % "2.3.0",
	"org.twitter4j" % "twitter4j-core" % "4.0.1",
	"org.scalaj" %% "scalaj-http" % "0.3.14",
	"ch.qos.logback" % "logback-classic" % "1.1.1" % "runtime",
	"com.typesafe.akka" %% "akka-testkit" % "2.3.0" % "test",
	"org.scalatest" %% "scalatest" % "2.1.0" % "test",
	"org.mockito" % "mockito-all" % "1.9.0" % "test"
)

fork in run := true

javaOptions += "-Dconfig.file=../deploy/local/pinboardbot.conf"

javaOptions += "-Dlogback.configurationFile=../deploy/local/logback.xml"

scalacOptions ++= Seq("-unchecked", "-deprecation")
