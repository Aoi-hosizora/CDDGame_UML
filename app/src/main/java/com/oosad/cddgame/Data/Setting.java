package com.oosad.cddgame.Data;

public class Setting {

    private int GameBGMVoloum;
    private int GameOtoVoloum;
    private User currUser;

    private static Setting instance;

    public static Setting getInstance() {
        if (instance == null) {
            instance = new Setting();
        }
        return instance;
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
