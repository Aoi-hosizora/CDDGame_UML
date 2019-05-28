package com.oosad.cddgame.Data.Entity;

public class Card implements Comparable<Card> {

    public Card(int cardNum, CardSuit cardSuit) {
        this.cardNum = cardNum;
        this.cardSuit = cardSuit;
    }

    /**
     * 1 - 13
     * 1: A
     * 11: J, 12: Q, 13: K
     */
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

    @Override
    public int compareTo(Card card) {
        if (this.getCardNum() != card.getCardNum())
            return this.getCardNum() > card.getCardNum() ? 1 : -1;
        return this.getCardSuit().compareTo(card.getCardSuit());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card
            && this.getCardNum() == ((Card) obj).getCardNum()
            && this.getCardSuit() == ((Card) obj).getCardSuit();
    }
}
