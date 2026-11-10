package edu.course.lab06;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class SampleAnalyticsTest {

    private final SampleAnalytics analytics = new SampleAnalytics();
    private final List<DataSample> samples = List.of(
            new DataSample("S3", "cam-1", 64, SampleStatus.READY),
            new DataSample("S1", "cam-2", 128, SampleStatus.NEW),
            new DataSample("S2", "cam-1", 64, SampleStatus.READY),
            new DataSample("S4", "cam-1", 32, SampleStatus.REJECTED)
    );

    @Test
    void countsByStatus() {
        Map<SampleStatus, Integer> counts = analytics.countByStatus(samples);
        assertEquals(2, counts.get(SampleStatus.READY));
        assertEquals(1, counts.get(SampleStatus.NEW));
        assertEquals(1, counts.get(SampleStatus.REJECTED));
        assertNull(counts.get(SampleStatus.PROCESSING));
    }

    @Test
    void findsUniqueSources() {
        Set<String> sources = analytics.getUniqueSources(samples);
        assertEquals(2, sources.size());
        assertTrue(sources.contains("cam-1"));
        assertTrue(sources.contains("cam-2"));
    }

    @Test
    void sortsByFeaturesThenId() {
        List<DataSample> sorted = analytics.sortByFeaturesAndId(samples);
        
        // Ожидаемый порядок: 
        // 32 (S4), затем 64 (S2, S3 - по алфавиту ID), затем 128 (S1)
        assertEquals("S4", sorted.get(0).getId());
        assertEquals("S2", sorted.get(1).getId());
        assertEquals("S3", sorted.get(2).getId());
        assertEquals("S1", sorted.get(3).getId());
    }

    @Test
    void doesNotModifyOriginalList() {
        List<DataSample> sorted = analytics.sortByFeaturesAndId(samples);
        assertNotSame(samples, sorted); // Это разные объекты
        assertEquals("S3", samples.get(0).getId()); // Исходный список не изменился
    }
}