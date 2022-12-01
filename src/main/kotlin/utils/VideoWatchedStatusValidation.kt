package utils

object VideoWatchedStatusValidation {

    @JvmStatic
    val watched = setOf ("Yet to watch", "Watching", "Watched")

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
