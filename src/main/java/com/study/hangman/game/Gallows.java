package com.study.hangman.game;

import java.util.Arrays;

public class Gallows {

    public static final String[] PARTS = new String[]{"Основание", "Столб", "Веревка", "Голова", "Тело", "Руки", "Ноги"};

    public int getMaxMistakes() {
        return PARTS.length;
    }

    public String[] getPartsLeft(int mistakesMade) {
        return Arrays.stream(PARTS).skip(mistakesMade).toArray(String[]::new);
    }

    public String[] getPartsUsed(int mistakesMade) {
        return Arrays.stream(PARTS).limit(mistakesMade).toArray(String[]::new);
    }
}
