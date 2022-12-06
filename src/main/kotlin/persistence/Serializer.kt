package persistence

/**
 * This interface provides a Serializer
 *
 * @author Emma Roche
 */
interface Serializer {
    @Throws(Exception::class)
    fun write(obj: Any?)

    @Throws(Exception::class)
    fun read(): Any?
}
