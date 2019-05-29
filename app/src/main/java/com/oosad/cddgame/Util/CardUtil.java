package com.oosad.cddgame.Util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.oosad.cddgame.R;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.UI.Widget.CardLayout;

import java.util.ArrayList;

public class CardUtil {

    /**
     * 从 Card 获得 CardLayout
     * @param context
     * @param card
     * @param IsShowCardLayout
     *      true: 指定布局为 用于展示的 CardLayout
     *      false: 指定布局为 个人用有的 CardLayout
     * @return
     */
    public static CardLayout getCardLayoutFromCard(Context context, Card card, boolean IsShowCardLayout) {
        CardLayout cl = new CardLayout(context);
        cl.setCard(card);

        int height, width;
        if (IsShowCardLayout) { // ShowCardLayout
            height = (int) context.getResources().getDimension(R.dimen.ShowCard_Height);
            width = (int) context.getResources().getDimension(R.dimen.ShowCard_Width);
        }
        else { // MainCardLayout
            height = (int) context.getResources().getDimension(R.dimen.Card_Height);
            width = (int) context.getResources().getDimension(R.dimen.Card_Width);
        }

        cl.setLayoutSize(width, height, context.getResources().getDisplayMetrics());
        cl.setBackground(CardUtil.getCardBackGroundFromCard(context, card));
        cl.setCanCardSelected(!IsShowCardLayout); // 拥有的牌 -> 可选
        return cl;
    }

    /**
     * 从 CardLayout[] 获得 Card[]
     * @param cardLayouts
     * @return
     */
    public static Card[] getCardsFromCardLayouts(CardLayout[] cardLayouts) {
        Card[] cards = new Card[cardLayouts.length];
        int idx = 0;
        for (CardLayout cl : cardLayouts) {
            if (cl != null)
                cards[idx++] = cl.getCard();
        }
        return cards;
    }

    /**
     * 通过 CardLayout[] 获取选中的 CardSet
     * @param cardLayouts
     * @return
     */
    public static CardLayout[] getCardSetLayoutUp(CardLayout[] cardLayouts) {
        ArrayList<CardLayout> cards = new ArrayList<>();
        for (CardLayout v : cardLayouts) {
            if (v.getIsUp())
                cards.add(v);
        }
        return (CardLayout[]) cards.toArray(new CardLayout[cards.size()]);
    }

    /**
     * 业余处理显示图片
     * @param context
     * @param card 要显示的扑克牌
     * @return 该扑克牌对应的 Drawable
     */
    public static Drawable getCardBackGroundFromCard(Context context, Card card) {
        Resources r = context.getResources();
        Drawable ret = r.getDrawable(R.drawable.card_40);
        switch (card.getCardSuit()) {
            case Spade: // 黑桃 ♠
                switch (card.getCardNum()) {
                    case 1:
                        ret = r.getDrawable(R.drawable.card_40);
                    break;
                    case 2:
                        ret = r.getDrawable(R.drawable.card_41);
                    break;
                    case 3:
                        ret = r.getDrawable(R.drawable.card_42);
                    break;
                    case 4:
                        ret = r.getDrawable(R.drawable.card_43);
                    break;
                    case 5:
                        ret = r.getDrawable(R.drawable.card_44);
                    break;
                    case 6:
                        ret = r.getDrawable(R.drawable.card_45);
                    break;
                    case 7:
                        ret = r.getDrawable(R.drawable.card_46);
                    break;
                    case 8:
                        ret = r.getDrawable(R.drawable.card_47);
                    break;
                    case 9:
                        ret = r.getDrawable(R.drawable.card_48);
                    break;
                    case 10:
                        ret = r.getDrawable(R.drawable.card_49);
                    break;
                    case 11:
                        ret = r.getDrawable(R.drawable.card_50);
                    break;
                    case 12:
                        ret = r.getDrawable(R.drawable.card_51);
                    break;
                    case 13:
                        ret = r.getDrawable(R.drawable.card_52);
                    break;
                }
            break;
            case Heart: // 红桃 ♥
                switch (card.getCardNum()) {
                    case 1:
                        ret = r.getDrawable(R.drawable.card_27);
                        break;
                    case 2:
                        ret = r.getDrawable(R.drawable.card_28);
                        break;
                    case 3:
                        ret = r.getDrawable(R.drawable.card_29);
                        break;
                    case 4:
                        ret = r.getDrawable(R.drawable.card_30);
                        break;
                    case 5:
                        ret = r.getDrawable(R.drawable.card_31);
                        break;
                    case 6:
                        ret = r.getDrawable(R.drawable.card_32);
                        break;
                    case 7:
                        ret = r.getDrawable(R.drawable.card_33);
                        break;
                    case 8:
                        ret = r.getDrawable(R.drawable.card_34);
                        break;
                    case 9:
                        ret = r.getDrawable(R.drawable.card_35);
                        break;
                    case 10:
                        ret = r.getDrawable(R.drawable.card_36);
                        break;
                    case 11:
                        ret = r.getDrawable(R.drawable.card_37);
                        break;
                    case 12:
                        ret = r.getDrawable(R.drawable.card_38);
                        break;
                    case 13:
                        ret = r.getDrawable(R.drawable.card_39);
                        break;
                }
            break;
            case Diamond: // 方块 ♦
                switch (card.getCardNum()) {
                    case 1:
                        ret = r.getDrawable(R.drawable.card_1);
                        break;
                    case 2:
                        ret = r.getDrawable(R.drawable.card_2);
                        break;
                    case 3:
                        ret = r.getDrawable(R.drawable.card_3);
                        break;
                    case 4:
                        ret = r.getDrawable(R.drawable.card_4);
                        break;
                    case 5:
                        ret = r.getDrawable(R.drawable.card_5);
                        break;
                    case 6:
                        ret = r.getDrawable(R.drawable.card_6);
                        break;
                    case 7:
                        ret = r.getDrawable(R.drawable.card_7);
                        break;
                    case 8:
                        ret = r.getDrawable(R.drawable.card_8);
                        break;
                    case 9:
                        ret = r.getDrawable(R.drawable.card_9);
                        break;
                    case 10:
                        ret = r.getDrawable(R.drawable.card_10);
                        break;
                    case 11:
                        ret = r.getDrawable(R.drawable.card_11);
                        break;
                    case 12:
                        ret = r.getDrawable(R.drawable.card_12);
                        break;
                    case 13:
                        ret = r.getDrawable(R.drawable.card_13);
                        break;
                }
            break;
            case Club: // 梅花 ♣
                switch (card.getCardNum()) {
                    case 1:
                        ret = r.getDrawable(R.drawable.card_14);
                        break;
                    case 2:
                        ret = r.getDrawable(R.drawable.card_15);
                        break;
                    case 3:
                        ret = r.getDrawable(R.drawable.card_16);
                        break;
                    case 4:
                        ret = r.getDrawable(R.drawable.card_17);
                        break;
                    case 5:
                        ret = r.getDrawable(R.drawable.card_18);
                        break;
                    case 6:
                        ret = r.getDrawable(R.drawable.card_19);
                        break;
                    case 7:
                        ret = r.getDrawable(R.drawable.card_20);
                        break;
                    case 8:
                        ret = r.getDrawable(R.drawable.card_21);
                        break;
                    case 9:
                        ret = r.getDrawable(R.drawable.card_22);
                        break;
                    case 10:
                        ret = r.getDrawable(R.drawable.card_23);
                        break;
                    case 11:
                        ret = r.getDrawable(R.drawable.card_24);
                        break;
                    case 12:
                        ret = r.getDrawable(R.drawable.card_25);
                        break;
                    case 13:
                        ret = r.getDrawable(R.drawable.card_26);
                        break;
                }
            break;
        }
        return ret;
    }

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
