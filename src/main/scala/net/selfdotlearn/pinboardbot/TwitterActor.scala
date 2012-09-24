package net.selfdotlearn.pinboardbot

import com.typesafe.config.ConfigFactory
import akka.actor.{ Actor, ActorLogging }

import net.selfdotlearn.pinboardbot.twitter.TwitterClient
import net.selfdotlearn.pinboardbot.Messages.{ FetchMentions, Mention }

class TwitterActor(val twitterClient: TwitterClient) extends Actor with ActorLogging {

	// TODO: Persistence of this value
	var mostRecentTweetId : Long = 1

	def receive = {
		case FetchMentions ⇒ 
			log.info("Fetching tweets from id: {}", mostRecentTweetId)

			twitterClient.fetchMentions(sinceTweetId = mostRecentTweetId).foreach { tweet => 
				tweet.url match {
					case None ⇒ log.debug("No url present, dropping tweet id: {}", tweet.id)
					case Some(url) ⇒ sender ! Mention(tweet.id, url, tweet.tags)
				}	

				if (tweet.id > mostRecentTweetId) {
					mostRecentTweetId = tweet.id	
				}
			}
	}
}
