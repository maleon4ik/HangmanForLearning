// src.resources.words.DefaultWordBank.java
package resources.words;

public class DefaultWordBank extends WordBank {

    public DefaultWordBank() {
        setWordBank();
    }

    @Override
    public void setWordBank() {
        this.wordBank = new String[]{"корова", "слон", "арбуз", "гранат", "грант", "телефон", "монитор", "дверь", "вентиляция", "шкаф", "система", "объект", "класс", "файл", "метод", "приложение", "аппаратура", "гарнитура", "текст", "строка", "контроль", "музыка", "стол", "фабрика", "станок", "результат", "конфета", "галактика", "планета", "звезда", "спутник", "солнце"};
    }
}
