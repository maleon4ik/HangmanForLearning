package com.study.hangman;
import com.study.hangman.game.GameInitiator;
import com.study.hangman.game.GameManager;

public class Main {
    public static void main(String[] args) {
        GameInitiator gameInitiator = new GameInitiator();
        GameManager gameManager = gameInitiator.initiate();
        gameManager.run();
    }
}
