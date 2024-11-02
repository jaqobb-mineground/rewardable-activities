package dev.jaqobb.rewardable_activities.util;

import java.util.Random;

public final class RandomUtils {
    
    private static final Random RANDOM = new Random();
    
    private RandomUtils() {
        throw new UnsupportedOperationException("Cannot create instance of this class");
    }
    
    public static double getRandomDouble(double minimum, double maximum) {
        return (RANDOM.nextDouble() * (maximum - minimum)) + minimum;
    }
    
    public static boolean chance(double chance) {
        return getRandomDouble(0.0D, 100.0D) <= chance;
    }
}
