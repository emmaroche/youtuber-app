package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.VideoLikedValidation.likedVideo
import utils.VideoLikedValidation.isValidVideoLikeStatus


internal class VideoLikedValidationTest {

    @Test
    fun likedVideoReturnsFulllikedVideoSet(){
        Assertions.assertEquals(2, likedVideo.size)
        Assertions.assertTrue(likedVideo.contains("Liked"))
        Assertions.assertTrue(likedVideo.contains("Disliked"))

    }

    @Test
    fun isValidVideoLikeStatusTrueWhenCategoryExists(){
        Assertions.assertTrue(isValidVideoLikeStatus("Liked"))
        Assertions.assertTrue(isValidVideoLikeStatus("LIKED"))
        Assertions.assertTrue(isValidVideoLikeStatus("liked"))
        Assertions.assertTrue(isValidVideoLikeStatus("Disliked"))
        Assertions.assertTrue(isValidVideoLikeStatus("disliked"))
        Assertions.assertTrue(isValidVideoLikeStatus("DISLIKED"))
    }

    @Test
    fun isValidVideoLikeStatusFalseWhenLikedStatusDoesNotExist(){
        Assertions.assertFalse(isValidVideoLikeStatus("likked"))
        Assertions.assertFalse(isValidVideoLikeStatus("dislkie"))
        Assertions.assertFalse(isValidVideoLikeStatus(" "))
    }
}
