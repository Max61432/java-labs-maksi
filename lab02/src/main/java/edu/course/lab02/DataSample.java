package edu.course.lab02;

/**
 * Наблюдение (data sample) в наборе данных для задач ИИ.
 *
 * <p>Инварианты:
 * <ul>
 *   <li>id и label не равны null и не пусты;</li>
 *   <li>status не равен null;</li>
 *   <li>features не равен null, не пуст и содержит только конечные числа;</li>
 *   <li>массив features копируется в конструкторе и при возврате из accessor.</li>
 * </ul>
 */
public final class DataSample {

    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, double[] features) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id не может быть null или пустым");
        }
        if (label == null || label.isEmpty()) {
            throw new IllegalArgumentException("label не может быть null или пустым");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("features не может быть null или пустым");
        }
        for (double value : features) {
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("features должен содержать только конечные числа");
            }
        }

        this.id = id;
        this.label = label;
        this.features = features.clone();
        this.status = SampleStatus.NEW;
    }

    /**
     * Меняет статус наблюдения.
     */
    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Новый статус не может быть null");
        }
        this.status = newStatus;
    }

    /**
     * Возвращает true, если наблюдение готово к использованию.
     */
    public boolean isReady() {
        return status == SampleStatus.READY;
    }

    /**
     * Вычисляет среднее значение признаков.
     */
    public double average() {
        double sum = 0;
        for (double value : features) {
            sum += value;
        }
        return sum / features.length;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SampleStatus getStatus() {
        return status;
    }

    /**
     * Возвращает копию массива признаков, чтобы внешний код не мог
     * изменить состояние объекта через ссылку на внутренний массив.
     */
    public double[] getFeatures() {
        return features.clone();
    }
}