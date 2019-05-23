package com.oosad.cddgame.UI.GamingAct.model;

public class Card {

    public Card(int cardNum, CardSuit cardSuit) {
        this.cardNum = cardNum;
        this.cardSuit = cardSuit;
    }

    private int cardNum;
    private CardSuit cardSuit;

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(CardSuit cardSuit) {
        this.cardSuit = cardSuit;
    }
}
