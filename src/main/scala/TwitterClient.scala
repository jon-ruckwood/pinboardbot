import org.slf4j.LoggerFactory
import twitter4j.{TwitterFactory, Twitter}
import twitter4j.auth.AccessToken
import org.streum.configrity._

object TwitterClient extends App {
	val log = LoggerFactory.getLogger(getClass)

	val config = Configuration.load(System.getProperty("pinboardbotDeploymentConfig"))

	val twitter = new TwitterFactory().getInstance()
	twitter.setOAuthConsumer(config[String]("oauthConsumerKey"), 
		config[String]("oauthConsumerSecret")) 

	val token = new AccessToken(config[String]("accessToken"),
		config[String]("accessTokenSecret")) 
	twitter.setOAuthAccessToken(token)


	log.debug("Tweets: {}", twitter.getUserTimeline())
}