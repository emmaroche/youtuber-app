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
        ksi = Youtuber(0, "Jj Olatunji","KSI", 2011,16000000, false)
        pewdiepie = Youtuber(1,"Felix Kjellberg", "PewDiePie",2010, 111000000, false)
        zerkaa = Youtuber(2, "Josh Bradley","ZerkaaPLays",2012, 2800000,  true)
        mollyMae = Youtuber(3, "Molly Mae Hague","MollyMae", 2016, 1700000, false)
        mrBeast = Youtuber(4, "Jimmy Donaldson", "Mr Beast",2012, 113000000,  true)

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
        fun `adding a Youtuber to a populated list adds to ArrayList`() {
            val newYoutuber = Youtuber(0, "Jj Olatunji","KSI", 2011,16000000, false)
            assertEquals(5, populatedYoutubers!!.numberOfYoutubers())
            assertTrue(populatedYoutubers!!.add(newYoutuber))
            assertEquals(6, populatedYoutubers!!.numberOfYoutubers())
            assertEquals(newYoutuber, populatedYoutubers!!.findYoutuber(populatedYoutubers!!.numberOfYoutubers() - 1))
        }

        @Test
        fun `adding a Youtuber to an empty list adds to ArrayList`() {
            val newYoutuber = Youtuber(0, "Jj Olatunji","KSI", 2011,16000000, false)
            assertEquals(0, emptyYoutubers!!.numberOfYoutubers())
            assertTrue(emptyYoutubers!!.add(newYoutuber))
            assertEquals(1, emptyYoutubers!!.numberOfYoutubers())
            assertEquals(newYoutuber, emptyYoutubers!!.findYoutuber(emptyYoutubers!!.numberOfYoutubers() - 1))
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
