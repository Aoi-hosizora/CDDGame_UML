package com.oosad.cddgame.Data.Adapter;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Util.RobotShowCardUtil;

public class NormalRobotShowCardAdapter implements RobotShowCardAdapter {

    @Override
    public Card[] showCard(Card[] HasCards, Card[] PreCards) {

        return RobotShowCardUtil.searchShowCard(PreCards,HasCards);
    }
}
