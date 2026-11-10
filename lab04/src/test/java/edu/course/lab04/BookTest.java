package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void booksWithSameIsbnAreEqualAndFoundInMap() {
        Book book1 = new Book("123-456", "Война и мир");
        Book book2 = new Book("123-456", "Война и мир (переиздание)");

        Map<Book, String> map = new HashMap<>();
        map.put(book1, "На полке");

        assertEquals(book1, book2);
        assertEquals("На полке", map.get(book2));
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book book1 = new Book("111", "Книга А");
        Book book2 = new Book("222", "Книга Б");

        assertNotEquals(book1, book2);
    }
}