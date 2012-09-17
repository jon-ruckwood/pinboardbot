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

		it("Should record the most recent id of all Tweets fetched") {
			given("the twitter client returns mentions out of order")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set("google", "search")),
				new Tweet(id = 3, url = Some("http://www.github.com"), tags = Set("git", "social")),
				new Tweet(id = 2, url = Some("http://www.twitter.com"), tags = Set("twitter", "social")))
			)

			when("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			then("the id of the tweet with the highest id is recorded")

			assert(twitterActorRef.underlyingActor.mostRecentTweetId == 3)
		}

		it("Should use the id of the most recent Tweet fetched when the next FetchMentions message is received") {
			given("the mostRecentTweetId has already been set")

			val mostRecentTweetId = 4
			twitterActorRef.underlyingActor.mostRecentTweetId = mostRecentTweetId

			when("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			then("the mostRecentTweetId is used to fetch tweets")

			Mockito.verify(twitterClient).fetchMentions(mostRecentTweetId)
		}

		it("Should record the most recent id of the given Tweets even when URLs are not present") {
			given("the twitter client returns mentions without URLs")

			BDDMockito.given(twitterClient.fetchMentions(anyLong())).willReturn(List(
				new Tweet(id = 5, url = None, tags = Set()),
				new Tweet(id = 7, url = None, tags = Set()),
				new Tweet(id = 6, url = None, tags = Set()))
			)

			when("a FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			then("the tweet with the most recent id is recorded")

			assert(twitterActorRef.underlyingActor.mostRecentTweetId == 7)
		}		
	}
}