package com.study.hangman.wordbank;

public class UserWordBank extends WordBank {

    public UserWordBank(String[] wordBank) {
        this.wordsArray = wordBank;
    }

    @Override
    protected void setWordsArray(String[] wordsArray) {
        this.wordsArray = wordsArray;
    }
}
