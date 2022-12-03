package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.VideoCategoryValidation.categories
import utils.VideoCategoryValidation.isValidCategory

internal class VideoCategoryValidationTest {
    @Test
    fun categoriesReturnsFullCategoriesSet() {
        Assertions.assertEquals(6, categories.size)
        Assertions.assertTrue(categories.contains("Gaming"))
        Assertions.assertTrue(categories.contains("Music"))
        Assertions.assertTrue(categories.contains("Tutorials"))
        Assertions.assertTrue(categories.contains("Entertainment"))
        Assertions.assertTrue(categories.contains("Beauty"))
        Assertions.assertTrue(categories.contains("Comedy"))
    }

    @Test
    fun isValidCategoryTrueWhenCategoryExists() {
        Assertions.assertTrue(isValidCategory("Gaming"))
        Assertions.assertTrue(isValidCategory("gaming"))
        Assertions.assertTrue(isValidCategory("GAMING"))
    }

    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist() {
        Assertions.assertFalse(isValidCategory("Tutori"))
        Assertions.assertFalse(isValidCategory("Entertainmentt"))
        Assertions.assertFalse(isValidCategory("Beauu"))
    }
}
