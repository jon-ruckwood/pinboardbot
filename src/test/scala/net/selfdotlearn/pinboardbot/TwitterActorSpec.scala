package net.selfdotlearn.pinboardbot

import org.scalatest.FunSpec
import org.scalatest.GivenWhenThen
import org.scalatest.BeforeAndAfterAll
import org.mockito.{BDDMockito, Mockito}

import akka.testkit.TestActorRef
import akka.testkit.TestKit
import akka.actor.{Actor, ActorSystem, Props}

import net.selfdotlearn.pinboardbot.test.Values._
import net.selfdotlearn.pinboardbot.test.Specs.UnitTestSpec

import net.selfdotlearn.pinboardbot.twitter.{TwitterClient, Tweet}
import net.selfdotlearn.pinboardbot.Messages.{FetchMentions, Mention}

class TwitterActorSpec extends TestKit(ActorSystem()) with UnitTestSpec with BeforeAndAfterAll {
	

	describe("TwitterActor") {
		it("Should send each mentions to the sender") {
			given("The twitter client returns mentions")

			val twitterClient = mock[TwitterClient]
			BDDMockito.given(twitterClient.fetchMentions(1)).willReturn(List(
				new Tweet(id = 1, url = Some("http://www.google.com"), tags = Set("google", "search")),
				new Tweet(id = 2, url = Some("http://www.twitter.com"), tags = Set("twitter", "social")))
			)

			val twitterActorRef = TestActorRef(new TwitterActor(twitterClient))

			when("A FetchTweets message is recieved")

			twitterActorRef ! FetchMentions

			then("Mentions are sent to the sender")

			// TODO: Determine why this expectation does not hold
			/*
			expectMsgAllOf(
				Mention(id = 1, url = "http://www.google.com", tags = Set("google", "search")),
				Mention(id = 2, url = "http://www.twitter.com", tags = Set("twitter", "social"))
			)*/
		}

		it("Should not send any mentions when there are none") (pending)
		it("Should drop mentions without a url present") (pending)
		it("Should only get new mentions since last time it ran") (pending)
		it("Should only get new mentions since last time it ran after restart") (pending)
	}

	override def afterAll {
    	system.shutdown()
  	}
}