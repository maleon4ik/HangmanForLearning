package com.study.hangman.model;

public record IterationResult(char letter, boolean isCorrect, boolean isGameOver, boolean isGameWon, GameCommand gameCommand) {

    public static IterationResult common(char letter, boolean isCorrect, boolean isGameOver, boolean isGameWon) {
        return new IterationResult(
                letter,
                isCorrect,
                isGameOver,
                isGameWon,
                GameCommand.NONE
        );
    }

    public static IterationResult command(GameCommand gameCommand) {
        return new IterationResult(
                '\0',
                false,
                false,
                false,
                gameCommand
        );
    }

}


