package net.selfdotlearn.pinboardbot

import com.typesafe.config.ConfigFactory
import akka.actor.{ Actor, ActorLogging }

import net.selfdotlearn.pinboardbot.twitter.TwitterClient
import net.selfdotlearn.pinboardbot.Messages.{ FetchMentions, Mention }

class TwitterActor(val twitterClient: TwitterClient) extends Actor with ActorLogging {

	// TODO: Persistence of this value
	// TODO: This value should start from 1
	var lastTweetId : Long = 0

	def receive = {
		case FetchMentions => 
			log.info("Fetching Mentions from Twitter. Tweet-tweet!")

			twitterClient.fetchMentions(sinceTweetId = lastTweetId).foreach { tweet => 
				tweet.url match {
					case None => log.debug("No url present, dropping tweet {}", tweet.id)
					case Some(url) => sender ! Mention(tweet.id, url, tweet.tags)
				}	

				lastTweetId = tweet.id	
			}
		case _ => log.info("Received unknown message")
	}
}
