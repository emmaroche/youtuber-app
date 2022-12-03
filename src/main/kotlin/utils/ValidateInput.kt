package utils

import java.util.*

object ValidateInput {
    @JvmStatic
    fun readValidVideoLikedStatus(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (VideoLikedValidation.isValidVideoLikeStatus(input))
                return input
            else {
                print(" $input is an Invalid option. Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    @JvmStatic
    fun readValidCategory(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (VideoCategoryValidation.isValidCategory(input))
                return input
            else {
                print(" $input is an invalid category.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    @JvmStatic
    fun readValidWatchedStatus(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (VideoWatchedStatusValidation.videoWatchedStatus(input))
                return input
            else {
                print(" $input is an invalid status option.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    @JvmStatic
    fun readValidRating(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1 ,5))
                return input
            else {
                print(" $input is an invalid rating, please choose a rating between 1 & 5.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }

    @JvmStatic
    fun readValidYear(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 2005 ,2022))
                return input
            else {
                print(" $input is an invalid date, please choose a date ranging between 2005 & 2022.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }

    @JvmStatic
    fun readValidSubscribers(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 0 ,300000000))
                return input
            else {
                print(" $input is an invalid subscriber amount, choose an amount between 0 & 300 million.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }
}



