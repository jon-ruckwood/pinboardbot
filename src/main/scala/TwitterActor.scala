import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.event.Logging
import scala.collection.immutable
import twitter.TwitterClient

import Messages.FetchTweets
import Messages.Mention

class TwitterActor(val twitterClient: TwitterClient) extends Actor {
	val log = Logging(context.system, this)

	def receive = {
		case FetchTweets => 
			log.info("Fetching Tweets from Twitter. Tweet-tweet!")

			// TODO: Default isn't working for some reason...?
			twitterClient.fetchMentions(1).foreach { tweet => 
				// TODO: Cross terminology is confusing, have something domain specific?
				sender ! Mention(tweet.id, tweet.url, tweet.tags)	
			}
		case _ => log.info("Received unknown message")
	}
}
