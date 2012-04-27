package net.selfdotlearn.pinboardbot

import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.event.Logging
import scala.collection.immutable

import net.selfdotlearn.pinboardbot.twitter.TwitterClient
import net.selfdotlearn.pinboardbot.Messages.{FetchTweets, Mention}

class TwitterActor(val twitterClient: TwitterClient) extends Actor {
	val log = Logging(context.system, this)

	def receive = {
		case FetchTweets => 
			log.info("Fetching Tweets from Twitter. Tweet-tweet!")

			// TODO: Default isn't working for some reason...?
			twitterClient.fetchMentions(1).foreach { tweet => 
				// TODO: Cross terminology is confusing, have something domain specific?
				// TODO: Handle else case
				sender ! Mention(tweet.id, tweet.url.getOrElse(""), tweet.tags)	
			}
		case _ => log.info("Received unknown message")
	}
}
