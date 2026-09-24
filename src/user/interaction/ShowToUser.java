// src.user.interaction.ShowToUser.java
package user.interaction;

public class ShowToUser {

    public void showString(String s) {
        System.out.println(s);
    }

    public void showGameRules() {
        System.out.println("Игра Виселица.\nЦель игры - полностью отгадать слово по буквам, не повесив вашего персонажа. У вас есть несколько частей, которые можно повесить. За каждое неправильно введенное слово, вешается одна часть. Как только все части будут повешены, вы проиграете.");
    }

    public void showWin(String secretWord) {
        System.out.println("Поздравляем, вы выиграли, вы угадали секретное слово - " + secretWord + "!");
    }

    public void showLose(String secretWord) {
        System.out.println("У вас не получилось угадать секретное слово, вы проиграли. Загаданным словом было - " + secretWord + ".");
    }

    public void showLeaveMessage() {
        System.out.println("Спасибо за игру.");
    }
}
