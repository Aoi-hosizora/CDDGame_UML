package com.oosad.cddgame.UI.GamingAct.model.system;

import android.util.Log;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Player;
import com.oosad.cddgame.Data.Robot;
import com.oosad.cddgame.Data.Setting;
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
                    instance.gameRound = GameRound.getInstane();
                    instance.RobotMgr = new Robot[] {
                            new Robot(Constant.PLAYER_ROBOT_1),
                            new Robot(Constant.PLAYER_ROBOT_2),
                            new Robot(Constant.PLAYER_ROBOT_3)
                    };

                }
            }
        return instance;
    }

    private CardMgr cardMgr;
    private GameRound gameRound;
    private Robot[] RobotMgr;

    public Robot getRobot(int idx) {
        return RobotMgr[idx-1];
    }

    /**
     * 判断 当前是否轮到出牌 并且是否能如此出牌
     * 不能: ret false, fin
     * 能: ret true, 将谁出的牌的出牌信息记录进 CardMgr
     *     包括更新拥有的牌，记录已经出过的牌
     * @param showcards 为 null 表示跳过
     * @param player
     * @return
     */
    public int canShowCardWithCheckTurn(Card[] showcards, Player player) {

        // 没有轮到
        if (!gameRound.checkIsRound(player))
            return Constant.ERR_NOTROUND;

        // 轮到了不符合规则
        if (!cardMgr.checkShowCardThroughRule(showcards))
            return Constant.ERR_NOTRULE;

        // 符合规则并进行出牌处理

        gameRound.setNextPlayer();

        return Constant.NO_ERR;
    }

    /**
     * 启动游戏，分发四个玩家的牌
     */
    public Card[] DistributeCardForStartGame() {

        CardMgr.getInstance().DistributeCards();

        Player[] players = new Player[] {Setting.getInstance().getCurrUser(), RobotMgr[0], RobotMgr[1], RobotMgr[2]};
        gameRound.setInitPlayer(players, cardMgr.getInitialPlayerIdx());
        return CardMgr.getInstance().Get_User_Cards();
    }
}
