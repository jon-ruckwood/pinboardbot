name := "Pinboard Bot"

version := "1.0"

scalaVersion := "2.9.1"

resolvers ++= Seq(
	"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)


libraryDependencies ++= Seq(
	"org.slf4j" % "slf4j-api" % "1.6.4",
	"ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
	"org.twitter4j" % "twitter4j-core" % "2.2.5",
	"com.typesafe.config" % "config" % "0.3.0"
)

fork in run := true

javaOptions += "-Dconfig.file=../deploy/local/pinboardbot.properties"

javaOptions += "-Dlogback.configurationFile=../deploy/local/logback.xml"
