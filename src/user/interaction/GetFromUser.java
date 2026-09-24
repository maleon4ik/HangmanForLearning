// src.user.interaction.GetFromUser.java
package user.interaction;

import java.util.Scanner;

public class GetFromUser {
    Scanner input = new Scanner(System.in);
    ShowToUser showToUser = new ShowToUser();

    public int getInt() {
        int result;
        while (true) {
            try {
                String userInput = input.nextLine();
                userInput.trim(); // Убираем лишние пробелы
                result = Integer.parseInt(userInput);
                return result;
            } catch (NumberFormatException e) {
                showToUser.showString("Вы написали не целое число, либо слишком большое число, повторите попытку: " + e.getMessage());
            } catch (Exception e) {
                showToUser.showString("Произошла непредвиденная ошибка: " + e.getMessage());
            }
        }
    }

    public String[] getStringArray() {
        showToUser.showString("Сколько слов вы собираетесь вписать?");
        int length = 0;
        while (length == 0) {
            showToUser.showString("Введите пожалуйста целое число не равное нулю.");
            length = Math.abs(getInt());
        }
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            showToUser.showString("Введите слово:");
            String userWord = input.nextLine();
            array[i] = userWord;
            if (userWord.isEmpty()) {
                showToUser.showString("Слово не может быть пустым, повторите попытку.");
                i--;
            }
        }
        showToUser.showString("Ваш массив готов.");
        return array;
    }

    public boolean wantToContinue() {
        showToUser.showString("Вы хотите продолжить играть? Игра начнется заново. Настройки не сохраняются.");
        while (true) {
            showToUser.showString("Введите Да/Нет");
            String userInput = input.nextLine();
            if (userInput.toLowerCase().contains("да") || userInput.toLowerCase().startsWith("д")) {
                return true;
            } else if (userInput.toLowerCase().contains("нет") || userInput.toLowerCase().startsWith("н")) {
                return false;
            } else {
                showToUser.showString("Вы не ввели \"Да\" или \"Нет\", попробуйте заново.");
            }
        }
    }
}
