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

    public int getCardLogicValue(){
        if(cardNum==1||cardNum==2)
            return cardNum+10;
        else return cardNum-3;
    }

//    public int compareBasic(Card card){
//        if (this.getCardNum() != card.getCardNum())
//            return this.getCardNum() > card.getCardNum() ? 1 : -1;
//        return this.getCardSuit().compareTo(card.getCardSuit());
//    }
//    @Override
//    public int compareTo(Card card) {
//        if(this.getCardNum()<3)
//            if(card.getCardNum()<3)
//                return this.compareBasic(card);
//            else return 1;
//        else if(card.getCardNum()<3)
//            return -1;
//        else return this.compareBasic(card);
//    }
      public int compareTo(Card card) {
        if(this.getCardLogicValue()==card.getCardLogicValue())
            return this.getCardSuit().ordinal()>card.getCardSuit().ordinal()?1:-1;
        else return this.getCardLogicValue() > card.getCardLogicValue()?1:-1;
      }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card
            && this.getCardNum() == ((Card) obj).getCardNum()
            && this.getCardSuit() == ((Card) obj).getCardSuit();
    }
}
