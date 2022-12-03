package controllers

import models.Youtuber
import persistence.Serializer
import utils.Utilities.formatListString

class YoutuberAPI(serializerType: Serializer) {

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

    fun add(youtuber: Youtuber): Boolean {
        youtuber.youtuberId = getId()
        return youtubers.add(youtuber)
    }

//    fun create(youtuber: Youtuber) {
//        youtuber.youtuberId = getId()
//        youtubers.add(youtuber)
//    }

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

    fun delete(id: Int) = youtubers.removeIf { youtuber -> youtuber.youtuberId == id }

    // ----------------------------------------------
    //  LISTING METHODS FOR YOUTUBER ARRAYLIST
    // ----------------------------------------------
    fun listAllYoutubers() =
        if (youtubers.isEmpty()) "\n No YouTubers stored"
        else formatListString(youtubers)

    fun listFavouriteYoutubers(): String =
        if (numberOfFavouriteYoutubers() == 0) "\n No YouTubers stored as favourites"
        else formatListString(youtubers.filter { youtubers -> youtubers.isFavouriteYoutuber })

    fun listNonFavouriteYoutubers(): String =
        if (numberOfNonFavouriteYoutubers() == 0) "\n No YouTubers stored as non favourites"
        else formatListString(youtubers.filter { youtubers -> !youtubers.isFavouriteYoutuber })

    fun listYoutubersInOrderOfSubCount(): String =
        if (youtubers.isEmpty()) "\n No YouTubers stored"
        else formatListString(youtubers.sortedByDescending { youtubers -> youtubers.youtuberSubscribers })

    fun listYoutubersFromNewestToOldestChannel(): String =
        if (numberOfYoutubers() == 0) "\n No YouTubers stored"
        else formatListString(youtubers.sortedBy { youtubers -> youtubers.youtuberYearJoined })

    // ----------------------------------------------
    //  COUNTING METHODS FOR YOUTUBER ARRAYLIST
    // ----------------------------------------------
    fun numberOfYoutubers() = youtubers.size

    fun numberOfFavouriteYoutubers(): Int = youtubers.count { youtubers: Youtuber -> youtubers.isFavouriteYoutuber }

    fun numberOfNonFavouriteYoutubers(): Int = youtubers.count { youtubers: Youtuber -> !youtubers.isFavouriteYoutuber }

    fun numberOfNotesBySubCount(sub: Int): Int = youtubers.count { youtubers: Youtuber -> youtubers.youtuberSubscribers == sub }

    // ----------------------------------------------
    //  ADD YOUTUBER TO FAVOURITES METHOD
    // ----------------------------------------------
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
    fun findYoutuber(youtuberId: Int) = youtubers.find { youtuber -> youtuber.youtuberId == youtuberId }

    fun searchYoutuberByChannelName(searchString: String) =
        formatListString(youtubers.filter { youtubers -> youtubers.youtuberChannelName.contains(searchString, ignoreCase = true) })

    fun searchYoutuberBySubCount(sub: Int): String =
        if (youtubers.isEmpty()) "\n No YouTubers found"
        else {
            val listOfSubs = formatListString(youtubers.filter { youtubers -> youtubers.youtuberSubscribers >= sub })
            if (listOfSubs == "") "\n No YouTubers with $sub or more subscribers found\n"
            else "\n ${numberOfNotesBySubCount(sub)} Youtubers(s) with $sub or more subscribers\n: $listOfSubs"
        }

    // ----------------------------------------------
    //  LISTING METHODS FOR VIDEO ARRAYLIST
    // ----------------------------------------------
    fun listWatchedVideos(): String =
        if (numberOfYoutubers() == 0) "\n No videos stored"
        else {
            var listOfWatched = " "
            for (youtube in youtubers) {
                for (video in youtube.videos) {
                    if (video.markVideoAsWatched) {
                        listOfWatched += youtube.youtuberChannelName + ": " + video.videoTitle + ": " + video.watchedStatus + "\n"
                    }
                }
            }
            listOfWatched
        }

    // ----------------------------------------------
    //  SEARCHING METHODS FOR VIDEO ARRAYLIST
    // ----------------------------------------------

    fun searchVideoByTitle(searchString: String): String {
        return if (numberOfYoutubers() == 0) "\n No videos stored"
        else {
            var listOfCats = ""
            for (youtube in youtubers) {
                for (video in youtube.videos) {
                    if (video.videoTitle.contains(searchString, ignoreCase = true)) {
                        listOfCats += "${video}\n"
                    }
                }
            }
            if (listOfCats == "") "\n No videos found with title: $searchString"
            else listOfCats
        }
    }

    fun searchVideoByCategory(searchString: String): String {
        return if (numberOfYoutubers() == 0) "\n No videos stored"
        else {
            var listOfCats = ""
            for (youtube in youtubers) {
                for (video in youtube.videos) {
                    if (video.videoCategory.contains(searchString, ignoreCase = true)) {
                        listOfCats += "${video}\n"
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
    @Throws(Exception::class)
    fun load() {
        youtubers = serializer.read() as ArrayList<Youtuber>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(youtubers)
    }
}
