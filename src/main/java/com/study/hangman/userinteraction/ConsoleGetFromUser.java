// src.user.interaction.GetFromUser.java
package com.study.hangman.userinteraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleGetFromUser implements GetFromUser {
    private final Scanner input = new Scanner(System.in);
    private final ShowToUser showToUser;

    public ConsoleGetFromUser(ShowToUser showToUser) {
        this.showToUser = showToUser;
    }

    public int getInt() {
        while (true) {
            try {
                String userInput = getString().trim();
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                showToUser.showStringLn("Вы написали не целое число, либо слишком большое число, повторите попытку: " + e.getMessage());
            } catch (Exception e) {
                showToUser.showStringLn("Произошла непредвиденная ошибка: " + e.getMessage());
            }
        }
    }

    @Override
    public char getChar() {
        while (true) {
            try {
                String userInput = getString().trim();

                if (userInput.isEmpty()) {
                    showToUser.showStringLn("Input can't be empty");
                    continue;
                }
                if (userInput.length() != 1) {
                    showToUser.showStringLn("Input must be a 1-symbol character. Received: " + userInput);
                    continue;
                }

                return userInput.charAt(0);

            } catch (Exception e) {
                showToUser.showStringLn("Unexpected error occurred: " + e.getMessage());
            }
        }
    }

    public String getString() {
        return input.nextLine();
    }

    public String[] getStringArray() {
        List<String> userWords = new ArrayList<>();
        do {
            showToUser.showStringLn("Type next word. If finished, type `stop!`");
            String userInput = getString();
            if (userInput.trim().equals("stop!")) {
                break;
            }
            userWords.add(userInput);
        } while (true);

        return userWords.toArray(new String[0]);
    }

    public String[] getConditionedStringArray(String regexToMatch) {
        List<String> userWords = new ArrayList<>();
        do {
            showToUser.showStringLn("Type next word. If finished, type `/stop`");
            String userInput = getString();
            if (userInput.trim().equals("/stop")) {
                break;
            }
            if (!userInput.matches("^[\\p{L}-]+$")) {
                showToUser.showStringLn("Words must only contain letters and `-` if needed");
                continue;
            }
            userWords.add(userInput);
        } while (true);

        return userWords.toArray(new String[0]);
    }

    public int getChoice(String... choices) {
        int userChoice = 0;
        do {
            for (int i = 1; i <= choices.length; i++) {
                showToUser.showStringLn(i + ": " + choices[i - 1]);
            }

            String userInput = input.nextLine();
            try {
                userChoice = Integer.parseInt(userInput.trim());
            } catch (NumberFormatException e) {
                showToUser.showStringLn("You didn't enter a number");
            } catch (Exception e) {
                throw new RuntimeException("Unexpected error", e);
            }

            if (userChoice < 1 || userChoice > choices.length) {
                showToUser.showStringLn("Only integers from 1 to " + choices.length + " are accepted");
            }

        } while (userChoice < 1 || userChoice > choices.length);
        return userChoice;
    }
}
