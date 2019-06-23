package com.oosad.cddgame.Data;

public class Constant {

    // region 登陆用户相关

    /**
     * 密码允许的最小长度
     */
    public static final int PassWordMinLen = 5;
    /**
     * 密码允许的最大长度
     */
    public static final int PassWordMaxLen = 20;

    // endregion

    // region 与扑克牌游戏相关 AllCardCnt AllCardSuitCnt PlayerCnt PlayerCardCnt

    /**
     * 扑克牌总数
     */
    public static final int AllCardCnt = 52;

    /**
     * 扑克牌花色总数
     */
    public static final int AllCardSuitCnt = 4;

    /**
     * 玩家个数
     */
    public static final int PlayerCnt = 4;

    /**
     * 玩家每人的牌数
     * AllCardCnt / PlayerCnt = 13
     */
    public static final int PlayerCardCnt = (int) Math.floor( (double) AllCardCnt / PlayerCnt);

    // endregion 与扑克牌游戏相关

    // region 与时延信息相关

    /**
     * 一秒，倒计时用
     */
    public static final int TIME_ONESECOND = 1000;

    /**
     * 半分钟，倒计时用
     */
    public static final int TIME_HALFAMINUTE = 30 * TIME_ONESECOND;

    /**
     * 机器人出牌时 清除出牌记录 与 发出新的出牌 之间的时延
     */
    public static final int TIME_WaitByClearRobotShowCardLayout = 200;
    /**
     * 机器人出牌时 每个机器人之间的时延, TIME_WaitByClearRobotShowCardLayout + 500
     */
    public static final int TIME_WaitBeforeRobotShowCard = 700;

    // endregion 与时延信息相关

    // region 与玩家信息相关 USER ROBOT

    public static final int PLAYER_USER = 0;
    public static final int PLAYER_ROBOT_1 = 1;
    public static final int PLAYER_ROBOT_2 = 2;
    public static final int PLAYER_ROBOT_3 = 3;

    // endregion 与玩家信息相关

    // region 与出牌信息相关 NO_ERR ERR_NOT_RULE ERR_NOT_ROUND

    /**
     * canShowCardWithCheckTurn(): 正常出牌
     */
    public static final int NO_ERR = 1;
    /**
     * canShowCardWithCheckTurn(): 不符合规则
     */
    public static final int ERR_NOT_RULE = -1;
    /**
     * canShowCardWithCheckTurn(): 还没轮到
     */
    public static final int ERR_NOT_ROUND = -2;
    /**
     * canShowCardWithCheckTurn(): 有胜者
     */
    public static final int ERR_HAS_WINNER = -3;

    // endregion 与出牌信息相关

    // region Online 与布局相关

    public static final int Left_Player = 1;
    public static final int Up_Player = 2;
    public static final int Right_Player = 3;
    public static final int Down_Player = 0;

    public static final int CountDownMaxTime = 30;

    // endregion Online 与布局相关

}
