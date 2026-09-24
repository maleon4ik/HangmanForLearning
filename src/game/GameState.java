// src.game.GameState.java
package game;

public class GameState {
    GameData gameData;
    private int triesLeft;
    private int triesUsed;
    private boolean gameWon;
    private boolean gameLost;

    public GameState(GameData gameData) {
        this.gameData = gameData;
        this.triesLeft = gameData.getParts().length;
        this.gameLost = gameData.getParts().length == 0;
        this.gameWon = false;
    }



    void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public int getTriesUsed() {
        return triesUsed;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public void decreaseTries() {
        if (triesLeft == 1) {
            triesLeft--;
            triesUsed++;
            gameLost = true;
        } else if (triesLeft > 0) {
            triesLeft--;
            triesUsed++;
        } else {
            throw new IllegalStateException("Количество попыток не может быть отрицательным.");
        }
    }

    public String[] getPartsLeft() {
        String[] result = new String[triesLeft];
        for (int i = 0; i < result.length; i++) {
            result[i] = gameData.getPart(i + triesUsed);
        }
        return result;
    }

    public boolean isEndOfGame() {
        return(gameWon || gameLost);
    }
}
