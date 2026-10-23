package edu.course.lab03;

public class PriceCalculator {
    private final DiscountPolicy policy;

    // Полиморфизм: принимаем интерфейс, а не конкретный класс
    public PriceCalculator(DiscountPolicy policy) {
        this.policy = policy;
    }

    public double calculate(double price) {
        return policy.apply(price);
    }
}