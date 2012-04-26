package net.selfdotlearn.pinboardbot

object Messages {

	sealed trait Message
	case object PollTwitter extends Message
	case object FetchTweets extends Message
	// TODO: Naming still too Twitter specific?
	case class Mention(id: Long, url: String, tags: Set[String]) extends Message

}
