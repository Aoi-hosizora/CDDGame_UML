package com.oosad.cddgame.Data.Adapter;

import com.oosad.cddgame.Data.Entity.Card;

public class NormalRobotShowCardAdapter implements RobotShowCardAdapter {

    @Override
    public Card[] showCard(Card[] HasCards, Card[] PreCards) {

        // TODO

        return new Card[] { HasCards[0] };
    }
}
