package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataSampleTest {

    // ---------- Корректное создание ----------

    @Test
    void constructorCreatesSampleWithNewStatus() {
        DataSample sample = new DataSample("s1", "cat", new double[]{1.0, 2.0});

        assertEquals("s1", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals(SampleStatus.NEW, sample.getStatus());
        assertFalse(sample.isReady());
    }

    // ---------- changeStatus и isReady ----------

    @Test
    void changeStatusUpdatesStatus() {
        DataSample sample = new DataSample("s1", "cat", new double[]{1.0});
        sample.changeStatus(SampleStatus.PROCESSING);

        assertEquals(SampleStatus.PROCESSING, sample.getStatus());
    }

    @Test
    void isReadyReturnsTrueWhenStatusIsReady() {
        DataSample sample = new DataSample("s1", "cat", new double[]{1.0});
        sample.changeStatus(SampleStatus.READY);

        assertTrue(sample.isReady());
    }

    @Test
    void changeStatusThrowsForNull() {
        DataSample sample = new DataSample("s1", "cat", new double[]{1.0});

        assertThrows(IllegalArgumentException.class,
                () -> sample.changeStatus(null));
    }

    // ---------- average ----------

    @Test
    void averageReturnsAverageOfFeatures() {
        DataSample sample = new DataSample("s1", "cat", new double[]{1.0, 2.0, 3.0, 4.0});

        assertEquals(2.5, sample.average(), 1e-9);
    }

    @Test
    void averageWorksWithNegativeFeatures() {
        DataSample sample = new DataSample("s1", "cat", new double[]{-3.0, 3.0});

        assertEquals(0.0, sample.average(), 1e-9);
    }

    @Test
    void averageOfSingleFeatureEqualsThatFeature() {
        DataSample sample = new DataSample("s1", "cat", new double[]{42.0});

        assertEquals(42.0, sample.average(), 1e-9);
    }

    // ---------- Защита от изменений снаружи ----------

    @Test
    void constructorCopiesFeaturesArray() {
        double[] external = {1.0, 2.0, 3.0};
        DataSample sample = new DataSample("s1", "cat", external);

        external[0] = 999.0;

        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, sample.getFeatures(), 1e-9);
    }

    @Test
    void getFeaturesReturnsCopyNotInternalArray() {
        DataSample sample = new DataSample("s1", "cat", new double[]{1.0, 2.0});
        double[] returned = sample.getFeatures();

        returned[0] = 999.0;

        assertArrayEquals(new double[]{1.0, 2.0}, sample.getFeatures(), 1e-9);
    }

    // ---------- Нарушения инвариантов в конструкторе ----------

    @Test
    void constructorThrowsForNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample(null, "cat", new double[]{1.0}));
    }

    @Test
    void constructorThrowsForEmptyId() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("", "cat", new double[]{1.0}));
    }

    @Test
    void constructorThrowsForNullLabel() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", null, new double[]{1.0}));
    }

    @Test
    void constructorThrowsForEmptyLabel() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "", new double[]{1.0}));
    }

    @Test
    void constructorThrowsForNullFeatures() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", null));
    }

    @Test
    void constructorThrowsForEmptyFeatures() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", new double[]{}));
    }

    @Test
    void constructorThrowsForNaNFeature() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", new double[]{1.0, Double.NaN}));
    }

    @Test
    void constructorThrowsForInfiniteFeature() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("s1", "cat", new double[]{1.0, Double.POSITIVE_INFINITY}));
    }
}