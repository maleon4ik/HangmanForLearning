package com.study.hangman.game;

import com.study.hangman.model.IterationResult;

import java.util.*;

public class GameState {

    private final Gallows gallows;

    private final String secretWord;
    private final StringBuilder userWord;
    private final List<Character> lettersUsed;
    private int mistakesMade;

    GameState(String secretWord, Gallows gallows) {
        this.gallows = gallows;
        this.secretWord = secretWord;
        this.userWord = new StringBuilder(createUserWord());
        this.lettersUsed = new ArrayList<>();
        this.mistakesMade = 0;
    }

    private String createUserWord() {
        return "_".repeat(secretWord.length());
    }

    String getSecretWord(boolean isGameOver) {
        if (isGameOver) {
            return secretWord;
        }
        return null;
    }

    public String getUserWord() {
        return userWord.toString();
    }

    public List<Character> getLettersUsed() {
        return Collections.unmodifiableList(lettersUsed);
    }

    public int getMistakesMade() {
        return mistakesMade;
    }

    public boolean tryLetter(char letter) {
        lettersUsed.add(letter);
        int firstIndexOfLetter = secretWord.indexOf(letter);
        if (firstIndexOfLetter == -1) {
            mistakesMade++;
            return false;
        }
        for (int i = firstIndexOfLetter; i < secretWord.length(); i++) {
            if (String.valueOf(letter).equals(secretWord.substring(i, i + 1))) {
                userWord.replace(i, i + 1, String.valueOf(letter));
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return isWon() || (mistakesMade == gallows.getMaxMistakes());
    }

    public boolean isWon() {
        return userWord.toString().equals(secretWord);
    }
}
