package net.selfdotlearn.pinboardbot

import com.typesafe.config.Config
import org.slf4j.LoggerFactory
import scalaj.http.{Http, HttpOptions}

package pinboard {

	class Bookmark(val url: String, val description: String, val tags: Set[String])

	trait PinboardClient {
		def add(bookmark: Bookmark)
	}

	class SingleUserPinboardClient(val endpoint : String, val userApiToken: String) extends PinboardClient {
		private val log = LoggerFactory.getLogger(getClass)

		override def add(bookmark: Bookmark) = {
			val result = Http.get(endpoint + "/posts/add")
				.params("auth_token"	-> userApiToken,
						"url" 			-> bookmark.url,
						"description" 	-> bookmark.description,
						"tags" 			-> bookmark.tags.mkString(" "))
				.option(HttpOptions.connTimeout(1000))
				.option(HttpOptions.readTimeout(5000))
				.asString

      // TODO: Compilation error, review using slf4j directly
      //log.info("Result of adding '{}' was: {}", bookmark.url, result)
		}
	}

	object PinboardClientFactory { 
		def get(config: Config) = {
			val usersApiToken = config.getString("pinboard-api-token")
			val pinboardApiEndpoint = config.getString("pinboard-api-endpoint")

			new SingleUserPinboardClient(pinboardApiEndpoint, usersApiToken)
		}
	}	
}
