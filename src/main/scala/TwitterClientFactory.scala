import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import com.typesafe.config.ConfigFactory
import scala.collection.immutable

package twitter {
	class Tweet(val id: Long, val url: String, val tags: Set[String])

	trait TwitterClient {
		def fetchMentions(sinceTweetId: Long): immutable.List[Tweet]
	}

	class Twitter4JTwitterClient(val twitter: twitter4j.Twitter) extends TwitterClient {
		override def fetchMentions(sinceTweetId: Long) = {
			// TODO: Implement this
			immutable.List(
				new Tweet(1, "http://www.google.com", immutable.Set("android", "icecream")),
				new Tweet(2, "http://www.apple.com", immutable.Set("ipad", "iphone"))
			)
		}
	}

	object TwitterClientFactory { 
		def get() = {
			val conf = ConfigFactory.load()
			val oauthConsumerKey = conf.getString("oauthConsumerKey")
			val oauthConsumerSecret = conf.getString("oauthConsumerSecret")
			val userAccessToken = conf.getString("accessToken")
			val userAccessTokenSecret = conf.getString("accessTokenSecret")	

			val twitter = new twitter4j.TwitterFactory().getInstance()
			twitter.setOAuthConsumer(oauthConsumerKey, oauthConsumerSecret)

			val accessToken = new AccessToken(userAccessToken, userAccessTokenSecret)
			twitter.setOAuthAccessToken(accessToken)

			new Twitter4JTwitterClient(twitter)
		}
	}
}