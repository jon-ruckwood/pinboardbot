import twitter4j.Twitter
import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.event.Logging
import scala.collection.immutable

import Messages.FetchTweets
import Messages.Tweet

class TwitterActor(val twitterClient: Twitter) extends Actor {
	val log = Logging(context.system, this)

	def receive = {
		case FetchTweets => 
			log.info("Fetching Tweets from Twitter. Tweet-tweet!")
			sender ! Tweet(94135, "http://www.selfdotlearn.net", immutable.Set("fail", "to-read"))
		case _ => log.info("Received unknown message")
	}
}
