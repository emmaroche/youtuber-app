package controllers

import models.Youtuber
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class YoutuberAPITest {

    private var ksi: Youtuber? = null
    private var pewdiepie: Youtuber? = null
    private var zerkaa: Youtuber? = null
    private var mollyMae: Youtuber? = null
    private var mrBeast: Youtuber? = null
    private var populatedYoutubers: YoutuberAPI? = YoutuberAPI(XMLSerializer(File("youtubers.xml")))
    private var emptyYoutubers: YoutuberAPI? = YoutuberAPI(XMLSerializer(File("youtubers.xml")))

    @BeforeEach
    fun setup() {
        ksi = Youtuber(0, "Jj Olatunji", "KSI", 2011, 16000000, false, false)
        pewdiepie = Youtuber(1, "Felix Kjellberg", "PewDiePie", 2010, 111000000, false, false)
        zerkaa = Youtuber(2, "Josh Bradley", "ZerkaaPLays", 2012, 2800000, true, true)
        mollyMae = Youtuber(3, "Molly Mae Hague", "MollyMae", 2016, 1700000, false, false)
        mrBeast = Youtuber(4, "Jimmy Donaldson", "Mr Beast", 2012, 113000000, true, true)

        // adding 5 Youtubers to the youtuber api
        populatedYoutubers!!.add(ksi!!)
        populatedYoutubers!!.add(pewdiepie!!)
        populatedYoutubers!!.add(zerkaa!!)
        populatedYoutubers!!.add(mollyMae!!)
        populatedYoutubers!!.add(mrBeast!!)
    }

    @AfterEach
    fun tearDown() {
        ksi = null
        pewdiepie = null
        zerkaa = null
        mollyMae = null
        mrBeast = null
        populatedYoutubers = null
        emptyYoutubers = null
    }

    @Nested
    inner class AddYoutubers {
        @Test
        fun `adding a youtuber to a populated list adds to ArrayList`() {
            val newYoutuber = Youtuber(0, "Jj Olatunji", "KSI", 2011, 16000000, false, false)
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            assertTrue(populatedYoutubers!!.add(newYoutuber))
            assertEquals(6, populatedYoutubers!!.numberOfYoutubers())
            assertEquals(newYoutuber, populatedYoutubers!!.findYoutuber(populatedYoutubers!!.numberOfYoutubers() - 1))
        }

        @Test
        fun `adding a youtuber to an empty list adds to ArrayList`() {
            val newYoutuber = Youtuber(0, "Jj Olatunji", "KSI", 2011, 16000000, false, false)
            assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
            assertTrue(emptyYoutubers!!.add(newYoutuber))
            assertEquals(1, emptyYoutubers!!.numberOfYoutubers())
            assertEquals(newYoutuber, emptyYoutubers!!.findYoutuber(emptyYoutubers!!.numberOfYoutubers() - 1))
        }
    }

    @Nested
    inner class UpdateYoutubers {
        @Test
        fun `updating a youtuber that does not exist returns false`() {
            assertFalse(populatedYoutubers!!.update(6, Youtuber(6, "Fred Jenkins", "WorkChannel", 2020, 348902, false, false)))
            assertFalse(
                populatedYoutubers!!.update(
                    -1,
                    Youtuber(-1, "Darren Wadkins", "iShowSpeed", 2012, 46822, true)
                )
            )
            assertFalse(emptyYoutubers!!.update(0, Youtuber(0, "Jane McGrath", "Tutorials4you", 2022, 46381, false, false)))
        }

        @Test
        fun `updating a youtuber that exists returns true and updates`() {
            // check youtuber 5 exists and check the contents
            assertEquals(mrBeast, populatedYoutubers!!.findYoutuber(4))
            assertEquals("Jimmy Donaldson", populatedYoutubers!!.findYoutuber(4)!!.youtuberName)
            assertEquals("Mr Beast", populatedYoutubers!!.findYoutuber(4)!!.youtuberChannelName)
            assertEquals(2012, populatedYoutubers!!.findYoutuber(4)!!.youtuberYearJoined)

            // update youtuber 5 with new information and ensure contents updated successfully
            assertTrue(populatedYoutubers!!.update(4, Youtuber(4, "Jimmy Donald", "Mr.Beast", 2013, 10110111, false, false)))
            assertEquals("Jimmy Donald", populatedYoutubers!!.findYoutuber(4)!!.youtuberName)
            assertEquals("Mr.Beast", populatedYoutubers!!.findYoutuber(4)!!.youtuberChannelName)
            assertEquals(2013, populatedYoutubers!!.findYoutuber(4)!!.youtuberYearJoined)
        }
    }

    @Nested
    inner class DeleteYoutubers {

        @Test
        fun `deleting a youtuber that does not exist, returns null`() {
            assertFalse(emptyYoutubers!!.delete(0))
            assertFalse(populatedYoutubers!!.delete(-1))
            assertFalse(populatedYoutubers!!.delete(5))
        }

        @Test
        fun `deleting a youtuber that exists delete and returns deleted object`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            assertTrue (populatedYoutubers!!.delete(4))
            assertEquals(4, populatedYoutubers!!.numberOfYoutubers())
            assertTrue (populatedYoutubers!!.delete(0))
            assertEquals(3, populatedYoutubers!!.numberOfYoutubers())

        }
    }

        @Nested
        inner class ListYoutubers {

            @Test
            fun `listAllYoutubers returns no youtubers stored message when ArrayList is empty`() {
                assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
                assertTrue(emptyYoutubers!!.listAllYoutubers().lowercase().contains("no youtubers stored"))
            }

            @Test
            fun `listAllYoutubers returns youtubers when ArrayList has youtubers stored`() {
                assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
                val youtuberString = populatedYoutubers!!.listAllYoutubers().lowercase()
                assertTrue(youtuberString.contains("ksi"))
                assertTrue(youtuberString.contains("pewdiepie"))
                assertTrue(youtuberString.contains("zerkaa"))
                assertTrue(youtuberString.contains("molly mae"))
                assertTrue(youtuberString.contains("mr beast"))

            }

            @Test
            fun `listFavouriteYoutubers returns no favourite youtubers when ArrayList is empty`() {
                assertEquals(0, emptyYoutubers!!.numberOfFavouriteYoutubers())
                assertTrue(
                    emptyYoutubers!!.listFavouriteYoutubers().lowercase().contains("no youtubers")
                )
            }

            @Test
            fun `listFavouriteYoutubers returns favourite youtubers when ArrayList has favourite youtubers  stored`() {
                assertEquals(2, populatedYoutubers!!.numberOfFavouriteYoutubers())
                val favouriteYoutubersString = populatedYoutubers!!.listFavouriteYoutubers().lowercase()
                assertFalse(favouriteYoutubersString.contains("Molly"))
                assertFalse(favouriteYoutubersString.contains("Jimmy"))
            }

            @Test
            fun `listNonFavouriteYoutubers returns non favourite Youtubers stored when ArrayList is empty`() {
                assertEquals(0, emptyYoutubers!!.numberOfNonFavouriteYoutubers())
                assertTrue(
                    emptyYoutubers!!.listNonFavouriteYoutubers().lowercase().contains("no youtubers")
                )
            }

            @Test
            fun `listNonFavouriteYoutubers returns non favourite Youtubers when ArrayList has non favourite Youtubers youtubers stored`() {
                assertEquals(3, populatedYoutubers!!.numberOfNonFavouriteYoutubers())
                val nonFavouriteYoutubersString = populatedYoutubers!!.listNonFavouriteYoutubers().lowercase()
                assertFalse(nonFavouriteYoutubersString.contains("KSI"))
                assertFalse(nonFavouriteYoutubersString.contains("PewDiePie"))
                assertFalse(nonFavouriteYoutubersString.contains("Molly Mae Hague"))
            }

            @Test
            fun `listYoutubersInOrderOfSubCount returns no youtubers when ArrayList is empty`() {
                assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
                assertTrue(
                    emptyYoutubers!!.listYoutubersInOrderOfSubCount().lowercase().contains("no youtubers store")
                )
            }

            @Test
            fun `listYoutubersInOrderOfSubCount returns youtubers when ArrayList has youtubers stored`() {
                assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
                val subCountOrderString = populatedYoutubers!!.listYoutubersInOrderOfSubCount().lowercase()
                assertFalse(subCountOrderString.contains("KSI"))
                assertFalse(subCountOrderString.contains("PewDiePie"))
                assertFalse(subCountOrderString.contains("ZerkaaPLays"))
                assertFalse(subCountOrderString.contains("MollyMae"))
                assertFalse(subCountOrderString.contains("Mr Beast"))
            }

            @Test
            fun `listYoutubersFromNewestToOldestChannel returns no youtubers when ArrayList is empty`() {
                assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
                assertTrue(
                    emptyYoutubers!!.listYoutubersFromNewestToOldestChannel().lowercase().contains("no youtubers store")
                )
            }

            @Test
            fun `listYoutubersFromNewestToOldestChannel returns youtubers when ArrayList has youtubers stored`() {
                assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
                val subCountOrderString = populatedYoutubers!!.listYoutubersFromNewestToOldestChannel().lowercase()
                assertFalse(subCountOrderString.contains("KSI"))
                assertFalse(subCountOrderString.contains("PewDiePie"))
                assertFalse(subCountOrderString.contains("ZerkaaPLays"))
                assertFalse(subCountOrderString.contains("MollyMae"))
                assertFalse(subCountOrderString.contains("Mr Beast"))
            }

            @Test
            fun `listWatchedVideos returns the videos marked as watched that are stored when ArrayList is empty`() {
                assertEquals(0, emptyYoutubers!!.numberOfWatchedVideos())
                assertTrue(
                    emptyYoutubers!!.listWatchedVideos().lowercase().contains("no videos")
                )
            }

            @Test
            fun `listWatchedVideos returns the videos marked as watched when ArrayList has non favourite Youtubers youtubers stored`() {
                assertEquals(0, populatedYoutubers!!.numberOfWatchedVideos())
                val nonFavouriteYoutubersString = populatedYoutubers!!.listWatchedVideos().lowercase()
                assertFalse(nonFavouriteYoutubersString.contains("KSI"))
                assertFalse(nonFavouriteYoutubersString.contains("PewDiePie"))
                assertFalse(nonFavouriteYoutubersString.contains("Molly Mae Hague"))
            }

        }

    @Nested
    inner class FavouriteYoutubers {
        @Test
        fun `favouriting a note that does not exist returns false`() {
            assertFalse(populatedYoutubers!!.addYoutuberToFavourites(6))
            assertFalse(populatedYoutubers!!.addYoutuberToFavourites(-1))
        }

        @Test
        fun `favouriting an already favourited note returns false`() {
            assertTrue(populatedYoutubers!!.findYoutuber(2)!!.isFavouriteYoutuber)
            assertFalse(populatedYoutubers!!.addYoutuberToFavourites(2))
        }

        @Test
        fun `favouriting an active youtuber that exists returns true and favouriting`() {
            assertFalse(populatedYoutubers!!.findYoutuber(1)!!.isFavouriteYoutuber)
            assertTrue(populatedYoutubers!!.addYoutuberToFavourites(1))
            assertTrue(populatedYoutubers!!.findYoutuber(1)!!.isFavouriteYoutuber)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfYoutubersCalculatedCorrectly() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
        }

        @Test
        fun numberOfNonFavouriteYoutubersCalculatedCorrectly() {
            assertEquals(3, populatedYoutubers!!.numberOfNonFavouriteYoutubers())
            assertEquals(0, emptyYoutubers!!.numberOfNonFavouriteYoutubers())
        }
        @Test
        fun numberOfFavouriteYoutubersNotesCalculatedCorrectly() {
            assertEquals(2, populatedYoutubers!!.numberOfFavouriteYoutubers())
            assertEquals(0, emptyYoutubers!!.numberOfFavouriteYoutubers())
        }
        @Test
        fun numberOfNotesBySubCountCalculatedCorrectly() {
            assertEquals(1, populatedYoutubers!!.numberOfNotesBySubCount(16000000))
            assertEquals(0, populatedYoutubers!!.numberOfNotesBySubCount(3))
            assertEquals(1, populatedYoutubers!!.numberOfNotesBySubCount(111000000))
            assertEquals(0, populatedYoutubers!!.numberOfNotesBySubCount(4))
            assertEquals(1, populatedYoutubers!!.numberOfNotesBySubCount(1700000))
            assertEquals(0, emptyYoutubers!!.numberOfNotesBySubCount(1))
        }

        @Test
        fun numberOfWatchedVideosCalculatedCorrectly() {
            assertEquals(0, populatedYoutubers!!.numberOfWatchedVideos())
            assertEquals(0, emptyYoutubers!!.numberOfWatchedVideos())
        }

    }

    @Nested
    inner class SearchMethods {

        @Test
        fun `search youtubers by channel name returns no youtubers when no youtubers with that channel name exist`() {
            // Searching a populated collection for a title that doesn't exist.
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            val searchResults = populatedYoutubers!!.searchYoutuberByChannelName("no youtubers found")
            assertTrue(searchResults.isEmpty())

            // Searching an empty collection
            assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
            assertTrue(emptyYoutubers!!.searchYoutuberByChannelName("no youtubers found").isEmpty())
        }

        @Test
        fun `search youtubers by channel name returns youtubers when youtubers with that channel name exist`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())

            // Searching a populated collection for a full title that exists (case matches exactly)
            var searchResults = populatedYoutubers!!.searchYoutuberByChannelName("KSI")
            assertTrue(searchResults.contains("KSI"))
            assertFalse(searchResults.contains("PewDiePie"))

            // Searching a populated collection for a partial title that exists (case matches exactly)
            searchResults = populatedYoutubers!!.searchYoutuberByChannelName("PewDiePie")
            assertTrue(searchResults.contains("PewDiePie"))
            assertFalse(searchResults.contains("Mr Beast"))
//
            // Searching a populated collection for a partial title that exists (case doesn't match)
            searchResults = populatedYoutubers!!.searchYoutuberByChannelName("ZerKAaPLays")
            assertTrue(searchResults.contains("ZerkaaPLays"))
            assertFalse(searchResults.contains("Molly Mae"))
        }

        @Test
        fun `searchYoutuberBySubCount returns No youtubers when ArrayList is empty`() {
            assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
            assertTrue(
                emptyYoutubers!!.searchYoutuberBySubCount(1).lowercase().contains("no youtubers found")
            )
        }

        @Test
        fun `searchYoutuberBySubCount returns no youtubers when no youtubers of that sub count exist`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            val subcount2String = populatedYoutubers!!.searchYoutuberBySubCount(2).lowercase()
            assertTrue(subcount2String.contains("2"))
        }

        @Test
        fun `searchYoutuberBySubCount returns all youtubers that match the amount of or above the given sub count when youtubers of that sub count exist`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            val subcount1String = populatedYoutubers!!.searchYoutuberBySubCount(16000000).lowercase()
            assertTrue(subcount1String.contains("16000000"))
            assertTrue(subcount1String.contains("ksi"))
            assertTrue(subcount1String.contains("mr beast"))
            assertTrue(subcount1String.contains("pewdiepie"))
            assertFalse(subcount1String.contains("zerkaapLays"))
            assertFalse(subcount1String.contains("molly"))

            val subcount113String = populatedYoutubers!!.searchYoutuberBySubCount(113000000).lowercase()
            assertTrue(subcount113String.contains("113000000"))
            assertTrue(subcount113String.contains("jimmy"))
            assertTrue(subcount113String.contains("beast"))
            assertFalse(subcount113String.contains("felix"))
            assertFalse(subcount113String.contains("zerkaapLays"))
            assertFalse(subcount113String.contains("olatunji"))
        }

        @Test
        fun `searchVideoByTitle returns no videos when ArrayList is empty`() {
            assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
            assertTrue(
                emptyYoutubers!!.searchVideoByTitle("hello").lowercase().contains("no videos stored")
            )
        }

        @Test
        fun `searchVideoByTitle returns no videos when no videos of that title exist`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            val titleString = populatedYoutubers!!.searchVideoByTitle("HEllO").lowercase()
            assertFalse(titleString.contains("HELLO"))
        }

        @Test
        fun `searchVideoByTitle returns all videos that match the title`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            val title1String = populatedYoutubers!!.searchVideoByTitle("Tutorial").lowercase()
            assertTrue(title1String.contains("tutorial"))
            assertFalse(title1String.contains("molly"))
        }

        @Test
        fun `searchVideoByCategory returns no videos when ArrayList is empty`() {
            assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
            assertTrue(
                emptyYoutubers!!.searchVideoByCategory("do i exist").lowercase().contains("no videos stored")
            )
        }

        @Test
        fun `searchVideoByCategory returns no videos when no videos of that title exist`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            val titleString = populatedYoutubers!!.searchVideoByCategory("I DONT EXIST").lowercase()
            assertFalse(titleString.contains("I DONT EXIST"))
        }

        @Test
        fun `searchVideoByCategory returns all videos that match the title`() {
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            val title1String = populatedYoutubers!!.searchVideoByCategory("I exist").lowercase()
            assertTrue(title1String.contains("i exist"))
            assertFalse(title1String.contains("ksi"))
        }
    }

    @Nested
        inner class PersistenceTests {

            @Test
            fun `saving and loading an empty collection in XML doesn't crash app`() {
                // Saving an empty youtubers.XML file.
                val storingYoutubers = YoutuberAPI(XMLSerializer(File("youtubers.xml")))
                storingYoutubers.store()

                // Loading the empty youtubers.xml file into a new object
                val loadedYoutubers = YoutuberAPI(XMLSerializer(File("youtubers.xml")))
                loadedYoutubers.load()

                // Comparing the source of the youtubers (storingYoutubers) with the XML loaded youtubers (loadedYoutubers)
                assertEquals(0, storingYoutubers.numberOfYoutubers())
                assertEquals(0, loadedYoutubers.numberOfYoutubers())
                assertEquals(storingYoutubers.numberOfYoutubers(), loadedYoutubers.numberOfYoutubers())
            }

            @Test
            fun `saving and loading an loaded collection in XML doesn't loose data`() {
                // Storing 3 Youtubers to the Youtubers.XML file.
                val storingYoutubers = YoutuberAPI(XMLSerializer(File("youtubers.xml")))
                storingYoutubers.add(ksi!!)
                storingYoutubers.add(pewdiepie!!)
                storingYoutubers.add(zerkaa!!)
                storingYoutubers.store()

                // Loading Youtubers.xml into a different collection
                val loadedYoutubers = YoutuberAPI(XMLSerializer(File("youtubers.xml")))
                loadedYoutubers.load()

                // Comparing the source of the Youtubers (storingYoutubers) with the XML loaded Youtubers (loadedYoutubers)
                assertEquals(3, storingYoutubers.numberOfYoutubers())
                assertEquals(3, loadedYoutubers.numberOfYoutubers())
                assertEquals(storingYoutubers.numberOfYoutubers(), loadedYoutubers.numberOfYoutubers())
                assertEquals(storingYoutubers.findYoutuber(0), loadedYoutubers.findYoutuber(0))
                assertEquals(storingYoutubers.findYoutuber(1), loadedYoutubers.findYoutuber(1))
                assertEquals(storingYoutubers.findYoutuber(2), loadedYoutubers.findYoutuber(2))
            }

            @Test
            fun `saving and loading an empty collection in JSON doesn't crash app`() {
                // Saving an empty Youtubers.json file.
                val storingYoutubers = YoutuberAPI(JSONSerializer(File("youtubers.json")))
                storingYoutubers.store()

                // Loading the empty Youtubers.json file into a new object
                val loadedYoutubers = YoutuberAPI(JSONSerializer(File("youtubers.json")))
                loadedYoutubers.load()

                // Comparing the source of the Youtubers (storingYoutubers) with the json loaded Youtubers (loadedYoutubers)
                assertEquals(0, storingYoutubers.numberOfYoutubers())
                assertEquals(0, loadedYoutubers.numberOfYoutubers())
                assertEquals(storingYoutubers.numberOfYoutubers(), loadedYoutubers.numberOfYoutubers())
            }

            @Test
            fun `saving and loading an loaded collection in JSON doesn't loose data`() {
                // Storing 3 Youtubers to the Youtubers.json file.
                val storingYoutubers = YoutuberAPI(JSONSerializer(File("youtubers.json")))
                storingYoutubers.add(ksi!!)
                storingYoutubers.add(pewdiepie!!)
                storingYoutubers.add(zerkaa!!)
                storingYoutubers.store()

                // Loading Youtubers.json into a different collection
                val loadedYoutubers = YoutuberAPI(JSONSerializer(File("youtubers.json")))
                loadedYoutubers.load()

                // Comparing the source of the Youtubers (storingYoutubers) with the json loaded Youtubers (loadedYoutubers)
                assertEquals(3, storingYoutubers.numberOfYoutubers())
                assertEquals(3, loadedYoutubers.numberOfYoutubers())
                assertEquals(storingYoutubers.numberOfYoutubers(), loadedYoutubers.numberOfYoutubers())
                assertEquals(storingYoutubers.findYoutuber(0), loadedYoutubers.findYoutuber(0))
                assertEquals(storingYoutubers.findYoutuber(1), loadedYoutubers.findYoutuber(1))
                assertEquals(storingYoutubers.findYoutuber(2), loadedYoutubers.findYoutuber(2))
            }
        }
    }

