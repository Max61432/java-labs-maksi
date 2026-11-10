package edu.course.lab04;

import java.util.Objects;

public final class SampleId {
    private final String datasetName;
    private final int sampleNumber;

    public SampleId(String datasetName, int sampleNumber) {
        if (datasetName == null || datasetName.isEmpty()) {
            throw new IllegalArgumentException("Имя набора не может быть пустым");
        }
        this.datasetName = datasetName;
        this.sampleNumber = sampleNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SampleId sampleId = (SampleId) o;
        return sampleNumber == sampleId.sampleNumber && 
               Objects.equals(datasetName, sampleId.datasetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datasetName, sampleNumber);
    }
}