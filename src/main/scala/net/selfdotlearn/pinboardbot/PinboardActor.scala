package net.selfdotlearn.pinboardbot

import akka.actor.{ Actor, ActorLogging }

import net.selfdotlearn.pinboardbot.Messages.Mention

class PinboardActor extends Actor with ActorLogging {

	def receive = {
		case mention: Mention ⇒ log.info("Received mention: {}", mention)
	}
}
