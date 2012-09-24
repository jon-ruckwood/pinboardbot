package net.selfdotlearn.pinboardbot

import akka.actor.{ Actor, ActorLogging }

import net.selfdotlearn.pinboardbot.Messages.Mention
import net.selfdotlearn.pinboardbot.pinboard.{PinboardClient, Bookmark}

class PinboardActor(private val client : PinboardClient) extends Actor with ActorLogging {

	def receive = {
		// TODO: Description is required.
		case Mention(id, url, tags) ⇒ client.add(new Bookmark(url, "Posted via API", tags))
	}
}
