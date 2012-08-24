package net.selfdotlearn.pinboardbot

import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.event.Logging
import scala.collection.immutable

import net.selfdotlearn.pinboardbot.twitter.TwitterClient
import net.selfdotlearn.pinboardbot.Messages.{FetchMentions, Mention}

class TwitterActor(val twitterClient: TwitterClient) extends Actor {
	val log = Logging(context.system, this)

	def receive = {
		case FetchMentions => 
			log.info("Fetching Mentions from Twitter. Tweet-tweet!")

			twitterClient.fetchMentions(sinceTweetId = 1).foreach { tweet => 
				sender ! Mention(tweet.id, tweet.url.getOrElse(""), tweet.tags)	
			}
		case _ => log.info("Received unknown message")
	}
}
