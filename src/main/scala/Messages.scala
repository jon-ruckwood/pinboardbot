
object Messages {

	sealed trait Message
	case object PollTwitter extends Message
	case object FetchTweets extends Message
	case class Tweet(id: Long, url: String, tags: Set[String]) extends Message

}
