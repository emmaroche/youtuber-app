package models

data class Video(
    var videoId: Int = 0,
    var videoTitle: String,
    var isVideoLiked: String,
    var videoCategory: String,
    var watchedStatus: String,
    var videoRating: Int)
