package com.study.hangman.game;

import com.study.hangman.userinteraction.GetFromUser;
import com.study.hangman.userinteraction.ShowToUser;
import com.study.hangman.wordbank.DefaultWordBank;
import com.study.hangman.wordbank.UserWordBank;
import com.study.hangman.wordbank.WordBank;
import com.study.hangman.wordbank.UserWordBankManager;

import java.io.File;

public class GameSetting {

    private final ShowToUser showToUser;
    private final GetFromUser getFromUser;
    private final UserWordBankManager userWordBankManager;


    GameSetting(ShowToUser showToUser, GetFromUser getFromUser, UserWordBankManager userWordBankManager) {
        this.showToUser = showToUser;
        this.getFromUser = getFromUser;
        this.userWordBankManager = userWordBankManager;
    }

    WordBank setupWordBank() {
        WordBank wordBank = null;
        do {
            WordBankType type = askWordBankType();
            wordBank = createWordBank(type);
        } while (wordBank == null);
        return wordBank;
    }

    private WordBankType askWordBankType() {
        WordBankType result = null;
        do {
            showToUser.showStringLn("Which word bank would you like to use?");
            switch (getFromUser.getChoice("Default word bank", "Saved word bank", "Create new word bank")) {
                case (1) -> {
                    result = WordBankType.DEFAULT_WORD_BANK;
                }
                case (2) -> {
                    result = WordBankType.JSON_WORD_BANK;
                }
                case (3) -> {
                    result = WordBankType.NEW_WORD_BANK;
                }
            };
        } while (result == null);
        return result;
    }

    private WordBank createWordBank(WordBankType type) {
        switch (type) {
            case DEFAULT_WORD_BANK -> {
                return new DefaultWordBank();
            }
            case JSON_WORD_BANK -> {
                String userPath = askFilePath(FileOperation.LOAD);
                if (userPath == null) {
                    return null;
                }
                return userWordBankManager.loadUserWordBankFromJson(userPath);
            }
            case NEW_WORD_BANK -> {
                String userPath = askFilePath(FileOperation.CREATE);
                if (userPath == null) {
                    return null;
                }
                String[] wordBankArray = askWordsStringArray();
                UserWordBank wordBank = new UserWordBank(wordBankArray);
                userWordBankManager.saveUserWordBankToJson(wordBank, userPath);
                showToUser.showStringLn("You successfully saved you word bank save file to " + userPath);
            }
        }
        return null;
    }

    private String askFilePath(FileOperation fileOperation) {
        String userPath;
        do {
            showToUser.showStringLn("Enter file path or `cancel` if you want to cancel.");
            userPath = getFromUser.getString();
            if (userPath.equalsIgnoreCase("cancel")) {
                return null;
            }
            if (isValidPath(userPath, fileOperation)) {
                return userPath;
            }
        } while (true);
    }

    private boolean isValidPath(String userPath, FileOperation fileOperation) {
        if (userPath == null || userPath.trim().isEmpty()) {
            showToUser.showStringLn("File path can't be empty");
            return false;
        }
        if (!userPath.endsWith(".json")) {
            showToUser.showStringLn("File must be .json type");
            return false;
        }

        File file = new File(userPath);
        switch (fileOperation) {
            case LOAD -> {
                if (!file.exists()) {
                    showToUser.showStringLn("File doesn't exist");
                    return false;
                }
                if (!file.isFile()) {
                    showToUser.showStringLn("File must be a file, not a directory");
                    return false;
                }
                if (!file.canRead()) {
                    showToUser.showStringLn("File can't be read, check reading rights");
                    return false;
                }
            }
            case CREATE -> {
                if (file.exists()) {
                    showToUser.showStringLn("File already exists");
                    int overwrite = getFromUser.getChoice("Change path", "Overwrite");
                    if (overwrite == 1) {
                        showToUser.showStringLn("Change path");
                        return false;
                    }
                }
                File parentDirectory = file.getParentFile();
                if (parentDirectory != null && !parentDirectory.canWrite()) {
                    showToUser.showStringLn("File can't be written, check writing rights");
                    return false;
                }
            }
        }
        return true;
    }

    private String[] askWordsStringArray() {
        showToUser.showStringLn("You are creating a new word bank");
        return getFromUser.getConditionedStringArray("^[\\p{L}-]+$");
    }

    private enum WordBankType {
        DEFAULT_WORD_BANK,
        JSON_WORD_BANK,
        NEW_WORD_BANK
    }

    private enum FileOperation {
        LOAD,
        CREATE
    }
}
