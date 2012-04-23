import org.scalatest.{FunSpec, GivenWhenThen, BeforeAndAfterEach}
import org.scalatest.mock.MockitoSugar
import org.mockito.BDDMockito
import org.mockito.Matchers._

import twitter4j.{Twitter, ResponseList, Paging, Status}
import twitter.Twitter4JTwitterClient

import Prototype._
import Values._

class Twitter4JTwitterClientSpec extends FunSpec with GivenWhenThen with BeforeAndAfterEach with MockitoSugar {
	
	var twitter: Twitter = _
	var responseList: ResponseList[Status] = _

	override def beforeEach() {
		twitter = mock[Twitter]
		responseList = mock[ResponseList[Status]]
	}

	describe("The client") {
		it("Should return an empty list when there are no mentions") {
			given("the underlying Twitter client returns no mentions")

			BDDMockito.given(responseList.iterator()).willReturn(emptyJavaIterator[Status]())
			BDDMockito.given(twitter.getMentions(any(classOf[Paging]))).willReturn(responseList)

			val client = new Twitter4JTwitterClient(twitter)

			when("mentions are fetched")

			val mentions = client.fetchMentions(AnyTweetId)

			then("the returned list must be empty")

			assert(mentions.isEmpty)
		}

		it("Should return single mention") {
			given("the underlying Twitter client returns a mention")


			when("mentions are fetched")


			then("the returned list contains the single mention")
		}

		it("Should return mentions since the specified Tweet Id") (pending)
	}
}