package com.study.hangman.wordbank;

public abstract class WordBank {

    protected String[] wordsArray;

    public String[] getWordsArray() {
        return wordsArray.clone();
    }

    public String getWord(int index) {
        return wordsArray[index];
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < wordsArray.length; i++) {
            result.append(wordsArray[i]).append(" ");
            if ((i + 1) % 10 == 0) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    protected abstract void setWordsArray(String[] wordsArray);
}
