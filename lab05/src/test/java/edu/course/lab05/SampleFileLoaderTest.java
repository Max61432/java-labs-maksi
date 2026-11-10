package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SampleFileLoaderTest {

    @TempDir
    Path tempDir;

    private final SampleFileLoader loader = new SampleFileLoader();
    private final String HEADER = "id;label;source;featureCount;status";

    @Test
    void loadsValidFile() throws IOException {
        Path file = tempDir.resolve("valid.csv");
        Files.write(file, List.of(HEADER, "S-101;cat;camera-1;128;READY"), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(1, result.getValidSamples().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("S-101", result.getValidSamples().get(0).getId());
    }

    @Test
    void handlesUnicodeText() throws IOException {
        Path file = tempDir.resolve("unicode.csv");
        Files.write(file, List.of(HEADER, "S-102;кот;камера-1;64;READY"), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(1, result.getValidSamples().size());
        assertEquals("кот", result.getValidSamples().get(0).getLabel());
    }

    @Test
    void catchesEmptyField() throws IOException {
        Path file = tempDir.resolve("empty_field.csv");
        Files.write(file, List.of(HEADER, "S-101;;camera-1;128;READY"), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("label", result.getErrors().get(0).getField());
    }

    @Test
    void catchesInvalidNumber() throws IOException {
        Path file = tempDir.resolve("invalid_num.csv");
        Files.write(file, List.of(HEADER, "S-101;cat;camera-1;abc;READY"), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidSamples().size());
        assertEquals("featureCount", result.getErrors().get(0).getField());
    }

    @Test
    void catchesUnknownStatus() throws IOException {
        Path file = tempDir.resolve("bad_status.csv");
        Files.write(file, List.of(HEADER, "S-101;cat;camera-1;128;UNKNOWN"), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidSamples().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void catchesWrongColumnCount() throws IOException {
        Path file = tempDir.resolve("wrong_cols.csv");
        Files.write(file, List.of(HEADER, "S-101;cat;camera-1;READY"), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("general", result.getErrors().get(0).getField());
    }

    @Test
    void handlesMixedValidAndInvalidLines() throws IOException {
        Path file = tempDir.resolve("mixed.csv");
        Files.write(file, List.of(
                HEADER,
                "S-101;cat;camera-1;128;READY", // Валидная
                "S-102;dog;camera-1;abc;READY", // Ошибка числа
                "S-103;bird;camera-2;64;PROCESSING" // Валидная
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(2, result.getValidSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber()); // Ошибка на 3-й строке
    }

    @Test
    void throwsExceptionForMissingFile() {
        Path missingFile = tempDir.resolve("does_not_exist.csv");

        DataLoadingException ex = assertThrows(DataLoadingException.class, () -> loader.load(missingFile));
        assertNotNull(ex.getCause()); // Проверяем, что исходная причина (IOException) сохранена
    }
}