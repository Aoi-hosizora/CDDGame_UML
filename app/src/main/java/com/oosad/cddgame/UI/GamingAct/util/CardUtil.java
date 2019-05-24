package com.oosad.cddgame.UI.GamingAct.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.model.CardSuit;
import com.oosad.cddgame.UI.GamingAct.view.CardLayout;

import java.util.Random;

public class CardUtil {

    /**
     * 获得随机数，闭区间
     * @param from
     * @param to
     * @return
     */
    private static int getRandom(int from, int to) {
        Random random = new Random();
        return random.nextInt(to - from + 1) + from;
    }

    /**
     * 发牌，目前为随机算法，不考虑重复
     * @return
     */
    public static Card[] DistributeCards() {
        int CardCnt = 13;
        Random random = new Random();
        Card[] cards = new Card[CardCnt];
        for (int i = 0; i < CardCnt; i++) {
            cards[i] = new Card(getRandom(1, 13), CardSuit.values()[getRandom(0, 3)]);
        }
        return cards;
    }

    /**
     * 通过 CardLayout[] 获取 CardSet
     * @param cardLayouts
     * @return
     */
    public static Card[] getCardSet(CardLayout[] cardLayouts) {
        Card[] cards = new Card[cardLayouts.length];
        int idx = 0;
        for (CardLayout v : cardLayouts)
            cards[idx++] = v.getCard();
        return cards;
    }

    /**
     * 通过 CardLayout[] 获取选中的 CardSet
     * @param cardLayouts
     * @return
     */
    public static Card[] getCardSetUp(CardLayout[] cardLayouts) {
        Card[] cards = new Card[cardLayouts.length];
        int idx = 0;
        for (CardLayout v : cardLayouts) {
            if (v.getIsUp())
                cards[idx++] = v.getCard();
        }
        return cards;
    }

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
}
