package models

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class YoutuberTest {
    private var ksi: Youtuber? = null
    private var musicVideo: Video? = null
    private var pewdiepie: Youtuber? = null
    private var minecraftTutorial: Video? = null
    private var zerkaa: Youtuber? = null
    private var gta5PlayAlong: Video? = null
    private var mollyMae: Youtuber? = null
    private var makeupTutorial: Video? = null
    private var mrBeast: Youtuber? = null
    private var winASportsCar: Video? = null
    private var givingAwayOneMillionDollars: Video? = null
    private var plantingOneTreeForEachLike: Video? = null
    private var iShowSpeed: Youtuber? = null

    @BeforeEach
    fun setup() {

        ksi = Youtuber(0, "Jj Olatunji", "KSI", 2011, 16000000, false, false, mutableSetOf())
        musicVideo = Video(0, "No Time - Official Music Video", "Yes", "Music Videos", "Watching", 4, false)

        pewdiepie = Youtuber(1, "Felix Kjellberg", "PewDiePie", 2010, 111000000, false, false, mutableSetOf())
        minecraftTutorial = Video(1, "Learn how to mine for diamonds in Minecraft", "No", "Tutorial", "Watched", 5, false)

        zerkaa = Youtuber(2, "Josh Bradley", "ZerkaaPLays", 2012, 2800000, true, true, mutableSetOf())
        gta5PlayAlong = Video(2, "GTA5 Role play", "y", "Gaming", "Watching", 1, true)

        mollyMae = Youtuber(3, "Molly Mae Hague", "MollyMae", 2016, 1700000, false, false, mutableSetOf())
        makeupTutorial = Video(3, "Eyeshadow tutorial", "n", "Beauty", "Watched", 3, false)

        mrBeast = Youtuber(4, "Jimmy Donaldson", "Mr Beast", 2012, 113000000, true, true, mutableSetOf())
        winASportsCar = Video(4, "WIN A SPORTS CAR!!!", "yes", "Entertainment", "Watching", 2, true)
        givingAwayOneMillionDollars = Video(4, "GIVING AWAY 1 MILLION DOLLARS!!!", "no", "Entertainment", "Watched", 2, true)
        plantingOneTreeForEachLike = Video(4, "Planting One Tree For Each Like!!!", "YES", "Entertainment", "Watching", 2, true)

        iShowSpeed = Youtuber(5, "Darren Wadkins", "iShowSpeed", 2016, 13700000, false, true, mutableSetOf())

        // adding videos to youtubers
        ksi!!.addVideo(musicVideo!!)
        pewdiepie!!.addVideo(minecraftTutorial!!)
        zerkaa!!.addVideo(gta5PlayAlong!!)
        mollyMae!!.addVideo(makeupTutorial!!)
        mrBeast!!.addVideo(winASportsCar!!)
        mrBeast!!.addVideo(givingAwayOneMillionDollars!!)
        mrBeast!!.addVideo(plantingOneTreeForEachLike!!)
    }

    @AfterEach
    fun tearDown() {
        ksi = null
        musicVideo = null
        pewdiepie = null
        minecraftTutorial = null
        zerkaa = null
        gta5PlayAlong = null
        mollyMae = null
        makeupTutorial = null
        mrBeast = null
        winASportsCar = null
        givingAwayOneMillionDollars = null
        plantingOneTreeForEachLike = null
        iShowSpeed = null
    }

    @Nested
    inner class AddVideos {
        @Test
        fun `adding a video to a populated list adds to ArrayList`() {
            val newVideo = Video(0, "No Time - Official Music Video", "Yes", "Music Videos", "Watching", 4)
            assertEquals(1, ksi!!.numberOfVideos())
            assertTrue(ksi!!.addVideo(newVideo))
            assertEquals(2, ksi!!.numberOfVideos())
            assertEquals(newVideo, ksi!!.findOne(ksi!!.numberOfVideos() - 1))
        }

        @Test
        fun `adding a video to an empty list adds to ArrayList`() {
            val newYoutuber = Video(0, "No Time - Official Music Video", "Yes", "Music Videos", "Watching", 4)
            assertEquals(0, iShowSpeed!!.numberOfVideos())
            assertTrue(iShowSpeed!!.addVideo(newYoutuber))
            assertEquals(1, iShowSpeed!!.numberOfVideos())
            assertEquals(newYoutuber, iShowSpeed!!.findOne(iShowSpeed!!.numberOfVideos() - 1))
        }
    }

    @Nested
    inner class UpdateYoutubers {
        @Test
        fun `updating a video that does not exist returns false`() {
            assertFalse(mrBeast!!.update(6, Video(6, "WIN A PRIVATE JET!!!", "No", "Entertainment", "Watching", 5)))
            assertFalse(
                mrBeast!!.update(
                    -1,
                    Video(6, "WIN A PRIVATE JET OMG!!", "no", "Entertainment", "Watched", 4)
                )
            )
        }

        @Test
        fun `updating a video that exists returns true and updates`() {
            // check youtuber 5 exists and check the contents
            assertEquals(winASportsCar, mrBeast!!.findOne(0))
            assertEquals("WIN A SPORTS CAR!!!", mrBeast!!.findOne(0)!!.videoTitle)
            assertEquals("yes", mrBeast!!.findOne(0)!!.isVideoLiked)
            assertEquals("Entertainment", mrBeast!!.findOne(0)!!.videoCategory)
            assertEquals("Watching", mrBeast!!.findOne(0)!!.watchedStatus)
            assertEquals(2, mrBeast!!.findOne(0)!!.videoRating)

            // update youtuber 5 with new information and ensure contents updated successfully
            assertTrue(mrBeast!!.update(0, Video(0, "WIN A SPORTS CAR (CRAZY) !!", "Yes", "Entertainment", "Watched", 5)))
            assertEquals("WIN A SPORTS CAR (CRAZY) !!", mrBeast!!.findOne(0)!!.videoTitle)
            assertEquals("Yes", mrBeast!!.findOne(0)!!.isVideoLiked)
            assertEquals("Entertainment", mrBeast!!.findOne(0)!!.videoCategory)
            assertEquals("Watched", mrBeast!!.findOne(0)!!.watchedStatus)
            assertEquals(5, mrBeast!!.findOne(0)!!.videoRating)
        }
    }
    @Nested
    inner class DeleteYoutubers {

        @Test
        fun `deleting a video that does not exist, returns null`() {
            assertFalse(iShowSpeed!!.delete(0))
            assertFalse(mollyMae!!.delete(-1))
            assertFalse(zerkaa!!.delete(5))
        }

        @Test
        fun `deleting a video that exists delete and returns deleted object`() {
            assertEquals(3, mrBeast!!.numberOfVideos())
            assertTrue(mrBeast!!.delete(1))
            assertEquals(2, mrBeast!!.numberOfVideos())
            assertTrue(mrBeast!!.delete(0))
            assertEquals(1, mrBeast!!.numberOfVideos())
        }
    }

    @Nested
    inner class ListVideos {

        @Test
        fun `listVideos returns no videos stored message when ArrayList is empty`() {
            assertEquals(0, iShowSpeed!!.numberOfVideos())
            assertTrue(iShowSpeed!!.listVideos().lowercase().contains("no videos added"))
        }

        @Test
        fun `listVideos returns videos when ArrayList has youtubers stored`() {
            assertEquals(1, pewdiepie!!.numberOfVideos())
            val videoString = pewdiepie!!.listVideos().lowercase()
            assertTrue(videoString.contains("tutorial"))

            assertEquals(1, ksi!!.numberOfVideos())
            val videoString2 = ksi!!.listVideos().lowercase()
            assertTrue(videoString2.contains("music"))
        }
    }

    @Nested
    inner class MarkVideoAsWatched {
        @Test
        fun `Marking a video as watched that does not exist returns false`() {
            assertFalse(ksi!!.markingVideoAsWatched(6))
            assertFalse(ksi!!.markingVideoAsWatched(-1))
        }

        @Test
        fun `Marking a video as watched that is already watched, video returns false`() {
            assertTrue(mrBeast!!.findOne(2)!!.markVideoAsWatched)
            assertFalse(mrBeast!!.markingVideoAsWatched(2))
        }

        @Test
        fun `Marking a video as watched that exists returns true`() {
            assertFalse(ksi!!.findOne(0)!!.markVideoAsWatched)
            assertTrue(ksi!!.markingVideoAsWatched(0))
            assertTrue(ksi!!.findOne(0)!!.markVideoAsWatched)
        }
    }
}
