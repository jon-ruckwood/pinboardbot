package net.selfdotlearn.pinboardbot

import twitter4j.{ Twitter, TwitterFactory, Paging, Status }
import twitter4j.auth.AccessToken
import com.typesafe.config.Config
import scala.collection.immutable
import scala.collection.mutable
import scala.collection.JavaConversions._

package twitter {
	class Tweet(val id: Long, val url: Option[String], val tags: Set[String])

	trait TwitterClient {
		def fetchMentions(sinceTweetId: Long): immutable.List[Tweet]
	}

	class Twitter4JTwitterClient(private val twitter: twitter4j.Twitter) extends TwitterClient {
		override def fetchMentions(sinceTweetId: Long = 1) = {
			val since = new Paging(sinceTweetId)
			val mentions = twitter.getMentionsTimeline(since)
			val tweets = new mutable.ListBuffer[Tweet]
			mentions.foreach { status : Status ⇒
				val id = status.getId()

				val url = if (hasUrls(status)) {
					Some(getFirstUrlAsString(status)) 
				} else {
					None
				}

				val tags = status.getHashtagEntities().map(_.getText()).toSet
				tweets += new Tweet(id, url, tags)
			}
			
			tweets.toList
		}

		private def hasUrls(status: Status) = {
			status.getURLEntities().length > 0
		}

		private def getFirstUrlAsString(status: Status) = {
			val urlEntities = status.getURLEntities()
			urlEntities(0).getExpandedURL().toString() 
		}
	}

	object TwitterClientFactory { 
		def get(config: Config) = {
			val oauthConsumerKey = config.getString("oauth-consumer-key")
			val oauthConsumerSecret = config.getString("oauth-consumer-secret")
			val userAccessToken = config.getString("access-token")
			val userAccessTokenSecret = config.getString("access-token-secret")	

			val twitter = new twitter4j.TwitterFactory().getInstance()
			twitter.setOAuthConsumer(oauthConsumerKey, oauthConsumerSecret)

			val accessToken = new AccessToken(userAccessToken, userAccessTokenSecret)
			twitter.setOAuthAccessToken(accessToken)

			new Twitter4JTwitterClient(twitter)
		}
	}
}
