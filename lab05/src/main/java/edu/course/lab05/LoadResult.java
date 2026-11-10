package edu.course.lab05;

import java.util.ArrayList;
import java.util.List;

public final class LoadResult {
    private final List<DataSample> validSamples = new ArrayList<>();
    private final List<ValidationError> errors = new ArrayList<>();

    public void addValidSample(DataSample sample) {
        validSamples.add(sample);
    }

    public void addError(ValidationError error) {
        errors.add(error);
    }

    public List<DataSample> getValidSamples() { return validSamples; }
    public List<ValidationError> getErrors() { return errors; }
}