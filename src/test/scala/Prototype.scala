import java.{ util => ju, lang => jl }

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
}