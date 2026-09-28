package com.study.hangman.game;

import com.study.hangman.userinteraction.ConsoleGetFromUser;
import com.study.hangman.userinteraction.ConsoleShowToUser;
import com.study.hangman.userinteraction.GetFromUser;
import com.study.hangman.userinteraction.ShowToUser;
import com.study.hangman.wordbank.UserWordBankManager;
import com.study.hangman.wordbank.WordBank;

public class GameInitiator {

    public GameManager initiate() {
        ShowToUser showToUser = new ConsoleShowToUser();
        GetFromUser getFromUser = new ConsoleGetFromUser(showToUser);

        UserWordBankManager userWordBankManager = new UserWordBankManager();
        GameSetting gameSetting = new GameSetting(showToUser, getFromUser, userWordBankManager);
        WordBank wordBank = gameSetting.setupWordBank();

        Gallows gallows = new Gallows();
        GameStateFactory gameStateFactory = new GameStateFactory(wordBank, gallows);
        GameLoopAction gameLoopAction = new GameLoopLetterOnlyAction(showToUser, getFromUser, gallows);

        return new GameManager(showToUser, gameStateFactory, gallows, gameLoopAction);
    }

}
