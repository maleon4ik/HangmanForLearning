package com.study.hangman.game;

import com.study.hangman.model.WordBankType;
import com.study.hangman.wordbank.DefaultWordBank;
import com.study.hangman.wordbank.UserWordBank;
import com.study.hangman.wordbank.WordBank;

import java.util.Random;

public class GameStateFactory {

    private final Random random = new Random();
    private final WordBank wordBank;
    private final Gallows gallows;

    private final WordBankType wordBankType;

    public GameStateFactory(WordBank wordBank, Gallows gallows) {
        this.wordBank = wordBank;
        this.gallows = gallows;

        if (wordBank instanceof UserWordBank) {
            wordBankType = WordBankType.USER_WORD_BANK;
        } else {
            wordBankType = WordBankType.DEFAULT_WORD_BANK;
        }
    }

    public GameState create() {
        String secretWord = createSecretWord();
        return new GameState(secretWord, gallows);
    }

    private String createSecretWord() {
        String[] wordsArray = wordBank.getWordsArray();
        if (wordsArray.length == 0) {
            throw new IllegalStateException("String array is empty");
        }
        return wordsArray[random.nextInt(0, wordsArray.length)];
    }

    public WordBankType getWordBankType() {
        return wordBankType;
    }

}
