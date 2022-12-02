package controllers

import persistence.Serializer
import utils.Utilities.formatListString
import models.Youtuber

class YoutuberAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var youtubers = ArrayList<Youtuber>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR YOUTUBER ArrayList
    // ----------------------------------------------

    //Add method
    fun add(youtuber: Youtuber): Boolean {
        youtuber.youtuberId = getId()
        return youtubers.add(youtuber)
    }

    //Update method
    fun update(id: Int, youtuber: Youtuber?): Boolean {
        // find the youtuber object by the ID
        val foundYoutuber = findYoutuber(id)

        // if the youtuber exists, use the youtuber details passed as parameters to update the found youtuber in the ArrayList.
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

    //Delete method
    fun delete(id: Int) = youtubers.removeIf { youtuber -> youtuber.youtuberId == id }

    // ----------------------------------------------
    //  LISTING METHODS FOR YOUTUBER ArrayList
    // ----------------------------------------------
    fun listAllYoutubers() =
        if (youtubers.isEmpty()) "No youtubers stored"
        else formatListString(youtubers)

    fun listNonFavouriteYoutubers(): String =
        if  (numberOfNonFavouriteYoutubers() == 0)  "No non favourite YouTubers stored\n"
        else formatListString(youtubers.filter { youtubers -> !youtubers.isFavouriteYoutuber})

    fun listFavouriteYoutubers(): String =
        if  (numberOfFavouriteYoutubers() == 0) "No favourite YouTubers stored\n"
        else formatListString(youtubers.filter { youtubers -> youtubers.isFavouriteYoutuber})

    fun listYoutubersInOrderOfSubCount(): String =
        if  (youtubers.isEmpty()) "No YouTubers stored\n"
        else formatListString(youtubers.sortedByDescending { youtubers -> youtubers.youtuberSubscribers})

   fun listYoutubersFromNewestToOldestChannel(): String =
    if  (numberOfYoutubers() == 0) "No YouTubers stored\n"
    else formatListString(youtubers.sortedBy { youtubers -> youtubers.youtuberYearJoined})

    // ----------------------------------------------
    //  COUNTING METHODS FOR YOUTUBER ArrayList
    // ----------------------------------------------
    fun numberOfYoutubers() = youtubers.size

    fun numberOfNonFavouriteYoutubers(): Int = youtubers.count { youtubers: Youtuber -> !youtubers.isFavouriteYoutuber}

    fun numberOfFavouriteYoutubers(): Int = youtubers.count { youtubers: Youtuber -> youtubers.isFavouriteYoutuber }

    fun numberOfNotesBySubCount(sub: Int): Int = youtubers.count { youtubers: Youtuber -> youtubers.youtuberSubscribers == sub }

    // ----------------------------------------------
    //  ADD YOUTUBER to FAVOURITES METHOD
    // ----------------------------------------------
    fun addYoutuberToFavourites(id: Int): Boolean {
        val foundYoutuber = findYoutuber(id)
        if (( foundYoutuber != null) && (!foundYoutuber.isFavouriteYoutuber)
        ){
            foundYoutuber.isFavouriteYoutuber = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findYoutuber(youtuberId : Int) =  youtubers.find{ youtuber -> youtuber.youtuberId == youtuberId }

    fun searchYoutuberByChannelName(searchString: String) =
        formatListString(youtubers.filter { youtubers -> youtubers.youtuberChannelName.contains(searchString, ignoreCase = true) })

    fun searchYoutuberBySubCount(sub: Int): String =
        if (youtubers.isEmpty()) "No YouTubers found"
        else {
            val listOfSubs = formatListString(youtubers.filter{ youtubers -> youtubers.youtuberSubscribers >= sub})
            if (listOfSubs.equals("")) "No YouTubers with $sub subscribers\n"
            else "${numberOfNotesBySubCount(sub)} Youtubers(s) with $sub subscribers\n: $listOfSubs"
        }

    // ----------------------------------------------
    //  Read and write methods
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
