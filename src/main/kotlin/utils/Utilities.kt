package utils

import models.Video
import models.Youtuber

object Utilities {

    @JvmStatic
    fun formatListString(youtubersToFormat: List<Youtuber>): String =
        youtubersToFormat
            .joinToString(separator = "\n") { youtuber ->  "$youtuber" }

    @JvmStatic
    fun formatSetString(itemsToFormat: MutableSet<Video>): String =
        itemsToFormat
            .joinToString(separator = "\n") { video ->  "\t$video" }

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

}
