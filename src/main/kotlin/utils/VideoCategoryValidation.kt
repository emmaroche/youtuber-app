package utils

/**
 * This class provides validation for the categories a user can enter when adding or updating a Video.
 *
 * @author Emma Roche
 */

object VideoCategoryValidation {

    /**
     * This function sets the categories avaiable for a user to choose from when entering a video category.
     */
    @JvmStatic
    val categories = setOf("Gaming", "Music", "Tutorials", "Entertainment", "Beauty", "Comedy")

    /**
     * This function validates that the category entered for a video matches one of the set categories.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
    @JvmStatic
    fun isValidCategory(categoryToCheck: String?): Boolean {
        for (category in categories) {
            if (category.equals(categoryToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}
