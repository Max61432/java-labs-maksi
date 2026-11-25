package edu.course.lab07;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamBasicsTest {

    @Test
    void testStreamBasics() {
        // Исходный список (изменяемый)
        List<String> original = new ArrayList<>(List.of("java", "", "git", "maven", "api"));
        
        // Сохраняем копию для проверки
        List<String> copy = new ArrayList<>(original);

        // Stream API: фильтрация, маппинг, сортировка
        List<String> result = original.stream()
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .sorted((s1, s2) -> {
                    if (s1.length() != s2.length()) {
                        return Integer.compare(s1.length(), s2.length());
                    }
                    return s1.compareTo(s2);
                })
                .collect(Collectors.toList());

        // Проверяем результат
        assertEquals(List.of("API", "GIT", "JAVA", "MAVEN"), result);
        
        // Проверяем, что исходный список не изменился
        assertEquals(copy, original);
    }
}