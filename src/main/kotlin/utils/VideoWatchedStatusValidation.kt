package utils

/**
 * This class provides validation for the watched status a user can enter when adding or updating a Video.
 *
 * @author Emma Roche
 */
object VideoWatchedStatusValidation {

    /**
     * This function sets the watched statuses that are available for a user to choose from when entering a video watched status.
     */
    @JvmStatic
    val watched = setOf("Yet to watch", "Watched")

    /**
     * This function validates that the watched status entered for a video matches one of the set watched statuses.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
    @JvmStatic
    fun videoWatchedStatus(watchStatusToCheck: String?): Boolean {
        for (watch in watched) {
            if (watch.equals(watchStatusToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}
