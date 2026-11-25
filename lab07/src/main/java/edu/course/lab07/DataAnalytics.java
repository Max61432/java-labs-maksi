package edu.course.lab07;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataAnalytics {

    // 1. Отфильтровать объекты по статусу
    public List<DataSample> filterByStatus(List<DataSample> samples, SampleStatus status) {
        return samples.stream()
                .filter(s -> s.getStatus() == status)
                .collect(Collectors.toList());
    }

    // 2. Отсортированный список нормализованных источников (без дубликатов)
    public List<String> getSortedUniqueSources(List<DataSample> samples) {
        return samples.stream()
                .map(DataSample::getSource)
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // 3. Посчитать число объектов по источнику (категории) через groupingBy
    public Map<String, Long> countBySource(List<DataSample> samples) {
        return samples.stream()
                .collect(Collectors.groupingBy(
                        DataSample::getSource,
                        Collectors.counting()
                ));
    }

    // 4. Найти объект с максимальным числом признаков и вернуть Optional
    public Optional<DataSample> findWithMaxFeatures(List<DataSample> samples) {
        return samples.stream()
                .max(Comparator.comparingInt(DataSample::getFeatureCount));
    }

    // 5. Новый список, отсортированный по двум ключам (число признаков, затем ID)
    public List<DataSample> sortByFeaturesAndId(List<DataSample> samples) {
        return samples.stream()
                .sorted(Comparator.comparingInt(DataSample::getFeatureCount)
                        .thenComparing(DataSample::getId))
                .collect(Collectors.toList());
    }
}