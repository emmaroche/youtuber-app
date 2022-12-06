package models

/**
 * This class provides a Video model - a new model where each YouTuber can have zero or many Videos (0-M relationship)
 *
 * @author Emma Roche
 */

data class Video(
    var videoId: Int = 0,
    var videoTitle: String,
    var isVideoLiked: String,
    var videoCategory: String,
    var watchedStatus: String,
    var videoRating: Int,
    var markVideoAsWatched: Boolean = false
)
