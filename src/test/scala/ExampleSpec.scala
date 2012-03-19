import org.scalatest.Spec
import org.scalatest.GivenWhenThen
import scala.collection.mutable.Stack

// TODO: Remove this example
class ExampleSpec extends Spec with GivenWhenThen {

	describe("A Stack") {

		it("should pop values in last-in-first-out order") {
			given("a non-empty stack")
			val stack = new Stack[Int]
			stack.push(1)
			stack.push(2)
			val oldSize = stack.size

			when("when pop is invoked on the stack")
			val result = stack.pop()

			then("the most recently pushed element should be returned")
			assert(result === 2)

			and("the stack should have one less than before")
			assert(stack.size === oldSize - 1)
		}

		it("should throw NoSuchElementException if an empty stack is popped") {

			given("an empty stack")
			val emptyStack = new Stack[String]

			when("when pop is invoked on the stack")
			then("NoSuchElementException should be thrown")
			intercept[NoSuchElementException] {
				emptyStack.pop()
			}

			and("the stack should still be empty")
			assert(emptyStack.isEmpty)
		}
	}
}