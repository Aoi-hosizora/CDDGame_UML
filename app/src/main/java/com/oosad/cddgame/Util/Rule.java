package com.oosad.cddgame.Util;

import com.oosad.cddgame.Data.Entity.Card;

public class Rule {


    /*判断卡组类型：单张，对子，三条，四张，
     *顺子(花色不同的5张连牌)，同花(花色相同的5张不连牌)，
     * 葫芦(3带2)，金刚(4带1)，同花顺(花色相同的5张连牌)
     *分别用数字1~9表示牌型，0表示无效牌型
     * 默认牌组从小到大排序
     */
    static final int errorType =0;
    static final int DanZhang =1;
    static final int DuiZi =2;
    static final int SanTiao =3;
    static final int SiZhang =4;
    static final int ShunZi=5;
    static final int TongHua =6;
    static final int Hulu =7;
    static final int Jingang=8;
    static final int TongHuaShun =9;

    //顺子
    public static boolean isShunZi(Card[] cards){
        //顺子不能有2所以不存在最大数小于7的以及最小数大于J的
        if(cards[0].getCardNum()>=11||cards[4].getCardNum()<7)
            return false;
        if(cards[0].getCardNum()==1){
            //顺子中带A时牌型一定是A 10 J Q K，不能用普通判定
            return (cards[1].getCardNum()==10&&cards[2].getCardNum()==11
                    &&cards[3].getCardNum()==12&&cards[4].getCardNum()==13);
        }
        //普通顺子判定
        for(int i=1;i<5;i++)
            if(cards[i].getCardNum()!=cards[i-1].getCardNum()+1)
                return false;
        return true;
    }

    //同花
    public static boolean isTonghua(Card[] cards){
        for(int i=1;i<5;i++)
            if (cards[i].getCardSuit() != cards[i - 1].getCardSuit())
                return false;
        return true;
    }

    //同花顺
    public static boolean isTonghuashun(Card[] cards){
        return (isTonghua(cards)&&isShunZi(cards));
    }

    //葫芦
    public static boolean isHulu(Card[] cards){
        int count=1;
        for(int i=1;i<5;i++){
            if(cards[i].getCardNum()==cards[i-1].getCardNum())
                count++;
            else if(count==3&&cards[i].getCardNum()==cards[i+1].getCardNum())
                //第二个等值判断防止 3 3 3 4 5也被判断成葫芦
                return true;
            else if(count==2){
                count=1;
            }
            else return false;  //存在单张，无法构成葫芦
        }
        return (count==3);
    }

    //金刚
    public static boolean isJingang(Card[] cards){
        int count=1;
        for(int i=1;i<5;i++){
            if(cards[i].getCardNum()==cards[i-1].getCardNum())
                count++;
            else if(count==4)
                return true;    //找到相同的四张牌
            else count=1;
        }
        return (count==4);
    }

    //对子，三条，四张
    public static boolean isDuizi(Card[] cards){
        return cards[0].getCardNum()==cards[1].getCardNum();
    }
    public static boolean isSantiao(Card[] cards){
        return (cards[0].getCardNum()==cards[1].getCardNum()
                &&cards[1].getCardNum()==cards[2].getCardNum());
    }
    public static boolean isSizhang(Card[] cards){
        return (cards[0].getCardNum()==cards[1].getCardNum()
                &&cards[1].getCardNum()==cards[2].getCardNum()
                &&cards[2].getCardNum()==cards[3].getCardNum());
    }

    //判断牌型
    public static int typeOfCards(Card[] cards){
        if(cards.length==1) return DanZhang;
        if(cards.length==2&&isDuizi(cards)) return DuiZi;
        if(cards.length==3&&isSantiao(cards)) return SanTiao;
        if(cards.length==4&&isSizhang(cards)) return SiZhang;
        if(cards.length==5){
            if(isTonghuashun(cards)) return TongHuaShun;
            if(isJingang(cards)) return Jingang;
            if(isHulu(cards)) return Hulu;
            if(isTonghua(cards)) return TongHua;
            if(isShunZi(cards)) return ShunZi;
        }
        return errorType;
    }

    //判断能否出牌,返回true代表能打上家的牌
    public static boolean judgement(Card[] upCards,Card[] curCards){
        if(typeOfCards(upCards)==0||typeOfCards(curCards)==0)
            return false;
        if(upCards.length!=curCards.length) return false;
        if(upCards.length<5){
            if(upCards.length==2)
                return curCards[1].compareTo(upCards[1]) == 1 ;
            else
                return curCards[0].compareTo(upCards[0]) == 1 ;
        }
        else{
            if(typeOfCards(upCards)==typeOfCards(curCards))
                //顺子，同花，同花顺因为A在0号位，需要单独判断
                if(typeOfCards(upCards)==5||typeOfCards(upCards)==6||typeOfCards(upCards)==9){
                    if(upCards[0].getCardNum()!=1)
                        if(curCards[0].getCardNum()!=1)
                            return curCards[4].compareTo(upCards[4])==1;
                        else
                            return true;
                    else
                    if(curCards[0].getCardNum()==1)
                        return curCards[0].compareTo(upCards[0])==1;
                    else
                        return false;
                }
                else    //葫芦和金刚比较只需要比较2号位
                    return curCards[2].compareTo(upCards[2])==1;
            else //卡牌类型不一样直接比较类型
                return typeOfCards(curCards)>typeOfCards(upCards);
        }
    }
}
