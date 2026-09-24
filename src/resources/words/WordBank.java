// src.resources.words.WordBank.java
package resources.words;

public abstract class WordBank {
    protected String[] wordBank;

    public WordBank() {

    }

    public abstract void setWordBank();

    public String[] getWordBank() {
        return wordBank;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < wordBank.length; i++) {
            result.append(wordBank[i] + " ");
            if ((i + 1) % 10 == 0) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    public String getWord(int index) {
        return wordBank[index];
    }
}
