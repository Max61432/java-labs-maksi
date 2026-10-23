package edu.course.lab03;

public class MeanCenteringNormalization implements NormalizationStrategy {
    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым");
        }
        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        double mean = sum / values.length;
        
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] - mean;
        }
        return result;
    }
}