package utils

import models.Video
import models.Youtuber

/**
 * This class provides formatting for how the listing of Youtubers and Videos will print to the console.
 * This class also provides a validation method to check that an input is within a valid range.
 *
 *
 * @author Emma Roche
 */

object Utilities {

    // displays the colour
    private const val brightRed = "\u001b[31;1m"
    private const val red = "\u001b[31m"
    private const val backgroundBrightRed = "\u001b[41;1m"
    // displays the decoration
    private const val bold = "\u001b[1m"
    // resets colour and decoration back to what it previously was
    private const val reset = "\u001b[0m"

    /**
     * This function formats how the YouTubers are listed and printed to the console
     */
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

    /**
     * This function formats how the Videos are listed and printed to the console
     */
    @JvmStatic
    fun formatSetString(itemsToFormat: MutableSet<Video>): String =
        itemsToFormat
            .joinToString(separator = "\n") { video ->
                "\n$backgroundBrightRed $reset$red$bold Video ${video.videoId}$reset\n" +
                    "$red$bold↳ Title:$reset ${video.videoTitle}\t$red$bold Interaction:$reset ${video.isVideoLiked}\t$red$bold Category:$reset ${video.videoCategory}\t$red$bold Watched status:$reset ${video.watchedStatus}\t$red$bold Rating:$reset ${video.videoRating} \n"
            }

    /**
     * This function validates that the input in a field is within a valid range.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }
}
