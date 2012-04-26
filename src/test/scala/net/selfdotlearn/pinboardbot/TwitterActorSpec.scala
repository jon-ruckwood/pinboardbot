package net.selfdotlearn.pinboardbot

import org.scalatest.FunSpec
import org.scalatest.GivenWhenThen

class TwitterActorSpec extends FunSpec with GivenWhenThen {
	
	describe("TwitterActor") {
		it("Should get mentions when a FetchTweets message is received") (pending)
		it("Should only get new mentions since last time it ran") (pending)
		it("Should only get new mentions since last time it ran after restart") (pending)
		it("Should send each mention back to the sender") (pending)
	}
}