package com.oosad.cddgame.Data.Entity.Player;

import com.oosad.cddgame.Data.Controller.CardMgr;
import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Data.Entity.Card;

import java.io.Serializable;
import java.util.Random;

public class Robot extends Player implements Serializable {

    public Robot(int id) {
        this.Id = id;
    }

    @Override
    public boolean IsRobot() {
        return true;
    }

    private int Id;

    public int getId() {
        return this.Id;
    }

    /**
     * 机器人的出牌，出牌算法待改，(AI)
     * 调用了 canShowCardWithCheckTurn
     * @return
     */
    public boolean showCard() {
        Card[] cards = CardMgr.getInstance().getPlayerCards(this);

        Card[] showcards;
        if (cards.length != 1)
            showcards = new Card[] {cards[0], cards[1]};
        else
            showcards = new Card[] {cards[0]};

        /// 算法待改

        GameSystem.getInstance().canShowCardWithCheckTurn(showcards, this);

        if (onRobotShowCard != null) {
            if (showcards.length != 0)
                onRobotShowCard.onShowCard(showcards); // 顺便通知订阅者更新
            else
                onRobotShowCard.onPassCard();
        }
        return true;
    }

    public interface OnRobotShowCardListener {
        void onShowCard(Card[] cards);
        void onPassCard();
    }

    /**
     * 出牌事件监听器
     */
    public OnRobotShowCardListener onRobotShowCard;

    public void setOnRobotShowCard(OnRobotShowCardListener onRobotShowCard) {
        this.onRobotShowCard = onRobotShowCard;
    }
}
