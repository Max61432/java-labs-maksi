package edu.course.lab06;

public final class DataSample {
    private final String id;
    private final String source;
    private final int featureCount;
    private final SampleStatus status;

    public DataSample(String id, String source, int featureCount, SampleStatus status) {
        this.id = id;
        this.source = source;
        this.featureCount = featureCount;
        this.status = status;
    }

    public String getId() { return id; }
    public String getSource() { return source; }
    public int getFeatureCount() { return featureCount; }
    public SampleStatus getStatus() { return status; }
}