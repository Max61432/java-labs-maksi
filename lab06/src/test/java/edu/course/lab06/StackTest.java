package edu.course.lab06;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void newStackIsEmpty() {
        Stack<String> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void pushAndPeekWork() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.peek()); // Последний зашел, первый вышел
        assertEquals(2, stack.size());
    }

    @Test
    void popRemovesElement() {
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        assertEquals("B", stack.pop());
        assertEquals("A", stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void throwsOnEmptyPopAndPeek() {
        Stack<Double> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @Test
    void throwsOnNullPush() {
        Stack<String> stack = new Stack<>();
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
    }
}