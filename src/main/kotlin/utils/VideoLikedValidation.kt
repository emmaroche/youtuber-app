package utils

object VideoLikedValidation {

    @JvmStatic
    val likedVideo = setOf ("Liked", "Disliked", "None")

    @JvmStatic
    fun isValidVideoLikeStatus(likedVideoToCheck: String?): Boolean {
        for (liked in likedVideo) {
            if (liked.equals(likedVideoToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}
