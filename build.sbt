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
	"org.streum" %% "configrity-core" % "0.10.0"
)

fork in run := true

javaOptions += "-DpinboardbotDeploymentConfig=/Users/jon_r/Documents/projects/deploy/local/pinboardbot.properties"
