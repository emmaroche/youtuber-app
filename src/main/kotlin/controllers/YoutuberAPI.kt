package controllers

import persistence.Serializer
import utils.Utilities.formatListString
import models.Youtuber
import utils.ScannerInput

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


    // ----------------------------------------------
    //  COUNTING METHODS FOR YOUTUBER ArrayList
    // ----------------------------------------------
    fun numberOfYoutubers() = youtubers.size

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
