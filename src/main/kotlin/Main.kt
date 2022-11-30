import controllers.YoutuberAPI
import models.Youtuber
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
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
            2 -> listAllYoutuber()
            3 -> updateYoutuber()
            4 -> deleteYoutuber()
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

//List all youtubers
fun listAllYoutuber() = println(youtuberAPI.listAllYoutubers())

//Update youtuber
fun updateYoutuber() {
    listAllYoutuber()
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

//Delete youtuber
fun deleteYoutuber() {
    listAllYoutuber()
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

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting app ... Thanks for using! :)")
    exitProcess(0)
}

