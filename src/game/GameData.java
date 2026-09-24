// src.game.GameData.java
package game;

import resources.words.WordBank;

public class GameData {
    private String[] parts;
    private WordBank wordBank;
    private String secretWord;
    private StringBuilder maskedWord = new StringBuilder();
    private StringBuilder lettersUsed = new StringBuilder();

    GameData() {
        this.parts = new String[]{"Основание", "Столб", "Веревка", "Голова", "Тело", "Руки", "Ноги"};
    }

    void setParts(String[] parts) {
        this.parts = parts;
    }

    void setWordBank(WordBank wordBank) {
        this.wordBank = wordBank;
    }

    void setSecretWord(int index) {
        this.secretWord = wordBank.getWord(index);
    }

    void createMaskedWord() {
        maskedWord.repeat("_", secretWord.length());
    }

    void setMaskedWord(String maskedWord) {
        this.maskedWord = new StringBuilder(maskedWord);
    }

    void setMaskedLetter(int index, char letter) {
        maskedWord.replace(index, index + 1, String.valueOf(letter));
    }

    String[] getParts() {
        return parts;
    }

    String getPart(int index) {
        return parts[index];
    }

    String[] getWordArray() {
        return wordBank.getWordBank();
    }

    String getSecretWord() {
        return secretWord.toLowerCase();
    }

    String getMaskedWord() {
        return maskedWord.toString();
    }

    void addLetterUsed(char letter) {
        lettersUsed.append(letter);
    }

    StringBuilder getLettersUsed() {
        return lettersUsed;
    }
}
