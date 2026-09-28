package com.study.hangman.game;

import com.study.hangman.model.IterationResult;
import com.study.hangman.userinteraction.ShowToUser;

import java.util.Arrays;

public class GameManager {

    private final ShowToUser showToUser;
    private final GameStateFactory gameStateFactory;
    private final Gallows gallows;
    private final GameLoopAction gameLoopAction;

    private boolean isRunning;
    private GameState gameState;

    public GameManager(ShowToUser showToUser, GameStateFactory gameStateFactory, Gallows gallows, GameLoopAction gameLoopAction) {
        this.showToUser = showToUser;
        this.gameStateFactory = gameStateFactory;
        this.gallows = gallows;
        this.gameLoopAction = gameLoopAction;
    }

    public void startGame() {
        if (isRunning) {
            showToUser.showStringLn("Game is already running");
            return;
        }

        gameState = gameStateFactory.create();
        isRunning = true;
        showToUser.showStringLn("Game is starting");
        gameLoop();
    }

    public void stopGame() {
        if (!isRunning) {
            showToUser.showStringLn("Game is already stopped");
            return;
        }

        isRunning = false;
        showToUser.showStringLn("Game is stopped");
    }

    public void restartGame() {
        showToUser.showStringLn("Game is restarting");
        stopGame();
        startGame();
    }

    private void gameLoop() {
        while (isRunning) {
            gameLoopAction.printIterationInfo(gameState);
            IterationResult iterationResult = gameLoopAction.guess(gameState);

            if (!iterationResult.isGameOver()) {
                continue;
            }

            if (iterationResult.isGameWon()) {
                gameLoopAction.printWinInfo(gameState, iterationResult);
            } else {
                gameLoopAction.printLoseInfo(gameState, iterationResult);
            }
            break;
        }
    }
}
