package com.oosad.cddgame.UI.GamingAct.model.system;

import com.oosad.cddgame.Data.Player;
import com.oosad.cddgame.UI.GamingAct.model.Card;

public class CardMgr {

    private CardMgr() {}
    private static CardMgr instance;

    public static CardMgr getInstance() {
        if (instance == null) {
            instance = new CardMgr();

        }
        return instance;
    }

    public boolean checkShowCardThroughRule(Card[] showcards, Player player) {
        return true;
    }
}
