package com.study.hangman.game;

import com.study.hangman.history.HistoryManager;
import com.study.hangman.model.*;
import com.study.hangman.userinteraction.GetFromUser;
import com.study.hangman.userinteraction.ShowToUser;
import com.study.hangman.wordbank.DefaultWordBank;
import com.study.hangman.wordbank.WordBank;

import java.io.IOException;
import java.util.Objects;

public class GameManager {

    private final ShowToUser showToUser;
    private final GetFromUser getFromUser;
    private final Gallows gallows;
    private final GameSetting gameSetting;
    private final GameSessionAction gameSessionAction;
    private final HistoryManager historyManager;

    public GameManager(ShowToUser showToUser, GetFromUser getFromUser, Gallows gallows, GameSetting gameSetting, GameSessionAction gameSessionAction, HistoryManager historyManager) {
        this.showToUser = showToUser;
        this.getFromUser = getFromUser;
        this.gallows = gallows;
        this.gameSetting = gameSetting;
        this.gameSessionAction = gameSessionAction;
        this.historyManager = historyManager;
    }
    
    public void run() {
        while (true) {
            WordBankInfo wordBankInfo;

            showToUser.showStringLn("\n========HANGMAN GAME========");
            int userChoice = getFromUser.getChoice("Setup and Play", "Play with Previous Settings", "Exit");
            switch (userChoice) {
                case 1 -> wordBankInfo = gameSetting.setupWordBank();
                case 2 -> {
                    if (historyManager.getHistory().isEmpty()) {
                        showToUser.showStringLn("History is empty, nothing to load");
                        continue;
                    }

                    SessionInfo lastSession = historyManager.getHistory().getLast();

                    try {
                        wordBankInfo = gameSetting.setupWordBank(lastSession);
                    } catch (IOException e) {
                        showToUser.showStringLn("History file from the previous play is not accessible.");
                        continue;
                    }
                }
                case 3 -> {
                    showToUser.showStringLn("Thank you for playing hangman");
                    return;
                }
                default -> wordBankInfo = new WordBankInfo(
                        WordBankType.DEFAULT_WORD_BANK,
                        new DefaultWordBank(),
                        ""
                );
            }
            WordBankType wordBankType = wordBankInfo.wordBankType();
            WordBank wordBank = wordBankInfo.wordBank();

            GameStateFactory gameStateFactory = new GameStateFactory(wordBank, gallows);

            GameState gameState = gameStateFactory.create();

            GameOutcome gameOutcome = playGameSession(gameState);

            if (gameOutcome == GameOutcome.STOPPED) {
                continue;
            }

            SessionInfo sessionInfo;
            if (Objects.requireNonNull(wordBankType) == WordBankType.USER_WORD_BANK) {
                sessionInfo = SessionInfo.userBank(
                        gameState.getUserWord(),
                        gameState.getSecretWord(),
                        gameOutcome == GameOutcome.WON,
                        wordBankInfo.path()
                );
            } else {
                sessionInfo = SessionInfo.defaultBank(
                        gameState.getUserWord(),
                        gameState.getSecretWord(),
                        gameOutcome == GameOutcome.WON
                );
            }
            historyManager.addSession(sessionInfo);
            historyManager.saveHistoryToSaveFile();
        }
    }

    private GameOutcome playGameSession(GameState gameState) {
        while (true) {
            gameSessionAction.printIterationInfo(gameState);
            IterationResult iterationResult = gameSessionAction.guess(gameState);

            if (iterationResult.gameCommand() == GameCommand.STOP) {
                return GameOutcome.STOPPED;
            }

            if (!iterationResult.isGameOver()) {
                continue;
            }

            if (iterationResult.isGameWon()) {
                gameSessionAction.printWinInfo(gameState, iterationResult);
                return GameOutcome.WON;
            } else {
                gameSessionAction.printLoseInfo(gameState, iterationResult);
                return GameOutcome.LOST;
            }
        }
    }
}
