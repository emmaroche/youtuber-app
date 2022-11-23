package controllers

import persistence.Serializer
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

    // ----------------------------------------------
    //  COUNTING METHODS FOR YOUTUBER ArrayList
    // ----------------------------------------------
    fun numberOfYoutubers() = youtubers.size

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
