package com.study.hangman.game;

import com.study.hangman.model.GameCommand;
import com.study.hangman.model.IterationResult;
import com.study.hangman.userinteraction.GetFromUser;
import com.study.hangman.userinteraction.ShowToUser;

import java.util.Arrays;

public class GameSessionActionWithCommands implements GameSessionAction {

    private final ShowToUser showToUser;
    private final GetFromUser getFromUser;
    private final Gallows gallows;

    public GameSessionActionWithCommands(ShowToUser showToUser, GetFromUser getFromUser, Gallows gallows) {
        this.showToUser = showToUser;
        this.getFromUser = getFromUser;
        this.gallows = gallows;
    }


    @Override
    public void printIterationInfo(GameState gameState) {
        showToUser.showString("Parts left:\t\t\t\t");
        Arrays.stream(gallows.getPartsLeft(gameState.getMistakesMade())).forEach((String part) -> {
            showToUser.showString(part + "\t\t");
        });
        showToUser.showStringLn("");

        showToUser.showString("Parts hanging:\t\t\t");
        Arrays.stream(gallows.getPartsUsed(gameState.getMistakesMade())).forEach((String part) -> {
            showToUser.showString(part + "\t\t");
        });
        showToUser.showStringLn("");

        showToUser.showStringLn("Your current word is:\t" + gameState.getUserWord());
    }

    @Override
    public IterationResult guess(GameState gameState) {
        String userInputString;
        char userInputChar;
        do {
            showToUser.showStringLn("Type 1 letter (or /stop):");
            userInputString = getFromUser.getString().trim().toLowerCase();
            if (userInputString.isEmpty()) {
                showToUser.showStringLn("Input can't be empty.");
                continue;
            }

            if (userInputString.equals("/stop")) {
                return IterationResult.command(GameCommand.STOP);
            }

            userInputChar = userInputString.charAt(0);

            if (userInputString.length() != 1) {
                showToUser.showStringLn("Your input is too long to be a single letter, and it is not a command.");
                continue;
            }
            if (!Character.isLetter(userInputChar)) {
                showToUser.showStringLn("Your input is not a letter or a command: " + userInputString);
                continue;
            }
            if (gameState.getLettersUsed().contains(userInputChar)) {
                showToUser.showStringLn("You've already used this letter: " + userInputString);
                continue;
            }

            break;
        } while (true);

        boolean isCorrect = gameState.tryLetter(userInputChar);
        return new IterationResult(
                userInputChar,
                isCorrect,
                gameState.isGameOver(),
                gameState.isWon(),
                GameCommand.NONE
                );
    }

    @Override
    public void printWinInfo(GameState gameState, IterationResult iterationResult) {
        showToUser.showStringLn("Congratulations, you won!");
        showToUser.showStringLn("The secret word was " + gameState.getSecretWord(iterationResult.isGameOver()));
    }

    @Override
    public void printLoseInfo(GameState gameState, IterationResult iterationResult) {
        showToUser.showStringLn("You lost");
        showToUser.showStringLn("The secret word was " + gameState.getSecretWord(iterationResult.isGameOver()));
    }


}
