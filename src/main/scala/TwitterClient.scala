import org.slf4j.LoggerFactory
import twitter4j._
import twitter4j.auth.AccessToken
import org.streum.configrity._
import scala.collection.JavaConversions._

object TwitterClient extends App {
	val log = LoggerFactory.getLogger(getClass)

	val config = Configuration.load(System.getProperty("pinboardbotDeploymentConfig"))
	val oauthConsumerKey = config[String]("oauthConsumerKey")
	val oauthConsumerSecret = config[String]("oauthConsumerSecret")
	val userAccessToken = config[String]("accessToken")
	val userAccessTokenSecret = config[String]("accessTokenSecret")

	val twitter = new TwitterFactory().getInstance()
	twitter.setOAuthConsumer(oauthConsumerKey, oauthConsumerSecret)

	val accessToken = new AccessToken(userAccessToken, userAccessTokenSecret)
	twitter.setOAuthAccessToken(accessToken)

	//val since = new Paging(170620191040999424L)
	val mentions = twitter.getMentions()

	mentions.foreach { status =>
		log.info("Mention -> {}", 
			Array(status.getId(), status.getUser().getScreenName(), 
				status.getURLEntities(), status.getHashtagEntities())
		)
	}
	
}