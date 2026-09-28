package com.study.hangman.userinteraction;

public interface GetFromUser {

    public int getInt();
    public char getChar();
    public String getString();
    public String[] getStringArray();
    public String[] getConditionedStringArray(String regexToMatch);
    public int getChoice(String... choices);

}
