package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountPolicyTest {

    @Test
    void noDiscountReturnsOriginalPrice() {
        PriceCalculator calc = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calc.calculate(100.0), 1e-9);
    }

    @Test
    void percentDiscountAppliesCorrectly() {
        PriceCalculator calc = new PriceCalculator(new PercentDiscount(20));
        assertEquals(80.0, calc.calculate(100.0), 1e-9);
    }

    @Test
    void calculatorCanSwapStrategies() {
        // Один и тот же калькулятор — разные стратегии
        PriceCalculator calc = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calc.calculate(100.0), 1e-9);

        calc = new PriceCalculator(new PercentDiscount(50));
        assertEquals(50.0, calc.calculate(100.0), 1e-9);
    }
    
    @Test
    void percentDiscountThrowsForInvalidPercent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-10));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(150));
    }
}
