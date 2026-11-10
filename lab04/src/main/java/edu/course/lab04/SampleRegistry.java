package edu.course.lab04;

import java.util.HashMap;
import java.util.Map;

public class SampleRegistry {
    private final Map<SampleId, DataSample> storage = new HashMap<>();

    public void addOrReplace(SampleId id, DataSample sample) {
        storage.put(id, sample);
    }

    public DataSample get(SampleId id) {
        return storage.get(id);
    }

    public void remove(SampleId id) {
        storage.remove(id);
    }

    public boolean containsKey(SampleId id) {
        return storage.containsKey(id);
    }

    public int size() {
        return storage.size();
    }
}