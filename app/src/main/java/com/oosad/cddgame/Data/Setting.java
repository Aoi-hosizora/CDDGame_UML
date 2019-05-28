package com.oosad.cddgame.Data;

import com.oosad.cddgame.Data.Entity.Player.User;

import java.io.Serializable;

public class Setting implements Serializable {

    private int GameBGMVoloum;
    private int GameOtoVoloum;
    private User currUser;

    private static Setting instance;

    public static Setting getInstance() {
        if (instance == null) {
            instance = new Setting();
            instance.GameBGMVoloum = instance.GameOtoVoloum = 50;
            instance.currUser = new User("Default");
        }
        return instance;
    }

    public void resetSetting() {
        this.GameBGMVoloum = this.GameOtoVoloum = 50;
        this.currUser = null;
    }

    private Setting() {}

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public int getGameBGMVoloum() {
        return GameBGMVoloum;
    }

    public int getGameOtoVoloum() {
        return GameOtoVoloum;
    }

    public void setGameBGMVoloum(int gameBGMVoloum) {
        GameBGMVoloum = gameBGMVoloum;
    }

    public void setGameOtoVoloum(int gameOtoVoloum) {
        GameOtoVoloum = gameOtoVoloum;
    }

}
