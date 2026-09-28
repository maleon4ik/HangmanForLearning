package com.study.hangman.model;

public record IterationResult(char userLetter, boolean isCorrect, boolean isGameOver, boolean isGameWon) {
}
