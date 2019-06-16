package com.oosad.cddgame.Data.Boundary;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Rules.NormalRuleCheckAdapter;
import com.oosad.cddgame.Data.Rules.RuleCheckAdapter;
import com.oosad.cddgame.Data.Entity.Player.Player;
import com.oosad.cddgame.Data.Entity.Player.Robot;
import com.oosad.cddgame.Data.Entity.Player.User;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Controller.CardMgr;
import com.oosad.cddgame.Data.Controller.GameRound;

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

                    // Adapter
                    instance.ruleCheck = new NormalRuleCheckAdapter();
                }
            }
        return instance;
    }

    /**
     * 初始化游戏数据，发牌前用
     */
    public void initGame() {
        cardMgr.initInstance();
        gameRound.initInstance();
        // RobotMgr 有事件注册，不能初始化
        Winner = null;
    }

    private CardMgr cardMgr;
    private GameRound gameRound;

    private Robot[] RobotMgr;
    private User GameUser;
    private Player Winner = null;

    private boolean isSingle;

    private RuleCheckAdapter ruleCheck;

    /**
     * 设置是否为单机游戏
     * 在 Handle_SetupBundle(GameAct) 会访问
     * @param isSingle
     */
    public void setIsSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }

    /**
     * 返回是否为单机游戏
     * @return
     */
    public boolean getIsSingle() {
        return isSingle;
    }

    /**
     * 设置当前玩家
     * @param user
     */
    public void setUser(User user) {
        this.GameUser = user;
    }

    /**
     * 获得当前玩家
     * @return
     */
    public User getCurrUser() {
        return GameUser;
    }

    /**
     * 通过 PLAYER_ROBOT_x 获取机器人
     * @param idx
     * @return
     */
    public Robot getRobot(int idx) {
        return RobotMgr[idx-1];
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * SG 通过本身指针 获取玩家的牌数
     * OG 访问服务器请求
     * @param player
     * @return
     */
    public int getPlayerCardsCnt(Player player) {
        return CardMgr.getInstance().getPlayerCards(player).length;
    }

    /**
     *
     * 判断 当前是否轮到出牌 并且是否能如此出牌 待改
     * 不能: ret false, fin
     * 能: ret true, 将谁出的牌的出牌信息记录进 CardMgr
     *     包括更新拥有的牌，记录已经出过的牌
     *
     * OG
     *
     * 判断存在胜者           服务器
     * 判断轮到出牌           服务器
     * 判断符合规则           本机
     * 执行跳过出牌           本机 & 服务器
     * 执行指定出牌           本机 & 服务器
     *
     * @param showcards 为 null 表示跳过
     * @param player
     * @return
     */
    public int canShowCardWithCheckTurn(Card[] showcards, Player player) {
        // 没有胜者
        if (Winner == null) {
            // 没有轮到
            if (!gameRound.checkIsRound(player))
                return Constant.ERR_NOT_ROUND;

            // 轮到了不符合规则
            if (!ruleCheck.checkShowCardRule(cardMgr.getLastShownCard(), showcards))
                return Constant.ERR_NOT_RULE;

            // 跳过出牌
            if (showcards == null) {
                gameRound.setNextPlayer();
                return Constant.NO_ERR;
            }

            // 符合规则并进行出牌处理，待处理
            cardMgr.removePlayerCardShown(showcards, player);
            cardMgr.setLastShownCard(showcards);

            gameRound.setNextPlayer();

            // 判断是否胜出
            if (getPlayerCardsCnt(player) == 0)
                Winner = player;

            return Constant.NO_ERR;
        }

        // 有胜者
        return Constant.ERR_HAS_WINNER;
    }

    /**
     * SG 启动游戏，分发四个玩家的牌，并初始化 gameRound 的玩家记录，同时会启动发牌
     *
     * OG TODO: 访问服务器请求分牌，并获取本玩家的牌返回
     */
    public Card[] DistributeCardForStartGame() {

        CardMgr.getInstance().DistributeCards();

        Player[] players = new Player[] {
                getCurrUser(),
                RobotMgr[0], RobotMgr[1], RobotMgr[2]
        };

        gameRound.setInitPlayer(players, cardMgr.getInitialPlayerIdx());
        return CardMgr.getInstance().getPlayerCards(getCurrUser());

        // if (isSingle) {
        //
        // }
        // else {
        //
        // }
    }
}
