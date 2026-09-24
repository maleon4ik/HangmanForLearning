// src.game.GameInstruction.java
package game;

import resources.words.WordBank;
import user.interaction.ShowToUser;


public class GameInstruction {
    private final ShowToUser showToUser = new ShowToUser();
    private final GameData gameData = new GameData();
    private final GameState gameState = new GameState(gameData);
    private final GameAction gameAction = new GameAction(gameData, gameState);

    public void startGame() {
        gameAction.gamePreferences();
        gameAction.setUpGame();
        showToUser.showGameRules();
        showToUser.showString("Игра началась!");
        while (!gameState.isEndOfGame()) {
            showToUser.showString(gameAction.getStatus());
            gameAction.guess();
            gameAction.checkStatus();
        }
        if (gameState.isGameWon()) {
            showToUser.showWin(gameData.getSecretWord());
        } else {
            showToUser.showLose(gameData.getSecretWord());
        }
    }
}
