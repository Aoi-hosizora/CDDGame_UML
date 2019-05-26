package com.oosad.cddgame.UI.GamingAct.model.system;

import com.oosad.cddgame.Data.Player;
import com.oosad.cddgame.UI.GamingAct.model.Card;

public class GameSystem {

    private GameSystem() {}
    private static volatile GameSystem instance;

    public static GameSystem getInstance() {
        if (instance == null) // 判空操作 提高效率
            synchronized (GameSystem.class) { // 同步代码块入口 线程安全
                if (instance == null) { // 判空操作 单例唯一
                    instance = new GameSystem();
                    instance.cardMgr = CardMgr.getInstance();
                }
            }
        return instance;
    }

    private CardMgr cardMgr;

    /**
     * 判断 当前是否轮到出牌 并且是否能如此出牌
     * 不能: ret false, fin
     * 能: ret true, 将谁出的牌的出牌信息记录进 CardMgr
     *     包括更新拥有的牌，记录已经出过的牌
     * @param showcards 为 null 表示跳过
     * @param player
     * @return
     */
    public boolean canShowCardWithCheckTurn(Card[] showcards, Player player) {
        // 没有轮到
        if (!GameRound.getInstane().checkIsRound(player))
            return false;
        // 轮到了不符合规则
        if (cardMgr.checkShowCardThroughRule(showcards, player))
            return false;
        // 符合规则并进行出牌处理
        GameRound.getInstane().setNextPlayer();

        return true;
    }

    /**
     * 启动游戏，分发四个玩家的牌
     */
    public Card[] DistributeCardForStartGame() {
        CardMgr.getInstance().DistributeCards();
        return CardMgr.getInstance().Get_User_Cards();
    }
}
