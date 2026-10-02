package com.study.hangman.history;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.hangman.model.SessionInfo;
import com.study.hangman.userinteraction.ShowToUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    private final ShowToUser showToUser;

    private final ObjectMapper mapper = new ObjectMapper();
    private final String defaultSavePath = "./saves/sessions-history.json";

    public HistoryStorage(ShowToUser showToUser) {
        this.showToUser = showToUser;
    }

    public void saveHistory(List<SessionInfo> sessionInfos) {
        do {
            try {
                mapper.writeValue(new File(defaultSavePath), sessionInfos);
                break;
            } catch (IOException e) {
                showToUser.showStringLn("File can't be accessed, a new file is going to be created." + e.getMessage());
                createSaveFile();
            }
        } while (true);
    }

    public List<SessionInfo> loadHistory() {
        List<SessionInfo> result = null;
        Path path = Path.of(defaultSavePath);
        do {
            try {
                if (!Files.exists(path)) {
                    showToUser.showStringLn("The history file does not exist. Creating an new history file");
                    createSaveFile();
                    return new ArrayList<>();
                }
                if (Files.size(path) == 0) {
                    showToUser.showStringLn("The history file is empty. Creating a new history list");
                    return new ArrayList<>();
                }
                result = mapper.readValue(new File(defaultSavePath), new TypeReference<List<SessionInfo>>() {});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (result == null);
        return result;
    }

    private void createSaveFile() {
        Path path = Paths.get(defaultSavePath);

        try {
            Files.createDirectories(path.toAbsolutePath().getParent());
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException("File might already exist", e);
        }
    }

}
