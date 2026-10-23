package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NormalizationStrategyTest {

    @Test
    void minMaxNormalizationWorksCorrectly() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] input = {10.0, 20.0, 30.0};
        double[] expected = {0.0, 0.5, 1.0};
        
        assertArrayEquals(expected, processor.process(input), 1e-9);
    }

    @Test
    void meanCenteringWorksCorrectly() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] input = {10.0, 20.0, 30.0}; // среднее = 20
        double[] expected = {-10.0, 0.0, 10.0};
        
        assertArrayEquals(expected, processor.process(input), 1e-9);
    }

    @Test
    void processorDoesNotModifyInputArray() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] input = {10.0, 20.0, 30.0};
        
        processor.process(input);
        
        // Проверяем, что исходный массив не изменился
        assertArrayEquals(new double[]{10.0, 20.0, 30.0}, input, 1e-9);
    }

    @Test
    void processorCanSwapStrategies() {
        double[] input = {10.0, 20.0, 30.0};
        
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, processor.process(input), 1e-9);

        // Заменяем стратегию, не меняя класс FeatureProcessor
        processor = new FeatureProcessor(new MeanCenteringNormalization());
        assertArrayEquals(new double[]{-10.0, 0.0, 10.0}, processor.process(input), 1e-9);
    }
}