// src.resources.words.UserWordBank.java
package resources.words;

import user.interaction.GetFromUser;

public class UserWordBank extends WordBank {
    private final GetFromUser getFromUser = new GetFromUser();

    public UserWordBank() {
        setWordBank();
    }

    @Override
    public void setWordBank() {
        this.wordBank = getFromUser.getStringArray();
    }
}
