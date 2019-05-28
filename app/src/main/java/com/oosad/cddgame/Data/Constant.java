package com.oosad.cddgame.Data;

public class Constant {

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


    public static final int PLAYER_USER = 0;
    public static final int PLAYER_ROBOT_1 = 1;
    public static final int PLAYER_ROBOT_2 = 2;
    public static final int PLAYER_ROBOT_3 = 3;

    /**
     * canShowCardWithCheckTurn(): 正常出牌
     */
    public static final int NO_ERR = 1;
    /**
     * canShowCardWithCheckTurn(): 不符合规则
     */
    public static final int ERR_NOTRULE = -1;
    /**
     * canShowCardWithCheckTurn(): 还没轮到
     */
    public static final int ERR_NOTROUND = -2;

    /**
     * 机器人出牌时 清除出牌记录 与 发出新的出牌 之间的时延
     */
    public static final int TIME_WaitByClearRobotShowCardLayout = 200;
    /**
     * 机器人出牌时 每个机器人之间的时延, TIME_WaitByClearRobotShowCardLayout + 500
     */
    public static final int TIME_WaitBeforeRobotShowCard = 800;
}
