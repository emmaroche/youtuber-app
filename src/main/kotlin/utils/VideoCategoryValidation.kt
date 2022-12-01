package utils

object VideoCategoryValidation {

    @JvmStatic
    val categories = setOf ("Gaming", "Music Videos", "Tutorials" , "Entertainment", "Beauty", "Comedy")

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
