package net.selfdotlearn.pinboardbot

import net.selfdotlearn.pinboardbot.test.Specs.UnitTestSpec

class TwitterActorSpec extends UnitTestSpec {
	
	describe("TwitterActor") {
		it("Should get mentions when a FetchTweets message is received") (pending)
		it("Should send each mention back to the sender") (pending)
		it("Should drop mentions without a url present") (pending)
		it("Should only get new mentions since last time it ran") (pending)
		it("Should only get new mentions since last time it ran after restart") (pending)
	}
}