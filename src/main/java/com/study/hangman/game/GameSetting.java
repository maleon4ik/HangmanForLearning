package com.study.hangman.game;

import com.study.hangman.model.SessionInfo;
import com.study.hangman.model.WordBankInfo;
import com.study.hangman.model.WordBankType;
import com.study.hangman.userinteraction.GetFromUser;
import com.study.hangman.userinteraction.ShowToUser;
import com.study.hangman.wordbank.DefaultWordBank;
import com.study.hangman.wordbank.UserWordBank;
import com.study.hangman.wordbank.WordBank;
import com.study.hangman.wordbank.UserWordBankStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameSetting {

    private final ShowToUser showToUser;
    private final GetFromUser getFromUser;
    private final UserWordBankStorage userWordBankStorage;


    GameSetting(ShowToUser showToUser, GetFromUser getFromUser, UserWordBankStorage userWordBankStorage) {
        this.showToUser = showToUser;
        this.getFromUser = getFromUser;
        this.userWordBankStorage = userWordBankStorage;
    }

    WordBankInfo setupWordBank() {
        WordBank wordBank = null;
        WordBankType wordBankType = null;
        String path = "";
        do {
            WordBankSetupOption setupOption = askWordBankType();
            switch (setupOption) {
                case DEFAULT_WORD_BANK -> {
                    wordBankType = WordBankType.DEFAULT_WORD_BANK;
                    wordBank = new DefaultWordBank();
                }
                case USER_WORD_BANK -> {
                    wordBankType = WordBankType.USER_WORD_BANK;
                    path = askFilePath(FileOperation.LOAD);
                    if (path.isEmpty()) {
                        continue;
                    }
                    try {
                        wordBank = userWordBankStorage.loadUserWordBankFromJson(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case NEW_WORD_BANK -> {
                    wordBankType = WordBankType.USER_WORD_BANK;
                    path = askFilePath(FileOperation.CREATE);
                    String[] wordBankArray;
                    do {
                        wordBankArray = askWordsStringArray();
                    } while (wordBankArray.length == 0);
                    try {
                        userWordBankStorage.saveUserWordBankToJson(new UserWordBank(wordBankArray), path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    showToUser.showStringLn("You successfully saved you word bank save file to " + Path.of(path).toAbsolutePath());
                    try {
                        wordBank = userWordBankStorage.loadUserWordBankFromJson(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } while (wordBank == null);
        return new WordBankInfo(
                wordBankType,
                wordBank,
                path
        );
    }

    WordBankInfo setupWordBank(SessionInfo sessionInfo) throws IOException {
        WordBankType wordBankType = sessionInfo.wordBankType();
        WordBank wordBank;
        String path = sessionInfo.path();
        if (sessionInfo.wordBankType() == WordBankType.USER_WORD_BANK) {
            wordBank = userWordBankStorage.loadUserWordBankFromJson(path);
        } else {
            wordBank = new DefaultWordBank();
        }
        return new WordBankInfo(
                wordBankType,
                wordBank,
                path
        );
    }

    private WordBankSetupOption askWordBankType() {
        WordBankSetupOption result = null;
        do {
            showToUser.showStringLn("Which word bank would you like to use?");
            switch (getFromUser.getChoice("Default word bank", "Saved word bank", "Create new word bank")) {
                case (1) -> result = WordBankSetupOption.DEFAULT_WORD_BANK;
                case (2) -> result = WordBankSetupOption.USER_WORD_BANK;
                case (3) -> result = WordBankSetupOption.NEW_WORD_BANK;
            }
        } while (result == null);
        return result;
    }

    private String askFilePath(FileOperation fileOperation) {
        String userPath;
        do {
            showToUser.showStringLn("Enter file path or `/cancel` if you want to cancel.");
            userPath = getFromUser.getString();
            if (userPath.equalsIgnoreCase("/cancel")) {
                return "";
            }
            if (isValidPath(userPath, fileOperation)) {
                userPath = userPath.replace(" ", "-");
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
        try {
            Paths.get(userPath);
        } catch (InvalidPathException e) {
            showToUser.showStringLn("File path is bad: " + userPath);
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
        showToUser.showStringLn("You are creating a new word bank. You must enter at least 1 word");
        return getFromUser.getConditionedStringArray("^[\\p{L}-]+$");
    }

    private enum WordBankSetupOption {
        DEFAULT_WORD_BANK,
        USER_WORD_BANK,
        NEW_WORD_BANK
    }

    private enum FileOperation {
        LOAD,
        CREATE
    }
}
