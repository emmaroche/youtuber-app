package utils

/**
 * This class provides validation for the liked status a user can enter when adding or updating a Video.
 *
 * @author Emma Roche
 */
object VideoLikedValidation {

    /**
     * This function sets the liked statuses available for a user to choose from when entering a video interaction.
     */
    @JvmStatic
    val likedVideo = setOf("Liked", "Disliked", "None")

    /**
     * This function validates that the liked status entered for a video matches one of the set liked statuses.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
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
