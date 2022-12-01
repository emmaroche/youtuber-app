package utils

object VideoLikedValidation {

    //NOTE: JvmStatic annotation means that the categories variable is static i.e. we can reference it through the class

    @JvmStatic
    val likedVideo = setOf ("Yes", "No", "Y" , "N", "y", "n")

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
