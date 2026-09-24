// src.Main.java
import game.GameInstruction;
import user.interaction.GetFromUser;
import user.interaction.ShowToUser;

public class Main {
    public static void main(String[] args) {
        GetFromUser getFromUser = new GetFromUser();
        ShowToUser showToUser = new ShowToUser();
        while (true) {
            GameInstruction gameInstruction = new GameInstruction();
            gameInstruction.startGame();
            if (!getFromUser.wantToContinue()) {
                showToUser.showLeaveMessage();
                break;
            }
        }
    }
}
