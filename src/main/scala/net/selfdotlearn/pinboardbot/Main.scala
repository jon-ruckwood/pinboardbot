package net.selfdotlearn.pinboardbot

import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.duration._
import akka.actor.{ Actor, ActorLogging }
import akka.actor.Cancellable

import net.selfdotlearn.pinboardbot.twitter.TwitterClientFactory
import net.selfdotlearn.pinboardbot.Messages._

object Main extends App {

	val system = ActorSystem("PinboardBotSystem")
	val master = system.actorOf(Props[Master], name = "master")

	val cancellable = system.scheduler.schedule(0 seconds,  10 seconds, master, PollTwitter) 

	class Master extends Actor with ActorLogging {
		val twitterActor = system.actorOf(Props(
			new TwitterActor(TwitterClientFactory.get(context.system.settings.config))), 
			name = "twitterActor")

		val pinboardActor = system.actorOf(Props[PinboardActor], name = "pinboardActor")
		
		def receive = {
			case PollTwitter ⇒ twitterActor ! FetchMentions
			case mention: Mention ⇒ pinboardActor ! mention 
		}
	}
}
