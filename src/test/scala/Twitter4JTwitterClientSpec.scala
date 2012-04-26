import org.scalatest.{FunSpec, GivenWhenThen, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.BDDMockito
import org.mockito.Matchers._
import scala.collection.immutable

import twitter4j.{Twitter, ResponseList, Paging, Status}
import twitter.Twitter4JTwitterClient

import Prototype._
import Values._

class Twitter4JTwitterClientSpec extends FunSpec with GivenWhenThen with BeforeAndAfterEach with MockitoSugar with ShouldMatchers {
	
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

			mentions should be ('empty)
		}

		it("Should return single mention") {
			given("the underlying Twitter client returns a single mention")

			val status = twitterStatus(id = 1, urls = List("http://www.google.com"), tags = List("google", "search"))
			BDDMockito.given(responseList.iterator()).willReturn(singleItemJavaIterator[Status](status))
			BDDMockito.given(twitter.getMentions(any(classOf[Paging]))).willReturn(responseList)


			val client = new Twitter4JTwitterClient(twitter)

			when("mentions are fetched")

			val mentions = client.fetchMentions(AnyTweetId)

			then("the returned list contains the single mention")

			mentions should have length (1)

			// TODO: Confusion over naming of tweet/mention. Reconcile this.
			val mention = mentions(0)
			mention.id should equal (1)
			// Contains only matcher?
			mention.url should equal ("http://www.google.com")
			mention.tags should equal (immutable.Set("google", "search")) 
		}

		it("Should return mentions since the specified Tweet Id") (pending)
	}
}