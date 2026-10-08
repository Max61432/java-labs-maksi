package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseToolkitTest {

    // ---------- isEven ----------

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }

    // Подготовительная часть: отрицательное чётное число.
    @Test
    void returnsTrueForNegativeEvenNumber() {
        boolean result = CourseToolkit.isEven(-8);

        assertTrue(result);
    }

    // ---------- isPrime ----------

    @Test
    void isPrimeReturnsFalseForNegativeNumber() {
        assertFalse(CourseToolkit.isPrime(-7));
    }

    @Test
    void isPrimeReturnsFalseForZeroAndOne() {
        assertFalse(CourseToolkit.isPrime(0));
        assertFalse(CourseToolkit.isPrime(1));
    }

    @Test
    void isPrimeReturnsTrueForTwo() {
        assertTrue(CourseToolkit.isPrime(2));
    }

    @Test
    void isPrimeReturnsTrueForLargePrime() {
        assertTrue(CourseToolkit.isPrime(97));
    }

    @Test
    void isPrimeReturnsFalseForComposite() {
        assertFalse(CourseToolkit.isPrime(100));
    }

    // Именно этот тест ловит ошибку с "<" вместо "<=".
    @Test
    void isPrimeReturnsFalseForSquareOfPrime() {
        assertFalse(CourseToolkit.isPrime(49));
    }

    // ---------- isPalindrome ----------

    @Test
    void isPalindromeReturnsTrueForSimplePalindrome() {
        assertTrue(CourseToolkit.isPalindrome("level"));
    }

    @Test
    void isPalindromeReturnsFalseForNonPalindrome() {
        assertFalse(CourseToolkit.isPalindrome("hello"));
    }

    @Test
    void isPalindromeReturnsTrueForEmptyString() {
        assertTrue(CourseToolkit.isPalindrome(""));
    }

    @Test
    void isPalindromeReturnsTrueForSingleChar() {
        assertTrue(CourseToolkit.isPalindrome("a"));
    }

    // Регистр значим: "Level" != "leveL".
    @Test
    void isPalindromeIsCaseSensitive() {
        assertFalse(CourseToolkit.isPalindrome("Level"));
    }

    // Пробелы значимы.
     @Test
    void isPalindromeIsSpaceSensitive() {
        assertFalse(CourseToolkit.isPalindrome("a b"));
    }

    @Test
    void isPalindromeThrowsForNull() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.isPalindrome(null));
    }

    // ---------- average ----------

    @Test
    void averageReturnsFractionalResult() {
        double result = CourseToolkit.average(new int[]{1, 2, 3, 4});

        assertEquals(2.5, result, 1e-9);
    }

    @Test
    void averageWorksWithNegativeNumbers() {
        double result = CourseToolkit.average(new int[]{-5, 5, -10});

        assertEquals(-10.0 / 3.0, result, 1e-9);
    }

    @Test
    void averageDoesNotModifyInputArray() {
        int[] values = {3, 1, 2};

        CourseToolkit.average(values);

        assertEquals(3, values[0]);
        assertEquals(1, values[1]);
        assertEquals(2, values[2]);
    }

    @Test
    void averageThrowsForNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(null));
    }

    @Test
    void averageThrowsForEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(new int[]{}));
    }
}
