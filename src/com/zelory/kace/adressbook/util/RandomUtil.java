package com.zelory.kace.adressbook.util;

import java.util.Random;

public class RandomUtil {
    private static RandomUtil INSTANCE;

    private Random random;

    public static RandomUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (RandomUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RandomUtil();
                }
            }
        }
        return INSTANCE;
    }

    private RandomUtil() {
        random = new Random();
    }

    public int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public int randomInt(int max) {
        return random.nextInt(max);
    }
}
