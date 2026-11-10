package edu.course.lab06;

import java.util.*;

public class SampleAnalytics {

    // 1. Число наблюдений по статусам через Map
    public Map<SampleStatus, Integer> countByStatus(List<DataSample> samples) {
        Map<SampleStatus, Integer> counts = new HashMap<>();
        for (DataSample sample : samples) {
            counts.put(sample.getStatus(), counts.getOrDefault(sample.getStatus(), 0) + 1);
        }
        return counts;
    }

    // 2. Уникальные источники через Set
    public Set<String> getUniqueSources(List<DataSample> samples) {
        Set<String> sources = new HashSet<>();
        for (DataSample sample : samples) {
            sources.add(sample.getSource());
        }
        return sources;
    }

    // 3. Новый список, отсортированный по числу признаков и идентификатору
    public List<DataSample> sortByFeaturesAndId(List<DataSample> samples) {
        // Создаем новый список (чтобы не менять исходный)
        List<DataSample> sorted = new ArrayList<>(samples);
        
        // Сортируем с помощью Comparator (без вычитания чисел!)
        sorted.sort(Comparator
                .comparingInt(DataSample::getFeatureCount)
                .thenComparing(DataSample::getId)
        );
        
        return sorted;
    }
}