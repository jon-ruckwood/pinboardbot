package net.selfdotlearn.pinboardbot

import scala.collection.immutable
import org.mockito.BDDMockito
import org.mockito.Matchers._

import twitter4j.{Twitter, ResponseList, Paging, Status}

import net.selfdotlearn.pinboardbot.twitter.Twitter4JTwitterClient
import net.selfdotlearn.pinboardbot.test.Specs.UnitTestSpec
import net.selfdotlearn.pinboardbot.test.Prototype._
import net.selfdotlearn.pinboardbot.test.Values._
import net.selfdotlearn.pinboardbot.test.Matchers._

class Twitter4JTwitterClientSpec extends UnitTestSpec with TweetMatcher {
	
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
			val tweet = mentions(0)

			tweet should have (
				id (1),
				url("http://www.google.com"),
				tags(immutable.Set("google", "search"))
			)
		}

		it("Should return mentions since the specified Tweet Id") (pending)
	}
}
