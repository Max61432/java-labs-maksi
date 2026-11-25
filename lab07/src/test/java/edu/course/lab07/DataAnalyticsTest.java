package edu.course.lab07;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalyticsTest {

    private final DataAnalytics analytics = new DataAnalytics();

    @Test
    void filterByStatusWorks() {
        List<DataSample> samples = List.of(
                new DataSample("1", "A", "cam", 10, SampleStatus.READY),
                new DataSample("2", "B", "cam", 20, SampleStatus.NEW),
                new DataSample("3", "C", "cam", 30, SampleStatus.READY)
        );

        List<DataSample> ready = analytics.filterByStatus(samples, SampleStatus.READY);
        assertEquals(2, ready.size());

        List<DataSample> empty = analytics.filterByStatus(samples, SampleStatus.REJECTED);
        assertTrue(empty.isEmpty()); // Тест пустого результата
    }

    @Test
    void getSortedUniqueSourcesWorks() {
        List<DataSample> samples = List.of(
                new DataSample("1", "A", "CAM-1", 10, SampleStatus.READY),
                new DataSample("2", "B", "cam-1", 20, SampleStatus.NEW), // Дубликат после lowerCase
                new DataSample("3", "C", "MIC", 30, SampleStatus.READY)
        );

        List<String> sources = analytics.getSortedUniqueSources(samples);
        assertEquals(List.of("cam-1", "mic"), sources);
    }

    @Test
    void countBySourceWorks() {
        List<DataSample> samples = List.of(
                new DataSample("1", "A", "cam-1", 10, SampleStatus.READY),
                new DataSample("2", "B", "cam-2", 20, SampleStatus.NEW),
                new DataSample("3", "C", "cam-1", 30, SampleStatus.READY)
        );

        Map<String, Long> counts = analytics.countBySource(samples);
        assertEquals(2L, counts.get("cam-1"));
        assertEquals(1L, counts.get("cam-2"));
    }

    @Test
    void findWithMaxFeaturesWorks() {
        List<DataSample> samples = List.of(
                new DataSample("1", "A", "cam", 10, SampleStatus.READY),
                new DataSample("2", "B", "cam", 50, SampleStatus.NEW)
        );

        Optional<DataSample> max = analytics.findWithMaxFeatures(samples);
        assertTrue(max.isPresent());
        assertEquals("2", max.get().getId());

        // Тест на отсутствие максимального элемента (пустой список)
        Optional<DataSample> emptyMax = analytics.findWithMaxFeatures(List.of());
        assertFalse(emptyMax.isPresent());
    }

    @Test
    void sortByFeaturesAndIdWorks() {
        List<DataSample> samples = List.of(
                new DataSample("B", "Label", "cam", 20, SampleStatus.READY),
                new DataSample("A", "Label", "cam", 20, SampleStatus.NEW), // Равный featureCount, сортируем по ID
                new DataSample("C", "Label", "cam", 10, SampleStatus.READY)
        );

        List<DataSample> sorted = analytics.sortByFeaturesAndId(samples);
        assertEquals("C", sorted.get(0).getId());
        assertEquals("A", sorted.get(1).getId());
        assertEquals("B", sorted.get(2).getId());
    }

    @Test
    void doesNotModifyOriginalList() {
        List<DataSample> original = new ArrayList<>(List.of(
                new DataSample("1", "A", "cam", 30, SampleStatus.READY),
                new DataSample("2", "B", "cam", 10, SampleStatus.NEW)
        ));
        List<DataSample> copy = new ArrayList<>(original);

        analytics.sortByFeaturesAndId(original);
        analytics.filterByStatus(original, SampleStatus.READY);

        assertEquals(copy, original); // Исходный список не изменился
    }
}