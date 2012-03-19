import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.duration._
import akka.actor.Actor
import akka.actor.Cancellable
import akka.event.Logging

import Messages._

object Main extends App {

	val system = ActorSystem("PinboardBotSystem")
	val master = system.actorOf(Props[Master], name = "master")

	master ! Start

	class Master extends Actor {
		val log = Logging(context.system, this)

		val twitterActor = system.actorOf(Props[TwitterActor], name = "twitterActor")
		var cancellable : Cancellable = _

		def receive = {
			case Start => 
				cancellable = system.scheduler.schedule(0 seconds,  10 seconds, 
					twitterActor, PollTwitter) 
			case Tweet(id, url, tags) =>
				log.info("Recieved tweet: " + id) 
			case _ => log.info("Received unknown message")
		}
	}
}
