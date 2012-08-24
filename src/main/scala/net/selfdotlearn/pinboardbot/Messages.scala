package net.selfdotlearn.pinboardbot

object Messages {

	sealed trait Message
	case object PollTwitter extends Message
	case object FetchMentions extends Message
	case class Mention(id: Long, url: String, tags: Set[String]) extends Message
}
