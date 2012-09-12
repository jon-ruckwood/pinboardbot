package net.selfdotlearn.pinboardbot

import org.scalatest.FunSpec
import org.scalatest.GivenWhenThen
import org.scalatest.BeforeAndAfterAll
import org.mockito.{ BDDMockito, Mockito }
import org.mockito.Matchers._

import akka.testkit.{ TestKit, TestActorRef, ImplicitSender }
import akka.actor.{ Actor, ActorSystem, Props }

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
			given("the twitter client does not return any mentions")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List())

			when("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			then("no Mention messages are sent")

			expectNoMsg()
		}

		it("Should send multiple mentions to the sender") {
			given("the twitter client returns multiple mentions")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set("google", "search")),
				new Tweet(id = 2, url = Some("http://www.twitter.com"), tags = Set("twitter", "social")))
			)

			when("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			then("multiple Mention messages are sent")

			expectMsgAllOf(
				Mention(id = 1, url = "http://www.google.com", tags = Set("google", "search")),
				Mention(id = 2, url = "http://www.twitter.com", tags = Set("twitter", "social"))
			)
		}

		it("Should drop mentions without a url present") {
			given("the twitter client returns a mention without a url")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = None, tags = AnyTags))
			)

			when("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			then("a Mention message is not sent")

			expectNoMsg()
		}

		it("Should not drop mentions without any tags") {
			given("the twitter client returns a mention without tags")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set()))
			)

			when("a FetchTweets message is received")

			twitterActorRef ! FetchMentions

			then("a Mention message is sent")

			expectMsg(Mention(id = 1, url = "http://www.google.com", tags = Set()))
		}

		it("Should record the id of the last Tweet fetched") {
			given("the twitter client returns mentions")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set("google", "search")),
				new Tweet(id = 2, url = Some("http://www.github.com"), tags = Set("git", "social")),
				new Tweet(id = 3, url = Some("http://www.twitter.com"), tags = Set("twitter", "social")))
			)

			when("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			then("the id of the last tweet is recorded")

			assert(twitterActorRef.underlyingActor.lastTweetId == 3)
		}

		it("Should use the id of the last Tweet fetched when the next FetchMentions message is received") {
			given("the id of the last Tweet fetched has been set")

			val lastTweetId = 10
			twitterActorRef.underlyingActor.lastTweetId = lastTweetId

			when("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			then("the id of the last tweet is used to fetch tweets")

			Mockito.verify(twitterClient).fetchMentions(lastTweetId)
		}
		
		it("Should start fetching mentions from tweet id 1")  (pending)

		// TODO: This should be an integration test/acceptance test
		it("Should only get new mentions since last time it ran after restart") (pending)
	}
}