name := "Pinboard Bot"

version := "1.0"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Spraycan Repository" at "http://repo.spray.cc/"

libraryDependencies += "cc.spray" %%  "spray-can" % "0.9.2" 
