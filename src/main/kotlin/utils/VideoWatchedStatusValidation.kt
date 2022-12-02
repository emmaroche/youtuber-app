package utils

object VideoWatchedStatusValidation {

    @JvmStatic
    val watched = setOf ("Yet to watch", "Watched")

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
