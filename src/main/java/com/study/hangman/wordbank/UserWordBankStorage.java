package com.study.hangman.wordbank;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserWordBankStorage {

    private final ObjectMapper mapper = new ObjectMapper();

    public void saveUserWordBankToJson(UserWordBank userWordBank, String path) throws IOException {
        Path pathPath = Path.of(path);
        if (Files.exists(pathPath)) {
            Files.delete(pathPath);
        }
        Files.createDirectories(pathPath.toAbsolutePath().getParent());
        Files.createFile(pathPath);
        File file = new File(path);
        mapper.writeValue(new File(path), userWordBank);
    }

    public UserWordBank loadUserWordBankFromJson(String path) throws IOException {
        return mapper.readValue(new File(path), UserWordBank.class);
    }

}
