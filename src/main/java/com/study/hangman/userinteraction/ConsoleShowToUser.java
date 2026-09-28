// src.user.interaction.ShowToUser.java
package com.study.hangman.userinteraction;

public class ConsoleShowToUser implements ShowToUser {

    public ConsoleShowToUser() {

    }

    @Override
    public void showString(String s) {
        System.out.print(s);
    }

    @Override
    public void showStringLn(String s) {
        System.out.println(s);
    }

}
