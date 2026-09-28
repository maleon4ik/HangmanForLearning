package com.study.hangman.wordbank;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class UserWordBankManager {

    private final ObjectMapper mapper = new ObjectMapper();

    public void saveUserWordBankToJson(UserWordBank userWordBank, String path) {
        try {
            mapper.writeValue(new File(path), userWordBank);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserWordBank loadUserWordBankFromJson(String path) {
        UserWordBank loadedWordBank;
        try {
            loadedWordBank = mapper.readValue(new File(path), UserWordBank.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loadedWordBank;
    }

}
