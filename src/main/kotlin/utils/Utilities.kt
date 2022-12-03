package utils

import models.Video
import models.Youtuber

object Utilities {

    // displays the colour
    private const val brightRed = "\u001b[31;1m"
    private const val red = "\u001b[31m"
    private const val backgroundBrightRed = "\u001b[41;1m"
    // displays the decoration
    private const val bold = "\u001b[1m"
    // resets colour and decoration back to what it previously was
    private const val reset = "\u001b[0m"

    @JvmStatic
    fun formatListString(youtubersToFormat: List<Youtuber>): String =
        youtubersToFormat
            .joinToString(separator = "\n") { youtuber ->
                "$brightRed \n" +
                    "   ###############################                                                         \n" +
                    "  ###################################                      \n" +
                    " #####################################                 \n" +
                    "################  #####################                    \n" +
                    "################    ###################                    \n" +
                    "################      #################                  \n" +
                    "################$reset $bold " + youtuber.youtuberId + " $reset$brightRed    ###############                    \n" +
                    "################      #################                    \n" +
                    "################    ###################                    \n" +
                    "################  ####################                    \n" +
                    " ####################################                     \n" +
                    "  ##################################                       \n" +
                    "   ################################                       \n" +
                    reset + youtuber.toString()
            }

    @JvmStatic
    fun formatSetString(itemsToFormat: MutableSet<Video>): String =
        itemsToFormat
            .joinToString(separator = "\n") { video ->
                "$backgroundBrightRed $reset$red$bold Video ${video.videoId}$reset\n" +
                    "$red$bold↳ Title:$reset ${video.videoTitle}\t$red$bold Interaction:$reset ${video.isVideoLiked}\t$red$bold Category:$reset ${video.videoCategory}\t$red$bold Watched status:$reset ${video.watchedStatus}\t$red$bold Rating:$reset ${video.videoRating} \n"
            }

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }
}
