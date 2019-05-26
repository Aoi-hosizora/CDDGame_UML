package com.oosad.cddgame.UI.GamingAct.model.system;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Player;
import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.model.CardSuit;

import java.util.Arrays;
import java.util.Random;

public class CardMgr {

    private Card[] robot_1_CardSet=new Card[Constant.PlayerCardCnt];
    private Card[] robot_2_CardSet=new Card[Constant.PlayerCardCnt];
    private Card[] robot_3_CardSet=new Card[Constant.PlayerCardCnt];
    private Card[] user_CardSet=new Card[Constant.PlayerCardCnt];

    private static volatile CardMgr cardMgr=null;
    private void CardMgr(){

    }
    public static CardMgr getInstance(){
        if(cardMgr==null) {
            synchronized (CardMgr.class){
                if(cardMgr==null){
                    cardMgr=new CardMgr();
                }
            }
        }
        return cardMgr;
    }


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
        DistributeCardsToOne(robot_1_CardSet,cardnum,0);
        DistributeCardsToOne(robot_2_CardSet,cardnum,Constant.PlayerCardCnt);
        DistributeCardsToOne(robot_3_CardSet,cardnum,Constant.PlayerCardCnt * 2);
        DistributeCardsToOne(user_CardSet,cardnum,Constant.PlayerCardCnt * 3);

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

    /**
     * 获取玩家的牌
     * @return
     */
    public Card[] Get_User_Cards(){
        Arrays.sort(user_CardSet);
        return user_CardSet;
    }

    /**
     * 获取机器人1的牌
     * @return
     */
    public Card[] Get_Robot_1_Cards(){
        Arrays.sort(robot_1_CardSet);
        return robot_1_CardSet;
    }

    /**
     * 获取机器人2的牌
     * @return
     */
    public Card[] Get_Robot_2_Cards(){
        Arrays.sort(robot_2_CardSet);
        return  robot_2_CardSet;
    }

    /**
     * 获取机器3的牌
     * @return
     */
    public Card[] Get_Robot_3_Cards(){
        Arrays.sort(robot_3_CardSet);
        return robot_3_CardSet;
    }

    /**
     * 上次玩家出的牌记录
     */
    private Card[] LastShownCard;
    /**
     * 玩家选择跳过的次数，超过 PlayerCnt-1 就清空 LastShownCard
     */
    private short HasJumpedCnt;

    public Card[] getLastShownCard() {
        return LastShownCard;
    }

    public void setLastShownCard(Card[] lastShownCard) {
        LastShownCard = lastShownCard;
    }

    /**
     * 调用规则模块
     * @param showcards 为 null 表示跳过
     * @param player
     * @return
     */
    public boolean checkShowCardThroughRule(Card[] showcards, Player player) {
        return true;
    }
}
