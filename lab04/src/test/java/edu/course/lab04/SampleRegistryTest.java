package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

class SampleRegistryTest {

    @Test
    void equalKeysFindSameValue() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id1 = new SampleId("cats", 1);
        SampleId id2 = new SampleId("cats", 1); // Другой экземпляр, но логически равный

        registry.addOrReplace(id1, new DataSample("cat1", new double[]{1.0}));
        
        assertNotNull(registry.get(id2)); // Находим по второму ключу
    }

    @Test
    void addingWithEqualKeyReplacesValueAndDoesNotIncreaseSize() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id1 = new SampleId("cats", 1);
        SampleId id2 = new SampleId("cats", 1);

        registry.addOrReplace(id1, new DataSample("old", new double[]{1.0}));
        registry.addOrReplace(id2, new DataSample("new", new double[]{2.0}));

        assertEquals(1, registry.size());
        assertEquals("new", registry.get(id1).getLabel());
    }

    @Test
    void unequalKeysWithSameHashCodeStoreDifferentValues() {
        // В эксперименте используем специальный ключ с одинаковым хешем
        Map<CollisionKey, String> map = new HashMap<>();
        CollisionKey key1 = new CollisionKey("A");
        CollisionKey key2 = new CollisionKey("B");

        assertEquals(key1.hashCode(), key2.hashCode()); // Доказываем, что хеш одинаковый

        map.put(key1, "Value1");
        map.put(key2, "Value2");

        assertEquals(2, map.size());
        assertEquals("Value1", map.get(key1));
        assertEquals("Value2", map.get(key2));
    }

    @Test
    void equalsReturnsFalseForNullOrOtherType() {
        SampleId id = new SampleId("cats", 1);
        
        assertFalse(id.equals(null));
        assertFalse(id.equals("строка"));
    }

    @Test
    void mutableKeyDemonstratesLookupProblem() {
        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey("initial");
        map.put(key, "Value");

        key.setValue("changed"); // Меняем поле, участвующее в equals/hashCode

        // Ищем по измененному ключу — не находим, потому что его хеш изменился!
        assertNull(map.get(key));
    }

    // --- Вспомогательные классы для тестов ---

    static class CollisionKey {
        private final String val;
        CollisionKey(String val) { this.val = val; }
        
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return Objects.equals(val, ((CollisionKey) o).val);
        }
        @Override public int hashCode() { return 42; } // Намеренно одинаковый хеш
    }

    static class MutableKey {
        private String val;
        MutableKey(String val) { this.val = val; }
        public void setValue(String val) { this.val = val; }
        
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return Objects.equals(val, ((MutableKey) o).val);
        }
        @Override public int hashCode() { return Objects.hash(val); }
    }
}