package com.study.hangman.wordbank;

public class DefaultWordBank extends WordBank {

    public DefaultWordBank() {
        setWordsArray(createWordBankArray());
    }

    private String[] createWordBankArray() {
        return new String[]{
                "корова", "слон", "арбуз", "гранат", "грант", "телефон", "монитор", "дверь",
                "вентиляция", "шкаф", "система", "объект", "класс", "файл", "метод", "приложение", "аппаратура",
                "гарнитура", "текст", "строка", "контроль", "музыка", "стол", "фабрика", "станок", "результат",
                "конфета", "галактика", "планета", "звезда", "спутник", "солнце"
        };
    }

    @Override
    protected void setWordsArray(String[] wordsArray) {
        this.wordsArray = wordsArray;
    }
}
