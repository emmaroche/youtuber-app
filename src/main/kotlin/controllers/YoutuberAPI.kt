package controllers

import models.Youtuber
import persistence.Serializer
import utils.Utilities.formatListString

/**
 * This class manages an ArrayList of YouTuber (single responsibility is to manage an ArrayList of YouTuber objects.)
 *
 * @author Emma Roche
 */
class YoutuberAPI(serializerType: Serializer) {
    /**
     * @constructor This constructor sets up the YouTuber to use a Serializer. The XML Serializer is being used.
     */

    private var serializer: Serializer = serializerType
    private var youtubers = ArrayList<Youtuber>()
    // ----------------------------------------------
    //  For managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR YOUTUBER ARRAYLIST
    // ----------------------------------------------
    /**
     * This function is for adding a YouTuber to the arraylist.
     */
    fun add(youtuber: Youtuber): Boolean {
        youtuber.youtuberId = getId()
        return youtubers.add(youtuber)
    }

    /**
     * This function is for updating a YouTuber in an arraylist.
     */
    fun update(id: Int, youtuber: Youtuber?): Boolean {
        // find the youtuber object by the ID
        val foundYoutuber = findYoutuber(id)

        // if the youtuber exists, use the youtuber details passed as parameters to update the found youtuber in the ArrayList
        if ((foundYoutuber != null) && (youtuber != null)) {
            foundYoutuber.youtuberName = youtuber.youtuberName
            foundYoutuber.youtuberChannelName = youtuber.youtuberChannelName
            foundYoutuber.youtuberYearJoined = youtuber.youtuberYearJoined
            foundYoutuber.youtuberSubscribers = youtuber.youtuberSubscribers
            foundYoutuber.subscribedToYoutuber = youtuber.subscribedToYoutuber
            return true
        }
        // if the youtuber was not found, return false, indicating that the update was not successful
        return false
    }

    /**
     * This function is for deleting a YouTuber in an arraylist.
     */
    fun delete(id: Int) = youtubers.removeIf { youtuber -> youtuber.youtuberId == id }

    // ----------------------------------------------
    //  LISTING METHODS FOR YOUTUBER ARRAYLIST
    // ----------------------------------------------
    /**
     * This function is for listing all YouTubers in an arraylist.
     */
    fun listAllYoutubers() =
        if (youtubers.isEmpty()) "\n No YouTubers stored"
        else formatListString(youtubers)

    /**
     * This function is for listing YouTubers in an arraylist that are added to favourites.
     */
    fun listFavouriteYoutubers(): String =
        if (numberOfFavouriteYoutubers() == 0) "\n No YouTubers stored as favourites"
        else formatListString(youtubers.filter { youtubers -> youtubers.isFavouriteYoutuber })

    /**
     * This function is for listing YouTubers in an arraylist that are not added to favourites.
     */
    fun listNonFavouriteYoutubers(): String =
        if (numberOfNonFavouriteYoutubers() == 0) "\n No YouTubers stored as non favourites"
        else formatListString(youtubers.filter { youtubers -> !youtubers.isFavouriteYoutuber })

    /**
     * This function is for listing YouTubers in an arraylist in order of their subscriber count.
     */
    fun listYoutubersInOrderOfSubCount(): String =
        if (youtubers.isEmpty()) "\n No YouTubers stored"
        else formatListString(youtubers.sortedByDescending { youtubers -> youtubers.youtuberSubscribers })

    /**
     * This function is for listing YouTubers in an arraylist in order of the newest channels to the oldest channels.
     */
    fun listYoutubersFromNewestToOldestChannel(): String =
        if (numberOfYoutubers() == 0) "\n No YouTubers stored"
        else formatListString(youtubers.sortedBy { youtubers -> youtubers.youtuberYearJoined })

    // ----------------------------------------------
    //  COUNTING METHODS FOR YOUTUBER ARRAYLIST
    // ----------------------------------------------
    /**
     * This function is for counting the number of all YouTubers in an arraylist.
     */
    fun numberOfYoutubers() = youtubers.size

    /**
     * This function is for counting the number of YouTubers in an arraylist that are added to favourites.
     */
    fun numberOfFavouriteYoutubers(): Int = youtubers.count { youtubers: Youtuber -> youtubers.isFavouriteYoutuber }

    /**
     * This function is for counting the number of YouTubers in an arraylist that are added to non favourites.
     */
    fun numberOfNonFavouriteYoutubers(): Int = youtubers.count { youtubers: Youtuber -> !youtubers.isFavouriteYoutuber }

    /**
     * This function is for counting the number of YouTubers in an arraylist by the inputted subscriber count.
     */
    fun numberOfYoutubersBySubCount(sub: Int): Int = youtubers.count { youtubers: Youtuber -> youtubers.youtuberSubscribers == sub }

    // ----------------------------------------------
    //  ADD YOUTUBER TO FAVOURITES METHOD
    // ----------------------------------------------
    /**
     * This function is for adding a YouTuber in an arraylist to favourites.
     */
    fun addYoutuberToFavourites(id: Int): Boolean {
        val foundYoutuber = findYoutuber(id)
        if ((foundYoutuber != null) && (!foundYoutuber.isFavouriteYoutuber)
        ) {
            foundYoutuber.isFavouriteYoutuber = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  SEARCHING/FINDING METHODS FOR YOUTUBER ARRAYLIST
    // ---------------------------------------------
    /**
     * This function is for finding a YouTuber in an arraylist.
     */
    fun findYoutuber(youtuberId: Int) = youtubers.find { youtuber -> youtuber.youtuberId == youtuberId }

    /**
     * This function is for searching for a YouTuber in an arraylist by channel name.
     */
    fun searchYoutuberByChannelName(searchString: String) =
        formatListString(youtubers.filter { youtubers -> youtubers.youtuberChannelName.contains(searchString, ignoreCase = true) })

    /**
     * This function is for searching for a YouTuber in an arraylist by subscriber count.
     */
    fun searchYoutuberBySubCount(sub: Int): String =
        if (youtubers.isEmpty()) "\n No YouTubers found"
        else {
            val listOfSubs = formatListString(youtubers.filter { youtubers -> youtubers.youtuberSubscribers >= sub })
            if (listOfSubs == "") "\n No YouTubers with $sub or more subscribers found\n"
            else "\n $listOfSubs"
        }

    // ----------------------------------------------
    //  LISTING METHODS FOR VIDEO ARRAYLIST
    // ----------------------------------------------
    /**
     * This function is for listing the watched Videos in an arraylist by title.
     */
    fun listWatchedVideos(): String =

        if (numberOfYoutubers() == 0) "\n No videos stored"
        else {
            // displays the colour
            val red = "\u001b[31m"
            // displays the decoration
            val bold = "\u001b[1m"
            // resets colour and decoration back to what it previously was
            val reset = "\u001b[0m"
            var listOfWatched = " "
            for (youtube in youtubers) {
                for (video in youtube.videos) {
                    if (video.markVideoAsWatched) {
                        listOfWatched += "$red$bold" + video.videoTitle + "$reset: " + video.watchedStatus + "\n"
                    }
                }
            }
            listOfWatched
        }

    // ----------------------------------------------
    //  SEARCHING METHODS FOR VIDEO ARRAYLIST
    // ----------------------------------------------
    /**
     * This function is for searching for a Video in an arraylist by title.
     */
    fun searchVideoByTitle(searchString: String): String {
        // displays the colour
        val red = "\u001b[31m"
        val backgroundBrightRed = "\u001b[41;1m"
        // displays the decoration
        val bold = "\u001b[1m"
        // resets colour and decoration back to what it previously was
        val reset = "\u001b[0m"
        return if (numberOfYoutubers() == 0) "\n No videos stored"
        else {
            var listOfCats = ""
            for (youtube in youtubers) {
                for (video in youtube.videos) {
                    if (video.videoTitle.contains(searchString, ignoreCase = true)) {
                        listOfCats += "\n$backgroundBrightRed $reset$red$bold Video ${video.videoId}$reset\n" +
                            "$red$bold↳ Title:$reset ${video.videoTitle}\t$red$bold Interaction:$reset ${video.isVideoLiked}\t$red$bold Category:$reset ${video.videoCategory}\t$red$bold Watched status:$reset ${video.watchedStatus}\t$red$bold Rating:$reset ${video.videoRating} \n"
                    }
                }
            }
            if (listOfCats == "") "\n No videos found with title: $searchString"
            else listOfCats
        }
    }

    /**
     * This function is for searching for a Video in an arraylist by category.
     */
    fun searchVideoByCategory(searchString: String): String {
        // displays the colour
        val red = "\u001b[31m"
        val backgroundBrightRed = "\u001b[41;1m"
        // displays the decoration
        val bold = "\u001b[1m"
        // resets colour and decoration back to what it previously was
        val reset = "\u001b[0m"
        return if (numberOfYoutubers() == 0) "\n No videos stored"
        else {
            var listOfCats = ""
            for (youtube in youtubers) {
                for (video in youtube.videos) {
                    if (video.videoCategory.contains(searchString, ignoreCase = true)) {
                        listOfCats += "\n$backgroundBrightRed $reset$red$bold Video ${video.videoId}$reset\n" +
                            "$red$bold↳ Title:$reset ${video.videoTitle}\t$red$bold Interaction:$reset ${video.isVideoLiked}\t$red$bold Category:$reset ${video.videoCategory}\t$red$bold Watched status:$reset ${video.watchedStatus}\t$red$bold Rating:$reset ${video.videoRating} \n"
                    }
                }
            }
            if (listOfCats == "") "\n No videos found with category: $searchString"
            else listOfCats
        }
    }

    // ----------------------------------------------
    //  COUNTING METHODS FOR VIDEO ARRAYLIST
    // ----------------------------------------------
    /**
     * This function is for counting videos in an arraylist that are marked as watched.
     */
    fun numberOfWatchedVideos(): Int {
        var numberOfWatchedVideos = 0
        for (youtube in youtubers) {
            for (video in youtube.videos) {
                if (video.markVideoAsWatched) {
                    numberOfWatchedVideos++
                }
            }
        }
        return numberOfWatchedVideos
    }

    // ----------------------------------------------
    //  READ AND WRITE METHODS
    // ---------------------------------------------
    /**
     * This function is for loading YouTubers from an arraylist.
     */
    @Throws(Exception::class)
    fun load() {
        youtubers = serializer.read() as ArrayList<Youtuber>
    }

    /**
     * This function is for storing YouTubers from an arraylist.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(youtubers)
    }
}
