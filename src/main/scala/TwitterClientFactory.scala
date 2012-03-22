import twitter4j._
import twitter4j.auth.AccessToken
import com.typesafe.config.ConfigFactory

object TwitterClientFactory { 
	def getTwitter() = {
		val conf = ConfigFactory.load()

		val oauthConsumerKey = conf.getString("oauthConsumerKey")
		val oauthConsumerSecret = conf.getString("oauthConsumerSecret")
		val userAccessToken = conf.getString("accessToken")
		val userAccessTokenSecret = conf.getString("accessTokenSecret")	

		val twitter = new twitter4j.TwitterFactory().getInstance()
		twitter.setOAuthConsumer(oauthConsumerKey, oauthConsumerSecret)

		val accessToken = new AccessToken(userAccessToken, userAccessTokenSecret)
		twitter.setOAuthAccessToken(accessToken)

		twitter
	}
}