
object Messages {

	sealed trait Message
	case object Start extends Message
	case object PollTwitter extends Message
	case class Tweet(id: Long, url: String, tags: Set[String]) extends Message

}
