package ua.edu.nupp.kpp.server;

import java.util.concurrent.ScheduledExecutorService;

public class RiskCalculator {
    public static double factor1(int count, int maxCount) {
        if (count < 0 || maxCount <= 0) {
            throw new IllegalArgumentException("Некоректні дані  F1");
        }
        double value = (double) count / maxCount * 100.0;
        return Math.max(0.0, Math.min(100.0, value));
    }
    public static double calculate(double countF1, double hourF2, double extraEqF3) {
        check(countF1);
        check(hourF2);
        check(extraEqF3);
        return Math.round((0.40 * countF1 + 0.35 * hourF2 + 0.25 * extraEqF3) * 100.0) / 100.0;
    }
    private static void check(double value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Фактор має бути в діапазонв від 0 до 100");
        }
    }
}
