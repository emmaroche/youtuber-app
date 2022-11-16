import controllers.YoutuberAPI
import utils.ScannerInput.readNextInt
import kotlin.system.exitProcess

private val youtuberAPI = YoutuberAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addYoutuber()
            2 -> listYoutubers()
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
         > |                  Youtuber KEEPER APP              |
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
fun addYoutuber() {
    println("Add youtuber")
}

fun listYoutubers() {
    println("List youtubers")
}

fun updateYoutuber() {
    println("Update youtuber")
}

fun deleteYoutuber() {
    println("Delete youtuber")
}

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

