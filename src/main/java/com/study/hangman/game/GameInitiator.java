package com.study.hangman.game;

import com.study.hangman.history.HistoryManager;
import com.study.hangman.history.HistoryStorage;
import com.study.hangman.userinteraction.ConsoleGetFromUser;
import com.study.hangman.userinteraction.ConsoleShowToUser;
import com.study.hangman.userinteraction.GetFromUser;
import com.study.hangman.userinteraction.ShowToUser;
import com.study.hangman.wordbank.UserWordBankStorage;

public class GameInitiator {

    public GameManager initiate() {
        ShowToUser showToUser = new ConsoleShowToUser();
        GetFromUser getFromUser = new ConsoleGetFromUser(showToUser);

        Gallows gallows = new Gallows();
        GameSetting gameSetting = new GameSetting(showToUser, getFromUser, new UserWordBankStorage());
        GameSessionAction gameSessionAction = new GameSessionActionWithCommands(showToUser, getFromUser, gallows);
        HistoryManager historyManager = new HistoryManager(new HistoryStorage(showToUser));

        return new GameManager(showToUser, getFromUser, gallows, gameSetting, gameSessionAction, historyManager);
    }

}
