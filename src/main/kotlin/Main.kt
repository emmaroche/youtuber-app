import controllers.YoutuberAPI
import models.Video
import models.Youtuber
import persistence.JSONSerializer
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput.readValidCategory
import utils.ValidateInput.readValidRating
import utils.ValidateInput.readValidSubscribers
import utils.ValidateInput.readValidVideoLikedStatus
import utils.ValidateInput.readValidWatchedStatus
import utils.ValidateInput.readValidYear
import utils.VideoCategoryValidation
import utils.VideoLikedValidation
import utils.VideoWatchedStatusValidation
import java.io.File
import kotlin.system.exitProcess

private val youtuberAPI = YoutuberAPI(JSONSerializer(File("youtubers.json")))

/**
 * This class's single responsibility is to manage the user I/O.
 *
 * @author Emma Roche
 */
fun main() = welcomeMenu()

// ------------------------------------
// APP WELCOME SCREEN
// ------------------------------------
/**
 * This function is a menu option list for welcoming a user to the YouTuber App.
 */
fun welcomeMenu() {
    do {
        when (welcomeScreen()) {
            1 -> runMenu()
            else -> runMenu()
        }
    } while (true)
}

/**
 * This function is a welcome screen that the users will see as soon as this app is run.
 */
fun welcomeScreen(): Int {

    // code reference for adding colour to improve UI: https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html#rich-text
    // reference for the menu titles made with ASCII to improve UI: https://patorjk.com/software/taag/#p=display&f=Graceful&t=Youtuber%20app%20menu

    // displays the colour
    val red = "\u001b[31m"
    val brightRed = "\u001b[31;1m"
    // displays the decoration
    val bold = "\u001b[1m"
    // resets colour and decoration back to what it previously was
    val reset = "\u001b[0m"

    return readNextInt(
        """ 
         >
         >                        $bold Welcome to the $reset                         
         > $brightRed                        
         > _  _  __   _  _  ____  _  _  ____  ____  ____     __   ____  ____ 
         >( \/ )/  \ / )( \(_  _)/ )( \(  _ \(  __)(  _ \   / _\ (  _ \(  _ \
         > )  /(  O )) \/ (  )(  ) \/ ( ) _ ( ) _)  )   /  /    \ ) __/ ) __/
         >(__/  \__/ \____/ (__) \____/(____/(____)(__\_)  \_/\_/(__)  (__)  
         > $reset 
         > 
         > $bold !Press any number key followed by the enter key to start using!               
         >                         
         >                                $red???$reset                                 
         >                                """.trimMargin(">")
    )
}

// ------------------------------------
// APP MAIN MENU
// ------------------------------------
/**
 * This function shows the options that a user can choose for the main menu.
 */
fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addYoutuber()
            2 -> listingMenu()
            3 -> updateYoutuber()
            4 -> deleteYoutuber()
            5 -> searchingMenu()
            6 -> videoMenu()
            7 -> addYoutuberToFav()
            8 -> save()
            9 -> load()

            10 -> info()

//            -99 -> dummyData()
            0 -> exitApp()
            else -> println("\n Invalid menu choice: $option, try again!")
        }
    } while (true)
}

/**
 * This function is a main menu screen that the users will see after the welcome screen.
 */
fun mainMenu(): Int {

    // displays the colour
    val red = "\u001b[31m"
    val brightRed = "\u001b[31;1m"
    // displays the decoration
    val bold = "\u001b[1m"
    // resets colour and decoration back to what it previously was
    val reset = "\u001b[0m"

    return readNextInt(
        """ 
         >
         >$brightRed
         >  _  _  __   _  _  ____  _  _  ____  ____  ____     __   ____  ____    _  _  ____  __ _  _  _ 
         > ( \/ )/  \ / )( \(_  _)/ )( \(  _ \(  __)(  _ \   / _\ (  _ \(  _ \  ( \/ )(  __)(  ( \/ )( \
         >  )  /(  O )) \/ (  )(  ) \/ ( ) _ ( ) _)  )   /  /    \ ) __/ ) __/  / \/ \ ) _) /    /) \/ (
         > (__/  \__/ \____/ (__) \____/(____/(____)(__\_)  \_/\_/(__)  (__)    \_)(_/(____)\_)__)\____/
         >$reset                     
         >     
         >$bold 1 $red???$reset Add a YouTuber                               
         >$bold 2 $red???$reset List YouTubers                           
         >$bold 3 $red???$reset Update a YouTuber                            
         >$bold 4 $red???$reset Delete a YouTuber 
         >$bold 5 $red???$reset Search Youtubers
         >$bold 6 $red???$reset YouTuber Videos MENU
         >$bold 7 $red???$reset Add YouTuber to Favourites List
         >$bold 8 $red???$reset Save a YouTuber   
         >$bold 9 $red???$reset Load a YouTuber
         >                 
         >$bold 10 $red???$reset About this App
         >                     
         >$bold 0 $red???$reset Exit App           
         >                       
         >$bold Enter your option $red???$reset  """.trimMargin(">")
    )
}

// ------------------------------------
// APP LISTING MENU
// ------------------------------------
/**
 * This function is a listing menu screen that gives a user options for what YouTuber lists they can do.
 */
fun listingMenu() {

    // displays the colour
    val red = "\u001b[31m"
    val brightRed = "\u001b[31;1m"
    // displays the decoration
    val bold = "\u001b[1m"
    // resets colour and decoration back to what it previously was
    val reset = "\u001b[0m"

    val option = readNextInt(
        """ 
         >
         >$brightRed
         > __    __  ____  ____  __  __ _   ___    _  _  ____  __ _  _  _ 
         >(  )  (  )/ ___)(_  _)(  )(  ( \ / __)  ( \/ )(  __)(  ( \/ )( \
         >/ (_/\ )( \___ \  )(   )( /    /( (_ \  / \/ \ ) _) /    /) \/ (
         >\____/(__)(____/ (__) (__)\_)__) \___/  \_)(_/(____)\_)__)\____/
         >$reset                   
         >     
         >$bold 1 $red???$reset List ALL YouTubers 
         >$bold 2 $red???$reset List Favourite YouTubers                                   
         >$bold 3 $red???$reset List Non-Favourite YouTubers                              
         >$bold 4 $red???$reset List YouTubers from Oldest-Newest Channels                           
         >$bold 5 $red???$reset List YouTubers from Highest-Lowest Subscribers
         > 
         >$bold 0 $red???$reset  Return to main menu   
         > 
         >$bold Enter your option $red???$reset   """.trimMargin(">")
    )

    when (option) {
        1 -> listAllYoutubers()
        2 -> listFYoutubers()
        3 -> listNFYoutubers()
        4 -> listNewToOldChannels()
        5 -> listInSubOrder()
        0 -> mainMenu()
        else -> println("\n Invalid menu choice: $option, try again!")
    }
}

// ------------------------------------
// APP SEARCHING MENU
// ------------------------------------
/**
 * This function is a searching menu screen that gives a user options for what YouTuber searches they can do.
 */
fun searchingMenu() {

    // displays the colour
    val red = "\u001b[31m"
    val brightRed = "\u001b[31;1m"
    // displays the decoration
    val bold = "\u001b[1m"
    // resets colour and decoration back to what it previously was
    val reset = "\u001b[0m"

    val option = readNextInt(
        """ 
         >
         >$brightRed
         > ____  ____   __   ____   ___  _  _  __  __ _   ___    _  _  ____  __ _  _  _ 
         >/ ___)(  __) / _\ (  _ \ / __)/ )( \(  )(  ( \ / __)  ( \/ )(  __)(  ( \/ )( \
         >\___ \ ) _) /    \ )   /( (__ ) __ ( )( /    /( (_ \  / \/ \ ) _) /    /) \/ (
         >(____/(____)\_/\_/(__\_) \___)\_)(_/(__)\_)__) \___/  \_)(_/(____)\_)__)\____/
         >$reset                     
         >     
         >$bold 1 $red???$reset Search YouTubers by Channel Name  
         >$bold 2 $red???$reset Search YouTubers by Subscriber Count
         >                                   
         >$bold 0 $red???$reset Return to main menu   
         > 
         >$bold Enter your option $red???$reset  """.trimMargin(">")
    )

    when (option) {
        1 -> searchYoutuberByChannel()
        2 -> searchYoutuberBySubCount()
        0 -> mainMenu()
        else -> println("\n Invalid menu choice: $option, try again!")
    }
}

// ------------------------------------
// APP VIDEO MENU
// ------------------------------------
/**
 * This function is a video menu screen that gives a user options for what videos CRUD, lists and searches they can d.
 */
fun videoMenu() {

    // displays the colour
    val red = "\u001b[31m"
    val brightRed = "\u001b[31;1m"
    // displays the decoration
    val bold = "\u001b[1m"
    // resets colour and decoration back to what it previously was
    val reset = "\u001b[0m"

    val option = readNextInt(
        """ 
         >
         >$brightRed
         > _  _  __  ____  ____  __     _  _  ____  __ _  _  _ 
         > / )( \(  )(    \(  __)/  \   ( \/ )(  __)(  ( \/ )( \
         > \ \/ / )(  ) D ( ) _)(  O )  / \/ \ ) _) /    /) \/ (
         >  \__/ (__)(____/(____)\__/   \_)(_/(____)\_)__)\____/
         >$reset                    
         >     
         >$bold 1 $red???$reset Add a Video to a YouTuber
         >$bold 2 $red???$reset List Videos from chosen YouTuber                                  
         >$bold 3 $red???$reset Update Video Details                              
         >$bold 4 $red???$reset Delete Video                         
         >$bold 5 $red???$reset Mark a Video as Watched
         >$bold 6 $red???$reset List Watched Videos
         >$bold 7 $red???$reset Search Video by Title   
         >$bold 8 $red???$reset Search Video by Category  
         > 
         >$bold 0 $red???$reset Return to main menu 
         >   
         >$bold Enter your option $red???$reset  """.trimMargin(">")
    )

    when (option) {
        1 -> addVideoToYoutuber()
        2 -> listYoutuberVideos()
        3 -> updateVideoContents()
        4 -> deleteAVideo()
        5 -> markVideoAsWatched()
        6 -> listWatchedVideos()
        7 -> searchVideosByTitle()
        8 -> searchVideosByCategory()
        0 -> mainMenu()
        else -> println("\n  Invalid menu choice: $option, try again!")
    }
}

// ------------------------------------
// MENU CRUD
// ------------------------------------
/**
 * This function is for adding a YouTuber.
 */
fun addYoutuber() {
    val youtuberName = readNextLine("\n Enter the name of the YouTuber you would like to add: ")
    val youtuberChannelName = readNextLine(" Enter their YouTube channel name: ")
    val youtuberYearJoined = readValidYear(" Enter the year they created their YouTube channel: ")
    val youtuberSubscribers = readValidSubscribers(" Enter how many subscribers this YouTuber has: ")
    val subscribedToYoutuber = readNextChar(" Are you subscribed to this YouTuber?: y/n ")
    val booleanSubscribed = (subscribedToYoutuber == 'y' || subscribedToYoutuber == 'Y')
    val isAdded = youtuberAPI.add(Youtuber(youtuberName = youtuberName, youtuberChannelName = youtuberChannelName, youtuberYearJoined = youtuberYearJoined, youtuberSubscribers = youtuberSubscribers, subscribedToYoutuber = booleanSubscribed))

    if (isAdded) {
        println("\n YouTuber Added Successfully")
    } else {
        println("\n Failed to add YouTuber")
    }
}

/**
 * This function is for adding a Video to a YouTuber.
 */
private fun addVideoToYoutuber() {
    val video: Youtuber? = askUserToChooseYoutuber()
    if (video != null) {
        if (video.addVideo(
                Video(
                        videoTitle = readNextLine("\n Video Title: "),
                        videoCategory = readValidCategory(" Video Category, please choose from ${VideoCategoryValidation.categories}: "),
                        isVideoLiked = readValidVideoLikedStatus(" Video Interaction ${VideoLikedValidation.likedVideo}: "),
                        videoRating = readValidRating(" Video rating [1-5]: "),
                        watchedStatus = readValidWatchedStatus(" Watched Status, please choose from ${VideoWatchedStatusValidation.watched}: ")
                    )
            )
        )
            println("\n Video Added Successful!")
        else println("\n Failed to add Video")
    }
}

/**
 * This function is for listing all YouTubers.
 */
fun listAllYoutubers() = println(youtuberAPI.listAllYoutubers())

/**
 * This function is for listing Videos linked to a YouTubers.
 */
fun listYoutuberVideos() {
    val video: Youtuber? = askUserToChooseYoutuber()
    if (video != null) {
        println(video.listVideos())
    } else println("\n Unable to list YouTubers at this moment, please try again later!")
}

/**
 * This function is for updating all YouTubers.
 */
fun updateYoutuber() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber if it exists
        val id = readNextInt("\n Enter the number of the YouTuber you want to update: ")
        if (youtuberAPI.findYoutuber(id) != null) {
            val youtuberName = readNextLine(" Enter the name of the YouTuber you would like to add: ")
            val youtuberChannelName = readNextLine(" Enter their YouTube channel name: ")
            val youtuberYearJoined = readValidYear(" Enter the year they created their YouTube channel: ")
            val youtuberSubscribers = readValidSubscribers(" Enter how many subscribers this YouTuber has: ")
            val subscribedToYoutuber = readNextChar(" Are you subscribed to this YouTuber?: y/n ")
            val booleanSubscribed = (subscribedToYoutuber == 'y' || subscribedToYoutuber == 'Y')

            // pass the ID of the youtuber and the new youtuber details to YoutuberAPI for updating and check for success.
            if (youtuberAPI.update(id, Youtuber(0, youtuberName, youtuberChannelName, youtuberYearJoined, youtuberSubscribers, subscribedToYoutuber = booleanSubscribed))) {
                println("\n Update Successful")
            } else {
                println("\n Update Failed")
            }
        } else {
            println("\n There are no YouTubers linked to this ID")
        }
    }
}

/**
 * This function is for updating Videos.
 */
fun updateVideoContents() {
    val youtuber: Youtuber? = askUserToChooseYoutuber()
    if (youtuber != null) {
        val video: Video? = askUserToChooseVideo(youtuber)
        if (video != null) {
            val newVideo = readNextLine("\n Enter an updated video title: ")
            val newCategory = readValidCategory(" Updated Video Category, please choose from ${VideoCategoryValidation.categories}: ")
            val newIsVideoLiked = readValidVideoLikedStatus(" Updated Video Interaction ${VideoLikedValidation.likedVideo}: ")
            val newRating = readValidRating(" Updated Video Rating [1-5]: ")
            val newWatchedStatus = readValidWatchedStatus(" Updated Watched Status, please choose from ${VideoWatchedStatusValidation.watched}: ")
            if (youtuber.update(
                    video.videoId,
                    Video(
                            videoTitle = newVideo,
                            videoCategory = newCategory,
                            isVideoLiked = newIsVideoLiked,
                            videoRating = newRating,
                            watchedStatus = newWatchedStatus
                        )
                )
            ) {
                println("\n Video Details Updated Successfully!")
            } else {
                println("\n Video details NOT updated")
            }
        } else {
            println("\n Invalid Video ID")
        }
    }
}

/**
 * This function is for deleting YouTubers.
 */
fun deleteYoutuber() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber to delete if it exists
        val id = readNextInt("\n Enter the id of the YouTuber you'd like to delete: ")
        // pass the index of the youtuber to YoutuberAPI for deleting and check for success.
        val youtuberToDelete = youtuberAPI.delete(id)
        if (youtuberToDelete) {
            println("\n YouTuber deleted Successfully!")
        } else {
            println("\n Delete NOT Successful")
        }
    }
}

/**
 * This function is for deleting Videos.
 */
fun deleteAVideo() {
    val youtuber: Youtuber? = askUserToChooseYoutuber()
    if (youtuber != null) {
        val video: Video? = askUserToChooseVideo(youtuber)
        if (video != null) {
            val isDeleted = youtuber.delete(video.videoId)
            if (isDeleted) {
                println("\n Video Deleted Successfully!")
            } else {
                println("\n Delete NOT Successful")
            }
        }
    }
}

// ------------------------------------
// ADD YOUTUBER TO FAVOURITES
// ------------------------------------
/**
 * This function is for adding a YiuTuber to favourites.
 */
fun addYoutuberToFav() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber to favourite if youtuber exists
        val indexToFavourite = readNextInt("\n Enter the number of the YouTuber to add to your favourites: ")
        // pass the index of the youtuber to YoutuberAPI for favouring and check for success.
        if (youtuberAPI.addYoutuberToFavourites(indexToFavourite)) {
            println("\n YouTuber Added to Favourites Successful!\n")
        } else {
            println("\n Add to Favourites NOT Successful\n")
        }
    }
}

// ------------------------------------
// MARKING A VIDEO AS WATCHED
// ------------------------------------
/**
 * This function is for marking a video as watched.
 */
fun markVideoAsWatched() {
    val youtube: Youtuber? = askUserToChooseYoutuber()
    if (youtube != null) {
        val video: Video? = askUserToChooseVideo(youtube)
        if (video != null) {
            val changeStatus: Char
            if (video.markVideoAsWatched) {
                changeStatus = readNextChar("\n This video is currently marked as watched...do you want to update the watched status to 'Yet to watch'? (y/n)")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    video.markVideoAsWatched = false
                video.watchedStatus = ("Yet to watch")
            } else {
                changeStatus = readNextChar("\n This video is currently Yet to watch...do you want to mark it as Watched? (y/n)")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    video.markVideoAsWatched = true
                video.watchedStatus = ("Watched")
            }
        }
    }
}

// --------------------------------------------
// MORE INVOLVED LISTING METHODS
// --------------------------------------------
/**
 * This function is for listing non-favourite YouTubers.
 */
fun listNFYoutubers() = println(youtuberAPI.listNonFavouriteYoutubers())

/**
 * This function is for listing favourite YouTubers.
 */
fun listFYoutubers() = println(youtuberAPI.listFavouriteYoutubers())

/**
 * This function is for listing YouTubers in order of highest to the lowest subscriber count.
 */
fun listInSubOrder() = println(youtuberAPI.listYoutubersInOrderOfSubCount())

/**
 * This function is for listing YouTubers in order of old-new channels based on the year they joined.
 */
fun listNewToOldChannels() = println(youtuberAPI.listYoutubersFromNewestToOldestChannel())

/**
 * This function is for listing videos marked as watched.
 */
fun listWatchedVideos() {
    if (youtuberAPI.numberOfWatchedVideos() > 0) {
        println("\n Total watched videos: ${youtuberAPI.numberOfWatchedVideos()}")
        println(youtuberAPI.listWatchedVideos())
    } else println("\n No videos marked as watched")
}

// ------------------------------------
// SEARCHING METHODS
// ------------------------------------
/**
 * This function is for searching YouTubers by channel name.
 */
fun searchYoutuberByChannel() {

    val searchTitle = readNextLine("\n Search for channel name: ")
    val searchResults = youtuberAPI.searchYoutuberByChannelName(searchTitle)
    if (searchResults.isEmpty()) {
        println("\n No YouTubers found\n")
    } else {
        println(searchResults)
    }
}

/**
 * This function is for searching YouTubers by subscriber count.
 */
fun searchYoutuberBySubCount() {

    val searchSubCount = readNextInt("\n Enter the subscriber count to search by ")
    val searchResults = youtuberAPI.searchYoutuberBySubCount(searchSubCount)
    if (searchResults.isEmpty()) {
        println("\n No YouTubers found\n")
    } else {
        println(searchResults)
    }
}

/**
 * This function is for searching videos by title.
 */
fun searchVideosByTitle() {
    val searchContents = readNextLine("\n Enter the video title to search by: ")
    val searchResults = youtuberAPI.searchVideoByTitle(searchContents)
    if (searchResults.isEmpty()) {
        println("\n No videos found")
    } else {
        println(searchResults)
    }
}

/**
 * This function is for searching videos by category.
 */
fun searchVideosByCategory() {
    val searchContents = readNextLine("\n Enter the video category to search by: ")
    val searchResults = youtuberAPI.searchVideoByCategory(searchContents)
    if (searchResults.isEmpty()) {
        println("\n No videos found")
    } else {
        println(searchResults)
    }
}

// ------------------------------------
// INFO PAGE
// ------------------------------------
/**
 * This function is an 'about the app' screen that provides information on the app.
 */
fun info() {

    // reference for the generated ASCII image of the YouTube logo to improve the UI: https://manytools.org/hacker-tools/convert-images-to-ascii-art/go/

    // displays the colour
    val brightRed = "\u001b[31;1m"
    // resets colour and decoration back to what it previously was
    val reset = "\u001b[0m"

    val option = readNextInt(
        """ 
         >$brightRed                                                        
         >                ###############################                                                         
         >              ###################################                      
         >             #####$reset  About the YouTuber App $brightRed ######                 
         >            ################  #####################                    
         >            ################    ###################                    
         >            ################        ###############                   
         >            ################          #############                    
         >            ################      #################                    
         >            ################  #####################                    
         >            #######################################                    
         >             #####################################                     
         >               #################################        
         >$reset
         > 
         > The YouTuber App allows you to store YouTubers and link many videos to them. 
         >                          
         > The YouTuber App is the perfect place to store information on the YouTubers you watch.
         > In this app, you can:
         >                     -  Add, list, update, delete and search YouTubers and Videos
         >                     -  Add YouTubers to a favourites list
         >                     -  Mark the videos as watched to help you keep on track of what you have and haven???t watched
         >                                 
         > Thank you for using The YouTuber App!   
         >                          
         > Press any number key followed by the enter key to return to the Main Menu  
         > 
         > """.trimMargin(">")
    )

    when (option) {
        0 -> mainMenu()
        else -> mainMenu()
    }
}

// ------------------------------------
// EXIT APP
// ------------------------------------
/**
 * This function is for exiting the app.
 */
fun exitApp() {
    println("\n Exiting app ... Thank you for using! :)")
    exitProcess(0)
}

// ------------------------------------
// HELPER FUNCTIONS
// ------------------------------------
/**
 * This function is for asking users to choose a Youtuber.
 */
private fun askUserToChooseYoutuber(): Youtuber? {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        val youtuber = youtuberAPI.findYoutuber(readNextInt("\n Enter the number of the Youtuber you want to choose: "))
        if (youtuber != null) {
            return youtuber // chosen youtuber is active
        } else {
            println("\n Youtuber number is not valid")
        }
    }
    return null
}

/**
 * This function is for asking users to choose a video linked to a YouTuber.
 */
private fun askUserToChooseVideo(youtuber: Youtuber): Video? {
    return if (youtuber.numberOfVideos() > 0) {
        print(youtuber.listVideos())
        youtuber.findOne(readNextInt("\n Enter the number of the video: "))
    } else {
        println("\n No videos stored for chosen Youtuber")
        null
    }
}

// ----------------------------------------------
//  SAVE AND LOAD YOUTUBERS
// ---------------------------------------------

/**
 * This function is for saving YouTubers.
 */
fun save() {
    try {
        youtuberAPI.store()
    } catch (e: Exception) {
        System.err.println("\n Error writing to file: $e")
    }
}

/**
 * This function is for loading in YouTubers.
 */
fun load() {
    try {
        youtuberAPI.load()
    } catch (e: Exception) {
        System.err.println("\n Error reading from file: $e")
    }
}
