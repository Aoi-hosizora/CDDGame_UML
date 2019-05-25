package com.oosad.cddgame.UI.GamingAct.model.system;

import com.oosad.cddgame.Data.Player;
import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.util.CardUtil;
import com.oosad.cddgame.UI.GamingAct.util.DistributeUtil;

public class GameSystem {

    private GameSystem() {}
    private static GameSystem instance;

    public static GameSystem getInstance() {
        if (instance == null) {
            instance = new GameSystem();
            instance.cardMgr = CardMgr.getInstance();
        }
        return instance;
    }

    private CardMgr cardMgr;

    /**
     * 判断是否能如此出牌
     * 不能: ret false, fin
     * 能: ret true, 将谁出的牌的出牌信息记录进 CardMgr
     *     包括更新拥有的牌，记录已经出过的牌
     * @param showcards 为 null 表示跳过
     * @param player
     * @return
     */
    public boolean canShowCard(Card[] showcards, Player player) {
        return cardMgr.checkShowCardThroughRule(showcards, player);
    }

    /**
     * 启动游戏，分发四个玩家的牌
     */
    public Card[] DistributeCardForStartGame() {
        // 52/4 = 13
        int CardCntForEveryPlayer = (int) Math.floor((float) CardUtil.AllCardCnt / CardUtil.PlayerCnt);
        DistributeUtil.ClearDistributeCards();

        Card[] userCard = DistributeUtil.DistributeCards(CardCntForEveryPlayer);
        Card[] robot_1_Card = DistributeUtil.DistributeCards(CardCntForEveryPlayer);
        Card[] robot_2_Card = DistributeUtil.DistributeCards(CardCntForEveryPlayer);
        Card[] robot_3_Card = DistributeUtil.DistributeCards(CardCntForEveryPlayer);

        // Mgr.setUserCard(userCard);
        // Mgr.setRobotCard(robot_1_Card, robot_2_Card, robot_3_Card);

        return userCard;
    }
}
