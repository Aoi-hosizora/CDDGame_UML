package com.oosad.cddgame.Data.Controller;

import android.os.Handler;
import android.util.Log;

import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Player.Player;

/**
 * GameRound Controller:
 *
 * +checkIsRound
 * +setInitPlayer / +setNextPlayer
 *
 * -getCurrPlayer
 * -LetRobotShowCard
 */
public class GameRound {

    private GameRound() {}
    private static volatile GameRound Instance;

    public static GameRound getInstance() {
        if (Instance == null) synchronized (GameRound.class) {
            if (Instance == null) {
                Instance = new GameRound();
                Instance.initInstance();
            }
        }
        return Instance;
    }

    /**
     * 初始化实例
     */
    public void initInstance() {
        currPlayerIdx = Constant.PLAYER_USER;
        PlayerOrder = new Player[Constant.PlayerCnt];
    }

    /**
     * 当前玩家在列表中的 idx
     */
    private int currPlayerIdx;

    /**
     * 玩家顺序列表: PLAYER_USER, PLAYER_ROBOT_1, PLAYER_ROBOT_2, PLAYER_ROBOT_3
     */
    private Player[] PlayerOrder;

    /**
     * 获取当前玩家
     * @return
     */
    private Player getCurrPlayer() {
        return PlayerOrder[currPlayerIdx];
    }

    /**
     * 判断当前是否是该玩家出牌
     * @param player
     * @return
     */
    public boolean checkIsRound(Player player) {
        return getCurrPlayer() == player;
    }

    /**
     * 设置轮次顺序，并且指定初始玩家
     * 如果为机器人则让机器人出牌
     *
     * @param players 考虑了顺序的玩家列表
     * @param CurrPlayerIndex
     */
    public void setInitPlayer(Player[] players, int CurrPlayerIndex) {
        this.PlayerOrder = players;
        this.currPlayerIdx = CurrPlayerIndex;

        Log.e("TAG", "currPlayerIdx: " + currPlayerIdx );

        // 机器人发完牌后会指定下一个出牌
        LetRobotShowCard();
    }

    /**
     * 设置为下一个玩家
     * 如果为机器人则让机器人出牌
     */
    public void setNextPlayer() {
        currPlayerIdx = (currPlayerIdx + 1) % Constant.PlayerCnt; // 4
        Log.e("TAG", "currPlayerIdx: " + currPlayerIdx );

        LetRobotShowCard();
    }

    /**
     * 机器人出牌，有时延
     * 伪外部事件 <<<<<<<<<<
     */
    private void LetRobotShowCard() {
        if (currPlayerIdx != Constant.PLAYER_USER) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    GameSystem.getInstance().getRobot(currPlayerIdx).showCard();
                }

            }, Constant.TIME_WaitBeforeRobotShowCard); // 800 = 200 + 500

        }
    }
}
