package com.study.hangman.game;

import com.study.hangman.model.IterationResult;

public interface GameSessionAction {

    void printIterationInfo(GameState gameState);

    IterationResult guess(GameState gameState);

    void printWinInfo(GameState gameState, IterationResult iterationResult);

    void printLoseInfo(GameState gameState, IterationResult iterationResult);

}
