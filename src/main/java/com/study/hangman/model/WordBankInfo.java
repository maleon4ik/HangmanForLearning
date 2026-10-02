package com.study.hangman.model;

import com.study.hangman.wordbank.WordBank;

public record WordBankInfo(WordBankType wordBankType, WordBank wordBank, String path) {
}
