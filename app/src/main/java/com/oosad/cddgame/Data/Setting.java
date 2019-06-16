package com.oosad.cddgame.Data;

import com.oosad.cddgame.Data.Entity.Player.User;

import java.io.Serializable;

public class Setting implements Serializable {

    private int GameBGMVoloum;
    private int GameOtoVoloum;

    private static Setting instance;

    public static Setting getInstance() {
        if (instance == null) {
            instance = new Setting();
            instance.GameBGMVoloum = instance.GameOtoVoloum = 50;
        }
        return instance;
    }

    public void resetSetting() {
        this.GameBGMVoloum = this.GameOtoVoloum = 50;
    }

    private Setting() {}

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
