package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.VideoWatchedStatusValidation.watched
import utils.VideoWatchedStatusValidation.videoWatchedStatus

class VideoWatchedStatusValidationTest {
    @Test
    fun likedVideoReturnsFulllikedVideoSet(){
        Assertions.assertEquals(3, watched.size)
        Assertions.assertTrue(watched.contains("Yet to watch"))
        Assertions.assertTrue(watched.contains("Watching"))
        Assertions.assertTrue(watched.contains("Watched"))

    }

    @Test
    fun isValidVideoLikeStatusTrueWhenCategoryExists(){
        Assertions.assertTrue(videoWatchedStatus("Yet to watch"))
        Assertions.assertTrue(videoWatchedStatus("yet to watch"))
        Assertions.assertTrue(videoWatchedStatus("YET TO WATCH"))
        Assertions.assertTrue(videoWatchedStatus("Watching"))
        Assertions.assertTrue(videoWatchedStatus("watching"))
        Assertions.assertTrue(videoWatchedStatus("WATCHING"))
        Assertions.assertTrue(videoWatchedStatus("Watched"))
        Assertions.assertTrue(videoWatchedStatus("watched"))
        Assertions.assertTrue(videoWatchedStatus("WATCHED"))
    }

    @Test
    fun isValidVideoLikeStatusFalseWhenLikedStatusDoesNotExist(){
        Assertions.assertFalse(videoWatchedStatus("WAchte"))
        Assertions.assertFalse(videoWatchedStatus("yt t watch"))
        Assertions.assertFalse(videoWatchedStatus("watcing"))
    }
}
