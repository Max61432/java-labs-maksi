package edu.course.lab04;

public final class DataSample {
    private final String label;
    private final double[] features;

    public DataSample(String label, double[] features) {
        this.label = label;
        this.features = features;
    }

    public String getLabel() { return label; }
    public double[] getFeatures() { return features.clone(); }
}