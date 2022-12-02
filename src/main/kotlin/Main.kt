import controllers.YoutuberAPI
import models.Youtuber
import models.Video
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

fun main() = runMenu()

//------------------------------------
// App Menu
//------------------------------------
fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addYoutuber()
            2 -> listAllYoutubers()
            3 -> updateYoutuber()
            4 -> deleteYoutuber()
            5 -> addYoutuberToFavs()
            10 -> listFYoutubers()
            11 -> listNFYoutubers()
            12 -> listInSubOrder()
            13 -> listNewToOldChannels()
            14 -> searchYoutuberByChannel()
            15 -> searchYoutuberBySubCount()
            16 -> markVideoAsWatched()
            17 -> listWatchedVideos()
            18 -> searchVideosByTitle()
            19 -> searchVideosByCategory()
            6 -> addVideoToYoutuber()
            7 -> listYoutuberVideos()
            8 -> updateVideoContents()
            9 -> deleteAVideo()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option, try again!")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |               YouTuber KEEPER APP                 |
         > -----------------------------------------------------  
         > | YouTuber MENU                                     |
         > |   1) Add a YouTuber                               |
         > |   2) List all YouTubers                           |
         > |   3) Update a YouTuber                            |
         > |   4) Delete a YouTuber                            |
         > |   5) Add youtuber to favourites                   |
         > ----------------------------------------------------- 
         > | Listing MENU                                      | 
         > |   10) List favourite YouTubers                    |
         > |   11) List non-favourite YouTubers                |
         > |   12) List youtubers from highest-lowest subs     |
         > |   13) List youtubers from oldest-newest channels  | 
         > ----------------------------------------------------- 
         > | Searching MENU                                    | 
         > |   14) Search by channel name                      |
         > |   15) Search by sub count                         |
         > ----------------------------------------------------- 
         > | Video MENU                                        | 
         > |   6) Add video to a youtuber                      |
         > |   7) List contents of a video                     |
         > |   8) Update contents on a video                   |
         > |   9) Delete video                                 | 
         > |   16) Mark video as watched                       |
         > |   17) List watched videos                         |
         > |   18) Search video by title                       |
         > |   19) Search video by category                    |
         > ----------------------------------------------------- 
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
// Youtuber menu CRUD
//------------------------------------

//Add youtuber
fun addYoutuber() {
    val youtuberName = readNextLine("Enter the name of the YouTuber you would like to add: ")
    val youtuberChannelName = readNextLine("Enter their YouTube channel name: ")
    val youtuberYearJoined = readValidYear("Enter the year they created their YouTube channel: ")
    val youtuberSubscribers = readValidSubscribers("Enter how many subscribers they currently have: ")
    val subscribedToYoutuber = readNextChar("Are you subscribed to this YouTuber?: y/n ")
    val booleanSubscribed = (subscribedToYoutuber == 'y' || subscribedToYoutuber == 'Y')
    val isAdded = youtuberAPI.add(Youtuber(youtuberName = youtuberName, youtuberChannelName = youtuberChannelName, youtuberYearJoined = youtuberYearJoined,  youtuberSubscribers = youtuberSubscribers,  subscribedToYoutuber = booleanSubscribed))

    if (isAdded) {
        println("YouTuber Added Successfully")
    } else {
        println("Failed to add YouTuber")
    }
}

//Add youtubers video
private fun addVideoToYoutuber() {
    val video: Youtuber? = askUserToChooseYoutuber()
    if (video != null) {
        if (video.addVideo(Video(
                videoTitle = readNextLine("Video Title: "),
                videoCategory = readValidCategory("Enter a category for the video from ${VideoCategoryValidation.categories}: "),
                isVideoLiked = readValidVideoLikedStatus("Have you ${VideoLikedValidation.likedVideo} this video?: " ),
                videoRating = readValidRating("Video rating (1-5): "),
                watchedStatus = readValidWatchedStatus("What is your current watch status for this video?: ${VideoWatchedStatusValidation.watched}: "))))
            println("Video Added Successful!")
        else println("Failed to add Video")
    }
}

//List all youtubers
fun listAllYoutubers() = println(youtuberAPI.listAllYoutubers())

//List videos added to the youtuber
fun listYoutuberVideos(){
    val video: Youtuber? = askUserToChooseYoutuber()
    if(video != null){
        println(video.listVideos())
    }
    else println("Unable to list YouTubers at this moment, please try again later!")
}

//Update youtuber
fun updateYoutuber() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber if it exists
        val id = readNextInt("Enter the ID of the YouTuber you want to update: ")
        if (youtuberAPI.findYoutuber(id) != null) {
            val youtuberName = readNextLine("Enter the name of the YouTuber you would like to add: ")
            val youtuberChannelName = readNextLine("Enter their YouTube channel name: ")
            val youtuberYearJoined = readValidYear("Enter the year they created their YouTube channel: ")
            val youtuberSubscribers = readValidSubscribers("Enter how many subscribers do they currently have: ")
            val subscribedToYoutuber = readNextChar("Are you subscribed to this YouTuber?: y/n ")
            val booleanSubscribed = (subscribedToYoutuber == 'y' || subscribedToYoutuber == 'Y')

            // pass the ID of the youtuber and the new youtuber details to YoutuberAPI for updating and check for success.
            if (youtuberAPI.update(id, Youtuber(0, youtuberName, youtuberChannelName, youtuberYearJoined,youtuberSubscribers, subscribedToYoutuber = booleanSubscribed ))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no YouTubers linked to this ID")
        }
    }
}

fun updateVideoContents(){
    val youtuber: Youtuber? = askUserToChooseYoutuber()
    if (youtuber != null) {
        val video: Video? = askUserToChooseVideo(youtuber)
        if (video != null) {
            val newVideo = readNextLine("Enter an updated video title: ")
            val newCategory = readValidCategory("Enter an updated category for the video from ${VideoCategoryValidation.categories}: ")
            val newIsVideoLiked = readValidVideoLikedStatus("Enter an updated liked status from ${VideoLikedValidation.likedVideo}: ")
            val newRating = readValidRating("Enter an updated video rating: ")
            val newWatchedStatus = readValidWatchedStatus("What is your updated watch status for this video?: ${VideoWatchedStatusValidation.watched}: ")
            if (youtuber.update(video.videoId, Video(
                    videoTitle = newVideo,
                    videoCategory = newCategory,
                    isVideoLiked = newIsVideoLiked,
                    videoRating = newRating,
                    watchedStatus = newWatchedStatus
                ))) {
                println("Video Details Updated Successfully!")
            } else {
                println("Video details NOT updated")
            }
        } else {
            println("Invalid video Id")
        }
    }
}

//Delete youtuber
fun deleteYoutuber() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber to delete if it exists
        val id = readNextInt("Enter the id of the YouTuber you'd like to delete: ")
        // pass the index of the youtuber to YoutuberAPI for deleting and check for success.
        val youtuberToDelete = youtuberAPI.delete(id)
        if (youtuberToDelete) {
            println("YouTuber deleted Successfully!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

//Delete video
fun deleteAVideo() {
    val youtuber: Youtuber? = askUserToChooseYoutuber()
    if (youtuber != null) {
        val video: Video? = askUserToChooseVideo(youtuber)
        if (video != null) {
            val isDeleted = youtuber.delete(video.videoId)
            if (isDeleted) {
                println("Video Deleted Successfully!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

//------------------------------------
// Add youtuber to favourites
//------------------------------------
fun addYoutuberToFavs() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber to favourite if youtuber exists
        val indexToFavourite = readNextInt("Enter the number of the YouTuber to add to your favourites: ")
        // pass the index of the youtuber to YoutuberAPI for favouring and check for success.
        if (youtuberAPI.addYoutuberToFavourites(indexToFavourite)) {
            println("YouTuber Added to Favourites Successful!\n")
        } else {
            println("Add to Favourites NOT Successful\n")
        }
    }
}

//------------------------------------
// Mark video as watched
//------------------------------------

fun markVideoAsWatched() {
    val youtube: Youtuber? = askUserToChooseYoutuber()
    if (youtube != null) {
        val video: Video? = askUserToChooseVideo(youtube)
        if (video != null) {
            val changeStatus: Char
            if (video.markVideoAsWatched) {
                changeStatus = readNextChar("This video is currently marked as watched...do you want to update the watched status to 'Yet to watch'? (y/n)")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    video.markVideoAsWatched = false
                    video.watchedStatus = ("Yet to watch")

            }
            else {
                changeStatus = readNextChar("This video is currently Yet to watch...do you want to mark it as Watched? (y/n)")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    video.markVideoAsWatched = true
                    video.watchedStatus = ("Watched")

            }
        }
    }
}

//--------------------------------------------
// More involved listing methods
//--------------------------------------------

//List non-favourite youtubers
fun listNFYoutubers() = println(youtuberAPI.listNonFavouriteYoutubers())

//List favourite youtubers
fun listFYoutubers() = println(youtuberAPI.listFavouriteYoutubers())

//List YouTubers in order of highest to the lowest subscriber count
fun listInSubOrder() = println(youtuberAPI.listYoutubersInOrderOfSubCount())

//List YouTubers in order of old-new channels based on the year they joined
fun listNewToOldChannels() = println(youtuberAPI.listYoutubersFromNewestToOldestChannel())

//list watched videos
fun listWatchedVideos(){
    if (youtuberAPI.numberOfWatchedVideos() > 0) {
        println("Total watched videos: ${youtuberAPI.numberOfWatchedVideos()}")
        println(youtuberAPI.listWatchedVideos())
    }
    else println("No videos marked as watched")
}

//------------------------------------
// Searching methods
//------------------------------------
fun searchYoutuberByChannel() {

    val searchTitle = readNextLine("Search for channel name: ")
    val searchResults = youtuberAPI.searchYoutuberByChannelName(searchTitle)
    if (searchResults.isEmpty()) {
        println("No YouTubers found\n")
    } else {
        println(searchResults)
    }
}

fun searchYoutuberBySubCount() {

    val searchSubCount = readNextInt("Enter the subscriber count to search by ")
    val searchResults = youtuberAPI.searchYoutuberBySubCount(searchSubCount)
    if (searchResults.isEmpty()) {
        println("No YouTubers found\n")
    } else {
        println(searchResults)
    }
}

//search videos by title
fun searchVideosByTitle() {
    val searchContents = readNextLine("Enter the video title to search by: ")
    val searchResults = youtuberAPI.searchVideoByTitle(searchContents)
    if (searchResults.isEmpty()) {
        println("No videos found")
    } else {
        println(searchResults)
    }
}

//search videos by category
fun searchVideosByCategory() {
    val searchContents = readNextLine("Enter the video category to search by: ")
    val searchResults = youtuberAPI.searchVideoByCategory(searchContents)
    if (searchResults.isEmpty()) {
        println("No videos found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting app ... Thank you for using! :)")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------
private fun askUserToChooseYoutuber(): Youtuber? {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        val youtuber = youtuberAPI.findYoutuber(readNextInt("\nEnter the ID of the Youtuber: "))
        if (youtuber != null) {
            return youtuber //chosen youtuber is active
        }
        else {
            println("Youtuber ID is not valid")
        }
    }
    return null
}

private fun askUserToChooseVideo(youtuber: Youtuber): Video? {
    return if (youtuber.numberOfVideos() > 0) {
        print(youtuber.listVideos())
        youtuber.findOne(readNextInt("\nEnter the ID of the video: "))
    }
    else{
        println ("No videos stored for chosen Youtuber")
        null
    }
}
