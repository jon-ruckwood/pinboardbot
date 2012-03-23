import org.scalatest.Spec
import org.scalatest.GivenWhenThen

class TwitterActorSpec extends Spec with GivenWhenThen {
	
	describe("TwitterActor") {
		it("Should get mentions when a FetchTweets message is received") (pending)
		it("Should only get new mentions since last time it ran") (pending)
		it("Should only get new mentions since last time it ran after restart") (pending)
		// TODO: Rename tweets -> mentions?
		it("Should send each mention back to the sender") (pending)
	}
}