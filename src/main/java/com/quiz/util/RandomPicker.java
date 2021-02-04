package com.quiz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPicker {

    private RandomPicker() {
        // restricting instantiation
    }

    public static <T> List<T> pickRandom(final List<T> list, final int count) {
        if (count <= 0 || list.size() <= count) {
            return list;
        }
        final List<T> temp = new ArrayList<>();
        list.forEach(item -> temp.add(item));
        final List<T> picked = new ArrayList<>();
        int i = 0;
        final Random random = new Random();
        while (i < count) {
            final int index = random.nextInt(temp.size() - 1);
            picked.add(temp.remove(index));
        }
        return picked;
    }

}
