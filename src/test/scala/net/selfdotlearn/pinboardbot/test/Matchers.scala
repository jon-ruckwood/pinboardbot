package net.selfdotlearn.pinboardbot.test

import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.HavePropertyMatchResult	

import net.selfdotlearn.pinboardbot.twitter.Tweet	

object Matchers {
	
	trait TweetMatcher {
		def id(expected: Long) = new HavePropertyMatcher[Tweet, Long] {
			def apply(tweet: Tweet) = HavePropertyMatchResult(
				tweet.id == expected,
				"id",
				expected,
				tweet.id)
		}

		def url(expected: Option[String]) = new HavePropertyMatcher[Tweet, Option[String]] {
			def apply(tweet: Tweet) = HavePropertyMatchResult(
				tweet.url == expected,
				"url",
				expected,
				tweet.url)
		}	

		def tags(expected: Set[String]) = new HavePropertyMatcher[Tweet, Set[String]] {
			def apply(tweet: Tweet) = HavePropertyMatchResult(
				tweet.tags == expected,
				"tags",
				expected,
				tweet.tags)
		}				
	}
}
