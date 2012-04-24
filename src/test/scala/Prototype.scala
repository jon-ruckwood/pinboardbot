import java.{ util => ju, lang => jl }
import scala.collection.immutable
import scala.collection.mutable
import scala.collection.JavaConversions._
import twitter4j.{Status, HashtagEntity}

/**
 * Factory for objects useful for writing tests
 */ 
object Prototype {

	def emptyJavaIterator[T]() = {
		new ju.Iterator[T] {
				override def hasNext = false
				override def next()  = throw new ju.NoSuchElementException()
				override def remove() = throw new jl.UnsupportedOperationException()
			}
	}

	def singleItemJavaIterator[T](item: T) = {
		val list = new ju.ArrayList[T]()
		list.add(item)
		list.iterator()
	}

	// TODO: Add urls and tags into Status
	def twitterStatus(id: Long, urls: List[String], tags: List[String]) = {
		new Status {
			override def getId() = id

			override def getHashtagEntities() = {
				val entities = new mutable.ListBuffer[HashtagEntity]
				tags.foreach { tag => 
					val entity = new HashtagEntity {
						// TODO: Override
					}
				}

				// TODO: Return Java array

			}
			

			override def getURLEntities() = throw new jl.UnsupportedOperationException()

			override def compareTo(other: Status) = 0

			override def getAnnotations() = throw new jl.UnsupportedOperationException()
			override def getContributors() = throw new jl.UnsupportedOperationException()
			override def getCreatedAt() = throw new jl.UnsupportedOperationException()
			override def getGeoLocation() = throw new jl.UnsupportedOperationException()
			override def getInReplyToScreenName() = throw new jl.UnsupportedOperationException()
			override def getInReplyToStatusId() = throw new jl.UnsupportedOperationException()
			override def getInReplyToUserId() = throw new jl.UnsupportedOperationException()
			override def getPlace() = throw new jl.UnsupportedOperationException()
			override def getRetweetCount() = throw new jl.UnsupportedOperationException()
			override def getRetweetedStatus() = throw new jl.UnsupportedOperationException()
			override def getSource() = throw new jl.UnsupportedOperationException()
			override def getText() = throw new jl.UnsupportedOperationException()
			override def getUser() = throw new jl.UnsupportedOperationException()
			override def isFavorited() = throw new jl.UnsupportedOperationException()
			override def isRetweet() = throw new jl.UnsupportedOperationException()
			override def isRetweetedByMe() = throw new jl.UnsupportedOperationException()
			override def isTruncated() = throw new jl.UnsupportedOperationException()			
			override def getAccessLevel() = throw new jl.UnsupportedOperationException()
			override def getRateLimitStatus() = throw new jl.UnsupportedOperationException()
			override def getMediaEntities() = throw new jl.UnsupportedOperationException()
			override def getUserMentionEntities() = throw new jl.UnsupportedOperationException()
		}	
	}
}