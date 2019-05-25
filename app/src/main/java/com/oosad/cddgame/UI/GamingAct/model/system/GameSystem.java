package com.oosad.cddgame.UI.GamingAct.model.system;

public class GameSystem {
    private GameSystem() {}
    private static GameSystem instance;

    public static GameSystem getInstance() {
        if (instance == null) {
            instance = new GameSystem();
            ///
        }
        return instance;
    }
}
