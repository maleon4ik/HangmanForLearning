package com.study.hangman.model;

import java.nio.file.Path;
import java.time.LocalDateTime;

public record SessionInfo(String dateTime, String userWord, String secretWord, boolean isWon, WordBankType wordBankType, String path) {

    public static SessionInfo defaultBank(String userWord, String secretWord, boolean isWon) {
        return new SessionInfo(
                LocalDateTime.now().toString(),
                userWord,
                secretWord,
                isWon,
                WordBankType.DEFAULT_WORD_BANK,
                ""
        );
    }

    public static SessionInfo userBank(String userWord, String secretWord, boolean isWon, String path) {
        return new SessionInfo(
                LocalDateTime.now().toString(),
                userWord,
                secretWord,
                isWon,
                WordBankType.USER_WORD_BANK,
                path
        );
    }

}
