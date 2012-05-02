package net.selfdotlearn.pinboardbot

import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.duration._
import akka.actor.Actor
import akka.actor.Cancellable
import akka.event.Logging

import net.selfdotlearn.pinboardbot.twitter.TwitterClientFactory
import net.selfdotlearn.pinboardbot.Messages._

object Main extends App {

	val system = ActorSystem("PinboardBotSystem")
	val master = system.actorOf(Props[Master], name = "master")

	val cancellable = system.scheduler.schedule(0 seconds,  10 seconds, master, PollTwitter) 

	class Master extends Actor {
		val log = Logging(context.system, this)

		val twitterActor = system.actorOf(Props(
			new TwitterActor(TwitterClientFactory.get())), 
			name = "twitterActor")
		
		def receive = {
			case PollTwitter => twitterActor ! FetchTweets
			case Mention(id, url, tags) => log.info("Recieved tweet {}, {}, {}", id, url, tags) 
			case _ => log.info("Received unknown message")
		}
	}
}