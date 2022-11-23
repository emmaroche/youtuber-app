package models
data class Youtuber(var youtuberId: Int = 0,
                    var youtuberName: String,
                    var youtuberChannelName: String,
                    var youtuberYearJoined: Int,
                    var youtuberSubscribers: Int,
                    var subscribedToYoutuber: Boolean = false)
