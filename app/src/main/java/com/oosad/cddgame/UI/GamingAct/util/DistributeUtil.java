package com.oosad.cddgame.UI.GamingAct.util;

import android.util.Log;

import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.model.CardSuit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class DistributeUtil {

    /**
     * 保存已经发放的扑克牌，防止重复发牌 getRandomCard() 用
     */
    private static ArrayList<HashSet<Integer>> HasDistributedCard = new ArrayList<>();

    /**
     * 初始化 ArrayList
     */
    static {
        for (int i = 0; i < CardUtil.AllCardSuitCnt; i++) {
            HasDistributedCard.add(new HashSet<Integer>());
        }
    }

    /**
     * 清除已经分发的牌的记录，
     */
    public static void ClearDistributeCards() {
        for (int i = 0; i < CardUtil.AllCardSuitCnt; i++) {
            HasDistributedCard.get(i).clear();
        }
    }

    /**
     * 获取不重复的 Card，执行前先执行 ClearDistributeCards()
     * @return
     */
    private static Card getRandomCard() {
        int cardsuit = getRandom(0, 3);
        int cardnum = getRandom(1, 13);

        while (HasDistributedCard.get(cardsuit).contains(cardnum)) {
            // Log.e("", "getRandomCard: " + cardnum);
            cardsuit = getRandom(0, 3);
            cardnum = getRandom(1, 13);
        }

        HasDistributedCard.get(cardsuit).add(cardnum);
        return new Card(cardnum, CardSuit.values()[cardsuit]);
    }

    /**
     * 发牌，执行前要先执行 ClearDistributeCards()
     * @return
     */
    public static Card[] DistributeCards(int CardCnt) {
        Card[] cards = new Card[CardCnt];

        for (int i = 0; i < CardCnt; i++)
            cards[i] = getRandomCard();

        Arrays.sort(cards);
        return cards;
    }

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
}
