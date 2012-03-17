import twitter4j._
import twitter4j.auth.AccessToken
import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.event.Logging

import Messages.PollTwitter

class TwitterActor extends Actor {
	val log = Logging(context.system, this)
	var twitter : Twitter = _ 

	override def preStart() = {
		log.info("Initialising Twitter client")

		val conf = ConfigFactory.load()

		val oauthConsumerKey = conf.getString("oauthConsumerKey")
		val oauthConsumerSecret = conf.getString("oauthConsumerSecret")
		val userAccessToken = conf.getString("accessToken")
		val userAccessTokenSecret = conf.getString("accessTokenSecret")	

		twitter = new TwitterFactory().getInstance()
		twitter.setOAuthConsumer(oauthConsumerKey, oauthConsumerSecret)

		val accessToken = new AccessToken(userAccessToken, userAccessTokenSecret)
		twitter.setOAuthAccessToken(accessToken)

	}

	def receive = {
		case PollTwitter => log.info("Polling Twitter. Tweet-tweet!")
		case _ => log.info("Received unknown message")
	}
}
