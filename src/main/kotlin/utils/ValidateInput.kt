package utils

import java.util.Scanner

/**
 * This class validates the input of what is entered into each field when adding or updating a YouTuber or Video.
 *
 * If the input does match the validation requirements, the user is prompted to enter an input again.
 *
 * @author Emma Roche
 *
 */

object ValidateInput {
    /**
     * This function validates that the liked status input for a video is correct.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
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

    /**
     * This function validates that the category entered for a video is correct.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
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

    /**
     * This function validates that the watched status entered for a video is correct.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
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

    /**
     * This function validates that the rating entered for a video is correct.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
    @JvmStatic
    fun readValidRating(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1, 5))
                return input
            else {
                print(" $input is an invalid rating, please choose a rating between 1 & 5.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }

    /**
     * This function validates that the year entered for when a YouTuber created their channel is correct.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     * The date validation ranges from the years 2005-2022 because 2005 was  the year YouTube was created so that is the minimum year a Youtuber could've joined YouTube!
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
    @JvmStatic
    fun readValidYear(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 2005, 2022))
                return input
            else {
                print(" $input is an invalid date, please choose a date ranging between 2005 & 2022.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }

    /**
     * This function validates that the subscriber amount entered for a YouTuber is correct.
     * If the entered data isn't actually correct, the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The input is read from the user and verified as correct.
     */
    @JvmStatic
    fun readValidSubscribers(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 0, 300000000))
                return input
            else {
                print(" $input is an invalid subscriber amount, choose an amount between 0 & 300 million.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }
}
