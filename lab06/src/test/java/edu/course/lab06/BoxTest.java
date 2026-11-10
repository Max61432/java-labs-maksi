package edu.course.lab06;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoxTest {

    @Test
    void boxStoresString() {
        Box<String> stringBox = new Box<>("Привет");
        assertEquals("Привет", stringBox.get());
        stringBox.set("Мир");
        assertEquals("Мир", stringBox.get());
    }

    @Test
    void boxStoresInteger() {
        Box<Integer> intBox = new Box<>(123);
        assertEquals(123, intBox.get());
    }

    /*
     * Пример некомпилируемого кода (параметр типа предотвращает эту ошибку):
     * Box<String> box = new Box<>("Текст");
     * box.set(100); // Ошибка компиляции! Нельзя положить Integer в Box<String>.
     */
}