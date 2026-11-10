package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SampleFileLoader {

    public LoadResult load(Path path) {
        LoadResult result = new LoadResult();
        List<String> lines;

        // Чтение файла (Ошибка источника)
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DataLoadingException("Cannot read file " + path, e);
        }

        if (lines.isEmpty()) {
            return result; // Пустой файл — нет данных, нет ошибок
        }

        // Проверка заголовка
        String header = lines.get(0);
        if (!header.equals("id;label;source;featureCount;status")) {
            throw new DataLoadingException("Invalid header format", null);
        }

        // Обработка строк начиная со 2-й
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1; // Номер строки для людей (с 1)

            if (line.trim().isEmpty()) {
                continue; // Пропускаем пустые строки
            }

            String[] parts = line.split(";", -1); // -1, чтобы сохранить пустые поля в конце

            if (parts.length != 5) {
                result.addError(new ValidationError(lineNumber, "general", "Expected 5 fields, got " + parts.length));
                continue;
            }

            String id = parts[0].trim();
            String label = parts[1].trim();
            String source = parts[2].trim();
            String featureCountStr = parts[3].trim();
            String statusStr = parts[4].trim();

            boolean hasError = false;

            if (id.isEmpty()) {
                result.addError(new ValidationError(lineNumber, "id", "ID cannot be empty"));
                hasError = true;
            }
            if (label.isEmpty()) {
                result.addError(new ValidationError(lineNumber, "label", "Label cannot be empty"));
                hasError = true;
            }
            if (source.isEmpty()) {
                result.addError(new ValidationError(lineNumber, "source", "Source cannot be empty"));
                hasError = true;
            }

            int featureCount = -1;
            try {
                featureCount = Integer.parseInt(featureCountStr);
                if (featureCount < 0) {
                    result.addError(new ValidationError(lineNumber, "featureCount", "Must be non-negative"));
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                result.addError(new ValidationError(lineNumber, "featureCount", "Invalid number"));
                hasError = true;
            }

            SampleStatus status = null;
            try {
                status = SampleStatus.valueOf(statusStr);
            } catch (IllegalArgumentException e) {
                result.addError(new ValidationError(lineNumber, "status", "Unknown status"));
                hasError = true;
            }

            if (!hasError) {
                result.addValidSample(new DataSample(id, label, source, featureCount, status));
            }
        }

        return result;
    }
}