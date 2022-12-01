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
                print("         Invalid option $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }
}
