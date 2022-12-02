package models

import roundTwoDecimals
import utils.Utilities.formatSetString

data class Youtuber(var youtuberId: Int = 0,
                    var youtuberName: String,
                    var youtuberChannelName: String,
                    var youtuberYearJoined: Int,
                    var youtuberSubscribers: Int,
                    var subscribedToYoutuber: Boolean = false,
                    var isFavouriteYoutuber: Boolean = false,
                    var videos : MutableSet<Video> = mutableSetOf()) {

    // functions to manage the video set will go in here
    private var lastVideoId = 0
    private fun getVideoId() = lastVideoId++

    // ----------------------------------------------
    //  CRUD METHODS
    // ----------------------------------------------
    fun addVideo(video: Video): Boolean {
        video.videoId = getVideoId()
        return videos.add(video)
    }

    fun numberOfVideos() = videos.size

    fun findOne(id: Int): Video? {
        return videos.find { video -> video.videoId == id }
    }

    fun delete(id: Int): Boolean {
        return videos.removeIf { video -> video.videoId == id }
    }

    fun update(id: Int, newVideo: Video): Boolean {
        val foundVideo = findOne(id)

        //if the object exists, use the details passed in the newVideo parameter to
        //update the found object in the Set
        if (foundVideo != null) {
            foundVideo.videoTitle = newVideo.videoTitle
            foundVideo.isVideoLiked = newVideo.isVideoLiked
            foundVideo.videoCategory = newVideo.videoCategory
            foundVideo.watchedStatus = newVideo.watchedStatus
            foundVideo.videoRating = newVideo.videoRating
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    fun listVideos() =
        if (videos.isEmpty()) "\tNo videos added to this YouTuber"
        else formatSetString(videos)


    // ----------------------------------------------
    //  MARK VIDEO AS WATCHED METHOD
    // ----------------------------------------------

    fun markingVideoAsWatched(id: Int): Boolean {
        val foundVideo= findOne(id)
        if ((foundVideo != null) && (!foundVideo.markVideoAsWatched))
        {
            foundVideo.markVideoAsWatched = true
            //Changes watched status of video to 'Watched'
            foundVideo.watchedStatus = ("Watched")
            return true
        }
        return false
    }

}




