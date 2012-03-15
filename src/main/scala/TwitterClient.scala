import org.slf4j.LoggerFactory
import twitter4j._
import twitter4j.auth.AccessToken
import scala.collection.JavaConversions._
import com.typesafe.config.ConfigFactory

object TwitterClient extends App {
	val log = LoggerFactory.getLogger(getClass)

	val conf = ConfigFactory.load()
	val oauthConsumerKey = conf.getString("oauthConsumerKey")
	val oauthConsumerSecret = conf.getString("oauthConsumerSecret")
	val userAccessToken = conf.getString("accessToken")
	val userAccessTokenSecret = conf.getString("accessTokenSecret")	


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