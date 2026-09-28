package com.study.hangman.game;

import com.study.hangman.model.IterationResult;
import com.study.hangman.userinteraction.GetFromUser;
import com.study.hangman.userinteraction.ShowToUser;

import java.util.Arrays;

public class GameLoopLetterOnlyAction implements GameLoopAction {

    private final ShowToUser showToUser;
    private final GetFromUser getFromUser;
    private final Gallows gallows;

    public GameLoopLetterOnlyAction(ShowToUser showToUser, GetFromUser getFromUser, Gallows gallows) {
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

    private char askLetter(GameState gameState) {
        char userChar;
        do {
            showToUser.showStringLn("Type 1 letter:");
            userChar = getFromUser.getChar();
            if (!Character.isLetter(userChar)) {
                showToUser.showStringLn("Your character is not a letter: " + userChar);
                continue;
            }
            if (gameState.getLettersUsed().contains(userChar)) {
                showToUser.showStringLn("You've already used this letter: " + userChar);
            }
            return userChar;
        } while (true);
    }

    @Override
    public IterationResult guess(GameState gameState) {
        char userLetter = askLetter(gameState);
        return new IterationResult(
                userLetter,
                gameState.tryLetter(userLetter),
                gameState.isGameOver(),
                gameState.isWon()
                );
    }

    @Override
    public void printWinInfo(GameState gameState, IterationResult iterationResult) {
        showToUser.showStringLn("Congratulations, you won!");
        showToUser.showStringLn("The secret word was " + gameState.getSecretWord(iterationResult));
    }

    @Override
    public void printLoseInfo(GameState gameState, IterationResult iterationResult) {
        showToUser.showStringLn("You lost");
        showToUser.showStringLn("The secret word was " + gameState.getSecretWord(iterationResult));
    }


}
