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
}
