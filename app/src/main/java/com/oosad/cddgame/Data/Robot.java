package com.oosad.cddgame.Data;

import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.model.system.CardMgr;
import com.oosad.cddgame.UI.GamingAct.model.system.GameSystem;

import java.io.Serializable;
import java.util.Random;

public class Robot extends Player implements Serializable {

    public Robot(int id) {
        this.Id = id;
    }

    public OnRobotShowCardListener onRobotShowCard;

    private int Id;

    public int getId() {
        return this.Id;
    }

    public boolean showCard() {
        Card[] cards = CardMgr.getInstance().Get_Robot_1_Cards();
        Random random = new Random();

        Card[] showcards = new Card[] {cards[random.nextInt(cards.length)]};
        GameSystem.getInstance().canShowCardWithCheckTurn(showcards, this);
        if (onRobotShowCard != null) {
            onRobotShowCard.onShowCard(showcards);
        }
        return true;
    }

    public interface OnRobotShowCardListener {
        void onShowCard(Card[] cards);
    }

    public void setOnRobotShowCard(OnRobotShowCardListener onRobotShowCard) {
        this.onRobotShowCard = onRobotShowCard;
    }
}
