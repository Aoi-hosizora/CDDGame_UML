package com.oosad.cddgame.Util;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Entity.CardSuit;

import java.lang.reflect.Array;
import java.util.Arrays;

public class RobotShowCardUtil {
    public static Card[] searchShowCard(Card[] upCards,Card[] hasCards){
        cardAnalysis cA=new cardAnalysis(hasCards);
//        if (cA.getCardRecord()[0][0]>0)
//                return new Card[]{hasCards[0]};
        if(upCards==null)
            return new Card[]{hasCards[0]};
        switch (RuleUtil.typeOfCards(upCards)){
            case 1: {
                return searchDanzhang(upCards, cA);
            }
            case 2:{
                return searchDuizi(upCards,cA);
            }
            case 5:{
                return searchShunzi(upCards,cA);
            }
            case 6:{
                return searchTonghua(upCards,cA);
            }
            case 7:{
                return searchHulu(upCards,cA);
            }
            case 8:{
                return searchJingang(upCards,cA);
            }
            case 9:{
                return searchTonghuaShun(upCards,cA);
            }
            default:{
                return new Card[]{};
            }
        }
    }

    public static Card[] searchShunzi(Card[] upCards,cardAnalysis cA){
        //记录upCard最大权值
        int upCardValue=upCards[0].getCardNum() == 1 ?
                upCards[0].getCardLogicValue() : upCards[4].getCardLogicValue();
        int upCardSuit=upCards[0].getCardNum() == 1 ?
                upCards[0].getCardSuit().ordinal() : upCards[4].getCardSuit().ordinal();

        //记录连续的牌数
        int linkcnt=0;
        for(int i=0;i<13-1;i++){
            //连续中断，linkcnt置零
            if(cA.getCardRecord()[4][i]==0){
                linkcnt=0;
                continue;
            }
            linkcnt++;
            //连牌数大于5且牌数值等于upCardValue
            if(i==upCardValue && linkcnt>=5){
                for (int j=0;j<4;j++) {
                    if (j > upCardSuit && cA.getCardRecord()[j][i]>0) {
                        //注意到对K有(i+3)%13==0
                        return new Card[]{
                                new Card((i - 4 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 4)]),
                                new Card((i - 3 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 3)]),
                                new Card((i - 2 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 2)]),
                                new Card((i - 1 + 3) % 13 == 0 ? 13 : (i - 1 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 1)]),
                                new Card((i + 3) % 13 == 0 ? 13 : (i + 3) % 13, CardSuit.values()[j])
                        };
                    }
                }
            }
            //连牌数大于5且牌数值大于upCardValue
            if(linkcnt>=5 && i>upCardValue){
                //注意到对K有(i+3)%13==0
                return new Card[]{
                        new Card((i - 4 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 4)]),
                        new Card((i - 3 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 3)]),
                        new Card((i - 2 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 2)]),
                        new Card((i - 1 + 3) % 13 == 0 ? 13 : (i - 1 + 3) % 13, CardSuit.values()[cA.findMinSuit(i - 1)]),
                        new Card((i + 3) % 13 == 0 ? 13 : (i + 3) % 13, CardSuit.values()[cA.findMinSuit(i)])
                };
            }

        }

        return new Card[]{};
    }
    public static Card[] searchTonghua(Card[] upCards,cardAnalysis cA){
        //记录upCard最大权值
        int upCardSuit=upCards[0].getCardSuit().ordinal();
        int upCardValue=(upCards[0].getCardNum() == 1 ||upCards[0].getCardNum() == 2)?
                upCards[0].getCardLogicValue() : upCards[4].getCardLogicValue();
        if(upCards[1].getCardNum()==2)
            upCardValue=upCards[1].getCardLogicValue();

        //从方块开始寻找同花
        for(int i=0;i<4;i++){
            int sameColourcnt=0;
            Card[] showCard=new Card[5];
            for(int j=0;j<13;j++){
                if(cA.getCardRecord()[i][j]>0)
                    sameColourcnt++;

                if(cA.getCardRecord()[i][j]>0 && j==upCardValue && sameColourcnt>=5 && i>upCardSuit
                ||cA.getCardRecord()[i][j]>0 && sameColourcnt>5 && j>upCardValue){
                    showCard[4]=new Card((j+ 3) % 13 == 0 ? 13 : (j + 3) % 13,CardSuit.values()[i]);
                    for (int k=0,l=0;k<j&&l<4;k++){
                        if (cA.getCardRecord()[i][k]>0){
                            showCard[l]=new Card((k+3)%13==0?13:(k+3)%13,CardSuit.values()[i]);
                            l++;
                        }
                    }
                    return showCard;
                }
            }
        }
        return new Card[]{};
    }
    public static Card[] searchHulu(Card[] upCards,cardAnalysis cA){
        //记录upCard最大权值
        int upCardValue=upCards[2].getCardLogicValue();
        Card[] showCard=new Card[5];
        for (int i=0;i<13;i++){
            if(cA.getCardRecord()[4][i]==3 && i>upCardValue) {
                for(int j=0,k=0;j<4&&k<3;j++){
                    if(cA.getCardRecord()[j][i]>0){
                        showCard[k]=new Card((i+3)%13==0?13:(i+3)%13,CardSuit.values()[j]);
                        k++;
                    }
                }
                for(int a=0;a<13;a++){
                    if(cA.getCardRecord()[4][a]==2){
                        for(int b=0,c=3;b<4&&c<5;b++){
                            if(cA.getCardRecord()[b][a]>0){
                                showCard[c]=new Card((a+3)%13==0?13:(a+3)%13,CardSuit.values()[b]);
                                c++;
                            }
                        }
                        return showCard;
                    }
                }
            }
        }
        return new Card[]{};
    }
    public static Card[] searchJingang(Card[] upCards,cardAnalysis cA){
        //记录upCard最大权值
        int upCardValue=upCards[2].getCardLogicValue();
        Card[] showCard=new Card[5];
        for(int i=0;i<13;i++){
            if(cA.getCardRecord()[4][i]==4 && i>upCardValue){
                for(int j=0;j<4;j++){
                    showCard[j]=new Card((i+3)%13==0?13:(i+3)%13,CardSuit.values()[j]);
                }
                for(int j=0;j<13;j++){
                    if(cA.getCardRecord()[4][j]>0) {
                        showCard[4] = new Card((j + 3) % 13 == 0 ? 13 : (j + 3) % 13, CardSuit.values()[cA.findMinSuit(j)]);
                        return showCard;
                    }
                }
                break;
            }
        }

        return new Card[]{};
    }
    //使用鸵鸟算法解决同花顺
    public static Card[] searchTonghuaShun(Card[] upCards,cardAnalysis cA){
        return new Card[]{};
    }
    public static Card[] searchDanzhang(Card[] upCards,cardAnalysis cA){
        for(int i=0;i<13;i++){
            if (cA.getCardRecord()[4][i]==1&&i==upCards[0].getCardLogicValue()){
                if(cA.findMaxSuit(i)>upCards[0].getCardSuit().ordinal())
                    return new Card[]{new Card((i + 3) % 13 == 0 ? 13 : (i + 3) % 13,CardSuit.values()[cA.findMaxSuit(i)])};
            }
            if(cA.getCardRecord()[4][i]==1 && i>upCards[0].getCardLogicValue()){
                return new Card[]{new Card((i + 3) % 13 == 0 ? 13 : (i + 3) % 13,CardSuit.values()[cA.findMaxSuit(i)])
                };
            }
        }
        return new Card[]{};
    }
    public static Card[] searchDuizi(Card[] upCards,cardAnalysis cA){
        Card[] showCard=new Card[2];
        for (int i=0;i<13;i++){
            if(cA.getCardRecord()[4][i]==2 && i>upCards[0].getCardLogicValue()){
                for(int j=0,k=0;j<4&&k<2;j++)
                    if(cA.getCardRecord()[j][i]>0){
                        showCard[k]=new Card((i + 3) % 13 == 0 ? 13 : (i + 3) % 13,CardSuit.values()[j]);
                        k++;
                    }
                return showCard;
            }
        }
        return new Card[]{};
    }

}

class cardAnalysis{
    //记录牌数
    private int totalcnt;

    //记录卡牌，[0]~[3]代表花色方块~黑桃，
    // [0]~[12]顺序为3，4，5...K,A,2
    //[4][0]~[4][12]记录对应牌值牌数
    private int[][] cardRecord=new int[5][13];

    //该数组记录牌型数量，[0]~[3]分别代表单张，对子，三条，四张
    private int[] typeCnt = new int[4];

    //记录每一种牌型拥有的牌的面值,并且按照逻辑值排序比如有3个3，3个5，
    // 那么typeRecord[2][0]==[2][1]==[2][2]==3，typeRecord[2][4]==[2][5]==[2][6]==5
//    private int[][] typeRecord = new int[4][13];

    public int getTotalcnt(){return totalcnt;}
    public int[][] getCardRecord(){return cardRecord;}
//    public int[][] getTypeRecord(){return typeRecord;}
    public int[] getTypeCnt(){return typeCnt;}

    public cardAnalysis() {this.clear();}
    public cardAnalysis(Card[] cards) {
        this.clear();
        totalcnt = cards.length;

        //将手牌记录到cardRecord中
        for (int i = 0; i < totalcnt; i++) {
            cardRecord[cards[i].getCardSuit().ordinal()][cards[i].getCardLogicValue()]++;
            cardRecord[4][cards[i].getCardLogicValue()]++;
        }

//        //分析牌型
//        for (int i = 0; i < totalcnt; ) {
//            int samecnt = 1;
//
//            for (int j = i + 1; j < totalcnt; j++) {
//                if (cards[i] == cards[j])
//                    samecnt++;
//                else
//                    break;
//            }
//
//            switch (samecnt) {
//                case 1: {
//                    typeRecord[0][typeCnt[0] * 1] = cards[i].getCardLogicValue();
//                    typeCnt[0]++;
//                    break;
//                }
//                case 2: {
//                    typeRecord[1][typeCnt[1] * 2] = cards[i].getCardLogicValue();
//                    typeRecord[1][typeCnt[1] * 2 + 1] = cards[i + 1].getCardLogicValue();
//                    typeCnt[1]++;
//                    break;
//                }
//                case 3: {
//                    typeRecord[2][typeCnt[2] * 3] = cards[i].getCardLogicValue();
//                    typeRecord[2][typeCnt[2] * 3+1] = cards[i+1].getCardLogicValue();
//                    typeRecord[2][typeCnt[2] * 3+2] = cards[i+2].getCardLogicValue();
//                    typeCnt[2]++;
//                    break;
//                }
//                case 4:{
//                    typeRecord[3][typeCnt[3]*4]=cards[i].getCardLogicValue();
//                    typeRecord[3][typeCnt[3]*4+1]=cards[i+1].getCardLogicValue();
//                    typeRecord[3][typeCnt[3]*4+2]=cards[i+2].getCardLogicValue();
//                    typeRecord[3][typeCnt[3]*4+3]=cards[i+3].getCardLogicValue();
//                    typeCnt[3]++;
//                    break;
//                }
//                default:
//                    break;
//            }
//            i+=samecnt;
//        }
    }

    void clear(){
        totalcnt=0;
        Arrays.fill(typeCnt,0);

        for(int i=0;i<5;i++){
            for (int j=0;j<13;j++)
                cardRecord[i][j]=0;
        }
//        Arrays.fill(typeRecord,temp);
    }

    //对给定牌面值找出最大花色
    public int findMaxSuit(int i){
        for(int j=3;j>-1;j--) {
            if (cardRecord[j][i] > 0)
                return j;
        }
        return -1;
    }

    //对给定牌面值找出最小花色
    public int findMinSuit(int i){
        for(int j=0;j<4;j++){
            if(cardRecord[j][i]>0)
                return j;
        }
        return -1;
    }

}