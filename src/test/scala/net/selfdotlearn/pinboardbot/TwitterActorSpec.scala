package net.selfdotlearn.pinboardbot

import org.scalatest.BeforeAndAfterAll
import org.mockito.{ BDDMockito, Mockito }
import org.mockito.Matchers._

import akka.testkit.{ TestKit, TestActorRef, ImplicitSender }
import akka.actor.ActorSystem

import net.selfdotlearn.pinboardbot.test.Values._
import net.selfdotlearn.pinboardbot.test.Specs.UnitTestSpec

import net.selfdotlearn.pinboardbot.twitter.{ TwitterClient, Tweet }
import net.selfdotlearn.pinboardbot.Messages.{ FetchMentions, Mention }

class TwitterActorSpec extends TestKit(ActorSystem()) with ImplicitSender with UnitTestSpec with BeforeAndAfterAll {
	
	val twitterClient = mock[TwitterClient]
	val twitterActorRef = TestActorRef(new TwitterActor(twitterClient))

	override def afterAll {
    	system.shutdown()
  	}

	describe("TwitterActor") {
		it("Should not send any Mentions") {
			Given("the twitter client does not return any mentions")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List())

			When("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			Then("no Mention messages are sent")

			expectNoMsg()
		}

		it("Should send multiple mentions to the sender") {
			Given("the twitter client returns multiple mentions")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set("google", "search")),
				new Tweet(id = 2, url = Some("http://www.twitter.com"), tags = Set("twitter", "social")))
			)

			When("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			Then("multiple Mention messages are sent")

			expectMsgAllOf(
				Mention(id = 1, url = "http://www.google.com", tags = Set("google", "search")),
				Mention(id = 2, url = "http://www.twitter.com", tags = Set("twitter", "social"))
			)
		}

		it("Should drop mentions without a url present") {
			Given("the twitter client returns a mention without a url")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = None, tags = AnyTags))
			)

			When("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			Then("a Mention message is not sent")

			expectNoMsg()
		}

		it("Should not drop mentions without any tags") {
			Given("the twitter client returns a mention without tags")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set()))
			)

			When("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			Then("a Mention message is sent")

			expectMsg(Mention(id = 1, url = "http://www.google.com", tags = Set()))
		}

		it("Should record the most recent id of all Tweets fetched") {
			Given("the twitter client returns mentions out of order")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set("google", "search")),
				new Tweet(id = 3, url = Some("http://www.github.com"), tags = Set("git", "social")),
				new Tweet(id = 2, url = Some("http://www.twitter.com"), tags = Set("twitter", "social")))
			)

			When("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			Then("the id of the tweet with the highest id is recorded")

			assert(twitterActorRef.underlyingActor.mostRecentTweetId == 3)
		}

		it("Should use the id of the most recent Tweet fetched when the next FetchMentions message is received") {
			Given("the mostRecentTweetId has already been set")

			val mostRecentTweetId = 4
			twitterActorRef.underlyingActor.mostRecentTweetId = mostRecentTweetId

			When("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			Then("the mostRecentTweetId is used to fetch tweets")

			Mockito.verify(twitterClient).fetchMentions(mostRecentTweetId)
		}

		it("Should record the most recent id of the given Tweets even when URLs are not present") {
			Given("the twitter client returns mentions without URLs")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 5, url = None, tags = Set()),
				new Tweet(id = 7, url = None, tags = Set()),
				new Tweet(id = 6, url = None, tags = Set()))
			)

			When("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			Then("the tweet with the most recent id is recorded")

			assert(twitterActorRef.underlyingActor.mostRecentTweetId == 7)
		}		
	}
}
