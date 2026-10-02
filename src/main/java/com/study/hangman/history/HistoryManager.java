package com.study.hangman.history;

import com.study.hangman.model.SessionInfo;

import java.util.List;

public class HistoryManager {

    private final HistoryStorage historyStorage;

    private final List<SessionInfo> history;
    public static final int MAX_HISTORY_SIZE = 100;

    public HistoryManager(HistoryStorage historyStorage) {
        this.historyStorage = historyStorage;
        this.history = getHistoryFromSaveFile();
    }

    public void addSession(SessionInfo sessionInfo) {
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
        history.add(sessionInfo);
    }

    public List<SessionInfo> getHistory() {
        return List.copyOf(history);
    }

    public void saveHistoryToSaveFile() {
        historyStorage.saveHistory(history);
    }

    public List<SessionInfo> getHistoryFromSaveFile() {
        return historyStorage.loadHistory();
    }

}
