import org.scalatest.FunSpec
import org.scalatest.GivenWhenThen
import org.scalatest.mock.MockitoSugar
import org.mockito.BDDMockito
import org.mockito.Matchers._

import twitter4j.{Twitter, ResponseList, Paging, Status}
import twitter.Twitter4JTwitterClient

import Prototype._
import Values._

class Twitter4JTwitterClientSpec extends FunSpec with GivenWhenThen with MockitoSugar {
	
	describe("The client") {
		it("Should return an empty list when there are no mentions") {
			given("the underlying Twitter client returns no mentions")

			val twitter = mock[Twitter]
			val responseList = mock[ResponseList[Status]]

			BDDMockito.given(twitter.getMentions(any(classOf[Paging]))).willReturn(responseList)

			BDDMockito.given(responseList.iterator()).willReturn(emptyJavaIterator[Status]())

			val client = new Twitter4JTwitterClient(twitter)

			when("mentions are fetched")

			val mentions = client.fetchMentions(AnyTweetId)

			then("the returned list must be empty")

			assert(mentions.isEmpty)
		}

		it("Should return mentions since the specified Tweet Id") (pending)
	}
}