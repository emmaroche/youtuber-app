package utils

import models.Youtuber
import models.Video

object Utilities {

    //NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(youtubersToFormat: List<Youtuber>): String =
        youtubersToFormat
            .joinToString(separator = "\n") { youtuber ->  "$youtuber" }

    @JvmStatic
    fun formatSetString(itemsToFormat: Set<Video>): String =
        itemsToFormat
            .joinToString(separator = "\n") { video ->  "\t$video" }

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

}
