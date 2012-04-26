package net.selfdotlearn.pinboardbot.test

import org.scalatest.{FunSpec, GivenWhenThen, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

object Specs {
	trait UnitTestSpec extends FunSpec with GivenWhenThen with BeforeAndAfterEach with MockitoSugar with ShouldMatchers 
}
