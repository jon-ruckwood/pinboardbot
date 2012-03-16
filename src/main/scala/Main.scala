import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.duration._

import Messages._

object Main extends App {

	val system = ActorSystem("PinboardBotSystem")
	val twitterActor = system.actorOf(Props[TwitterActor], name = "twitterActor")

	val cancellable = system.scheduler.schedule(0 seconds,  10 seconds, twitterActor,  PollTwitter)
}
