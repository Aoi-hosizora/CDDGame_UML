package com.oosad.cddgame.Data.Controller;

import android.util.Log;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Player.Player;
import com.oosad.cddgame.Data.Entity.Player.Robot;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Entity.CardSuit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * CardMgr Controller:
 *
 * +DistributeCards / +getInitialPlayerIdx
 *
 * +getPlayerCards <<<<<
 * +removePlayerCardShown <<<<<
 *
 * +getLastShownCard / +setLastShownCard
 */
public class CardMgr {

    private static volatile CardMgr cardMgr=null;

    private void CardMgr() { }

    public static CardMgr getInstance() {
        if(cardMgr==null) {
            synchronized (CardMgr.class){
                if(cardMgr==null){
                    cardMgr=new CardMgr();
                    cardMgr.initInstance();
                }
            }
        }
        return cardMgr;
    }

    /**
     * 初始化实例
     */
    public void initInstance() {
        Players_CardSets = new ArrayList<>();
        for (int i = 0; i < Constant.PlayerCnt; i++)
            cardMgr.Players_CardSets.add(new Card[Constant.PlayerCardCnt]);
        initPlayerIdx = Constant.PLAYER_USER;
        LastShownCard = null;
        HasJumpedCnt = 0;
    }

    /**
     * 0: User
     * 1 - 3: Robot
     */
    private ArrayList<Card[]> Players_CardSets = new ArrayList<>();

    /**
     * 发牌，目前为随机算法
     * @return
     */
    public void DistributeCards() {
        int cardnum[]=new int[Constant.AllCardCnt];
        for(int i=0;i<52;i++){
            cardnum[i]=i;
        }
        shuffle(cardnum);
        for (int i = 0; i < Constant.PlayerCnt; i++) {

            Card[] cards = new Card[Constant.PlayerCardCnt];

            DistributeCardsToOne(cards,cardnum,Constant.PlayerCardCnt * i);
            Arrays.sort(cards);

            // 判断是否为初始玩家
            for (Card card : cards)
                if (card.getCardNum() == 3 && card.getCardSuit() == CardSuit.Diamond)
                    initPlayerIdx = i;

            Players_CardSets.set(i, cards);
        }
    }

    /**
     * 52张牌中分13张牌给其中一个玩家
     * @param cards
     * @param arr
     * @param i
     */
    private void DistributeCardsToOne(Card[] cards,int []arr,int i){
        int j=i;
        for(int k=0;k<cards.length;k++){
            cards[k]=new Card((arr[j]/4)+1, CardSuit.values()[arr[j]%4]);
            j++;
        }
    }

    /**
     * 打乱数组的顺序（用来打乱卡片的顺序)
     * @param arr
     */
    private void shuffle(int []arr){
        Random random=new Random();
        for(int i=arr.length;i>0;i--){
            int randInt=random.nextInt(arr.length);
            swap(arr,randInt,i-1);
        }
    }
    /**
     * 交换数组中的两个数字
     * @param arr
     * @param i
     * @param j
     */
    private void swap(int[]arr,int i,int j){
        int k=arr[i];
        arr[i]=arr[j];
        arr[j]=k;
    }

    ////////////////////////////////////////

    /**
     * Diamond 3 初始玩家
     */
    private int initPlayerIdx;

    /**
     * 获取初始玩家
     * @return
     */
    public int getInitialPlayerIdx() {
        return initPlayerIdx;
    }

    /**
     * 通过本身指针 获取玩家的牌
     * @param player
     * @return
     */
    public Card[] getPlayerCards(Player player) {
        if (player.IsRobot()) {
            return Players_CardSets.get(((Robot) player).getId());
        }
        return Players_CardSets.get(0);
    }

    /**
     * 出牌后 对数据的更新处理
     * @param shownCards
     * @param player
     */
    public void removePlayerCardShown(Card[] shownCards, Player player) {
        int id = 0;
        if (player.IsRobot())
            id = ((Robot) player).getId();

        ArrayList<Card> cardList = new ArrayList<>(Arrays.asList(Players_CardSets.get(id)));
        cardList.removeAll(Arrays.asList(shownCards));

        Players_CardSets.set(id, cardList.toArray(new Card[0]));
    }

    /**
     * 上次玩家出的牌记录
     */
    private Card[] LastShownCard;

    /**
     * 玩家选择跳过的次数，超过 PlayerCnt-1 就清空 LastShownCard 待改
     */
    private short HasJumpedCnt = 0;

    /**
     * 获取上个玩家出的牌
     * @return
     */
    public Card[] getLastShownCard() {
        return LastShownCard;
    }

    /**
     * 设置上个玩家出的牌，并计数清空
     * @param lastShownCard
     */
    public void setLastShownCard(Card[] lastShownCard) {

        if (lastShownCard == null || lastShownCard.length==0)
            HasJumpedCnt++;

        if (lastShownCard != null && lastShownCard.length!=0)
            HasJumpedCnt = 0;
        if (HasJumpedCnt == 3)
            LastShownCard = null;
        else
            LastShownCard = lastShownCard;

        Log.e("TAG", "setLastShownCard: " + HasJumpedCnt );
    }
}
