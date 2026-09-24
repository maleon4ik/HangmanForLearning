// src.game.GameAction.java
package game;

import resources.words.DefaultWordBank;
import resources.words.UserWordBank;
import user.interaction.ShowToUser;

import java.util.Scanner;
import java.util.Random;

public class GameAction {
    private final ShowToUser showToUser = new ShowToUser();
    private final Scanner input = new Scanner(System.in);
    private final GameData gameData;
    private final GameState gameState;

    public GameAction(GameData gameData, GameState gameState) {
        this.gameData = gameData;
        this.gameState = gameState;
    }

    public void gamePreferences() {
        showToUser.showString("Вы хотите настроить игру? В ином случае, игра настройки будут выставлены по-умолчанию.");
        while (true) {
            showToUser.showString("Введите Да/Нет");
            String userInput = input.nextLine();
            if (userInput.toLowerCase().contains("да") || userInput.toLowerCase().startsWith("д")) {
                gameData.setWordBank(new UserWordBank());
                showToUser.showString("Вы настроили игру.");
                return;
            } else if (userInput.toLowerCase().contains("нет") || userInput.toLowerCase().startsWith("н")) {
                gameData.setWordBank(new DefaultWordBank());
                showToUser.showString("Игра будет продолжена с настройками, выставленными по-умолчанию.");
                return;
            } else {
                showToUser.showString("Вы не ввели \"Да\" или \"Нет\", попробуйте заново.");
            }
        }
    }

    public String getStatus() {
        StringBuilder result = new StringBuilder("Части которые еще не установлены:\n");
        String[] partsLeft = gameState.getPartsLeft();
        for (String part : partsLeft) {
            result.append(part + " ");
        }
        result.append("\nПопыток осталось: " + gameState.getTriesLeft());
        result.append("\nВаше слово: " + gameData.getMaskedWord());
        StringBuilder lettersUsed = gameData.getLettersUsed();
        if (!lettersUsed.isEmpty()) {
            result.append("\nИспользованные буквы: ");
            for (int i = 0; i < lettersUsed.length() - 1; i++) {
                result.append(lettersUsed.charAt(i) + ", ");
            }
            result.append(lettersUsed.charAt(lettersUsed.length() - 1) + ".");
        }

        return result.toString();
    }

    public boolean guess() {
        while (true) {
            showToUser.showString("Введите пожалуйста букву или слово целиком.");
            String userInput = input.nextLine().toLowerCase();
            if (userInput.isEmpty()) {
                showToUser.showString("Ваш ввод не может быть пустым.");
            } else if (userInput.equals(gameData.getSecretWord())) {
                gameData.setMaskedWord(gameData.getSecretWord());
                gameState.setGameWon(true);
                return true;
            } else if (userInput.length() > 1) {
                showToUser.showString("Вы ввели больше одной буквы и не угадали слово целиком. В качестве ввода будет взята первая буква вашего ввода: " + userInput.charAt(0));
                userInput = String.valueOf(userInput.charAt(0));
            }

            if (isLetterUsed(userInput.charAt(0))) {
                showToUser.showString("Буква " + userInput.charAt(0) + " уже была использована, попробуйте другую.");
            } else if (gameData.getSecretWord().contains(String.valueOf(userInput.charAt(0)))) {
                showToUser.showString("Вы угадали букву: " + userInput.charAt(0));
                revealLetter(userInput.charAt(0));
                gameData.addLetterUsed(userInput.charAt(0));
                return true;
            } else {
                showToUser.showString("Вы не угадали букву, " + gameState.getPartsLeft()[0].toLowerCase() + " теперь на месте для виселицы.");
                gameState.decreaseTries();
                gameData.addLetterUsed(userInput.charAt(0));
                return false;
            }
        }
    }

    public void revealLetter(char letter) {
        String secretWord = gameData.getSecretWord();
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                gameData.setMaskedLetter(i, secretWord.charAt(i));
            }
        }
    }

    void revealSecretWord() {
        gameData.setMaskedWord(gameData.getSecretWord());
        showToUser.showString("Секретным словом было слово - " + gameData.getSecretWord());
    }

    void checkStatus() {
        if (gameData.getSecretWord().equals(gameData.getMaskedWord())) {
            gameState.setGameWon(true);
        } else if (gameState.getTriesLeft() == 0) {
            gameState.setGameLost(true);
        }
    }

    void setUpGame() {
        Random random = new Random();
        gameData.setSecretWord(random.nextInt(0, gameData.getWordArray().length));
        gameData.createMaskedWord();
    }

    public boolean isLetterUsed(char letter) {
        return gameData.getLettersUsed().indexOf(String.valueOf(letter)) != -1;
    }

}
