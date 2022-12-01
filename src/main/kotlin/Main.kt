import controllers.YoutuberAPI
import models.Youtuber
import models.Video
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ValidateInput.readValidVideoLikedStatus
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
            5 -> addVideoToYoutuber()
            6 -> listYoutuberVideos()
            7 -> updateVideoContents()
            8 -> deleteAVideo()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |               Youtuber KEEPER APP                 |
         > -----------------------------------------------------  
         > | Youtuber MENU                                     |
         > |   1) Add a Youtuber                               |
         > |   2) List Youtubers                               |
         > |   3) Update a Youtuber                            |
         > |   4) Delete a Youtuber                            |
         > ----------------------------------------------------- 
         > | Video MENU                                        | 
         > |   5) Add video to a youtuber                      |
         > |   6) List contents of a video                     |
         > |   7) Update contents on a video                   |
         > |   8) Delete video                                 | 
         > ----------------------------------------------------- 
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
//Youtuber MENU CRUD
//------------------------------------

//Add youtuber
fun addYoutuber() {
    val youtuberName = ScannerInput.readNextLine("Enter the name of the Youtuber to add: ")
    val youtuberChannelName = ScannerInput.readNextLine("What is their channel name? ")
    val youtuberYearJoined = readNextInt("What year did they join youtube?: ")
    val youtuberSubscribers = readNextInt("How many subscribers do they have?: ")
    val subscribedToYoutuber = ScannerInput.readNextChar("Are you subscribed to this youtuber?: y/n ")
    val booleanSubscribed = (subscribedToYoutuber == 'y' || subscribedToYoutuber == 'Y')
    val isAdded = youtuberAPI.add(Youtuber(youtuberName = youtuberName, youtuberChannelName = youtuberChannelName, youtuberYearJoined = youtuberYearJoined,  youtuberSubscribers = youtuberSubscribers,  subscribedToYoutuber = booleanSubscribed))

    if (isAdded) {
        println("Youtuber Added Successfully")
    } else {
        println("Youtuber Add Failed")
    }
}

//Add youtubers video
private fun addVideoToYoutuber() {
    val video: Youtuber? = askUserToChooseYoutuber()
    if (video != null) {
        if (video.addVideo(Video(
                videoTitle = ScannerInput.readNextLine("\t Video Title: "),
                videoCategory = ScannerInput.readNextLine("\t Video Category: "),
                isVideoLiked = readValidVideoLikedStatus("\t Is video liked: "),
                videoRating = readNextInt("\t Video rating: "),
                watchedStatus = ScannerInput.readNextLine("\t Video Category: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
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
    else println("List NOT Successful")
}

//Update youtuber
fun updateYoutuber() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber if it exists
        val id = readNextInt("Enter the id of the youtuber to update: ")
        if (youtuberAPI.findYoutuber(id) != null) {
            val youtuberName = ScannerInput.readNextLine("Enter the name of the Youtuber to add: ")
            val youtuberChannelName = ScannerInput.readNextLine("What is their channel name? ")
            val youtuberYearJoined = readNextInt("What year did they join youtube?: ")
            val youtuberSubscribers = readNextInt("How many subscribers do they have?: ")
            val subscribedToYoutuber = ScannerInput.readNextChar("Are you subscribed?: y/n ")
            val booleanSubscribed = (subscribedToYoutuber == 'y' || subscribedToYoutuber == 'Y')

            // pass the ID of the youtuber and the new youtuber details to YoutuberAPI for updating and check for success.
            if (youtuberAPI.update(id, Youtuber(0, youtuberName, youtuberChannelName, youtuberYearJoined,youtuberSubscribers, subscribedToYoutuber = booleanSubscribed ))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no youtubers linked to this ID")
        }
    }
}

fun updateVideoContents(){
    val youtuber: Youtuber? = askUserToChooseYoutuber()
    if (youtuber != null) {
        val video: Video? = askUserToChooseVideo(youtuber)
        if (video != null) {
            val newVideo = ScannerInput.readNextLine("Enter new video title: ")
            val newCategory = ScannerInput.readNextLine("Enter new video category: ")
            val newIsVideoLiked = readValidVideoLikedStatus("Is video watched: ")
            val newRating = readNextInt("Enter new video rating: ")
            val newWatchedStatus = ScannerInput.readNextLine("Enter new watched status: ")
            if (youtuber.update(video.videoId, Video(
                    videoTitle = newVideo,
                    videoCategory = newCategory,
                    isVideoLiked = newIsVideoLiked,
                    videoRating = newRating,
                    watchedStatus = newWatchedStatus
                ))) {
                println("Video details updated")
            } else {
                println("Video details NOT updated")
            }
        } else {
            println("Invalid Book Id")
        }
    }
}

//Delete youtuber
fun deleteYoutuber() {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        // only ask the user to choose the youtuber to delete if it exists
        val id = readNextInt("Enter the id of the youtuber to delete: ")
        // pass the index of the youtuber to YoutuberAPI for deleting and check for success.
        val youtuberToDelete = youtuberAPI.delete(id)
        if (youtuberToDelete) {
            println("Delete Successful!")
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
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting app ... Thanks for using! :)")
    exitProcess(0)
}


//------------------------------------
//HELPER FUNCTIONS
//------------------------------------
private fun askUserToChooseYoutuber(): Youtuber? {
    listAllYoutubers()
    if (youtuberAPI.numberOfYoutubers() > 0) {
        val youtuber = youtuberAPI.findYoutuber(readNextInt("\nEnter the id of the note: "))
        if (youtuber != null) {
            return youtuber //chosen youtuber is active
        }
        else {
            println("Youtuber id is not valid")
        }
    }
    return null
}

private fun askUserToChooseVideo(youtuber: Youtuber): Video? {
    if (youtuber.numberOfVideos() > 0) {
        print(youtuber.listVideos())
        return youtuber.findOne(readNextInt("\nEnter the id of the item: "))
    }
    else{
        println ("No items for chosen note")
        return null
    }
}
