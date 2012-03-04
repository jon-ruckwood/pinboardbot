import org.slf4j.LoggerFactory
import twitter4j._
import twitter4j.auth.AccessToken
import org.streum.configrity._

object TwitterClient extends App {
	val log = LoggerFactory.getLogger(getClass)

	val config = Configuration.load(System.getProperty("pinboardbotDeploymentConfig"))
	val oauthConsumerKey = config[String]("oauthConsumerKey")
	val oauthConsumerSecret = config[String]("oauthConsumerSecret")
	val accessToken = config[String]("accessToken")
	val accessTokenSecret = config[String]("accessTokenSecret")

	val twitterStream = new TwitterStreamFactory().getInstance()
	twitterStream.setOAuthConsumer(oauthConsumerKey, oauthConsumerSecret)

	val token = new AccessToken(accessToken, accessTokenSecret) 
	twitterStream.setOAuthAccessToken(token)

	twitterStream.addListener(new StatusListener() {
		def onStatus(status: Status) {
			log.info("{} - '{}'", status.getUser().getName(), status.getText())
		}

		def onDeletionNotice(notice: StatusDeletionNotice) {
			// 
		}

		def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
			// 
		}

		def onScrubGeo(userId: Long, upToStatusId: Long) {
			//
		}

		def onException(ex: Exception) {
			log.error("Exception: {}", ex)
		}
	})

	twitterStream.sample()
}