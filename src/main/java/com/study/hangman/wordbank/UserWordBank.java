package com.study.hangman.wordbank;

import com.study.hangman.game.GameManager;

public class UserWordBank extends WordBank {

    public UserWordBank() {

    }

    public UserWordBank(String[] wordBank) {
        this.wordsArray = wordBank;
    }

    @Override
    protected void setWordsArray(String[] wordsArray) {
        this.wordsArray = wordsArray;
    }
}
