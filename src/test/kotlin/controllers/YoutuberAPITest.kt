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
                    emptyYoutubers!!.listFavouriteYoutubers().lowercase().contains("no favourite")
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
                    emptyYoutubers!!.listNonFavouriteYoutubers().lowercase().contains("no non favourite")
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

