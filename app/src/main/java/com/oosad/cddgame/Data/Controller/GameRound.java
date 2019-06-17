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
     * SG 当前玩家在列表中的 idx
     * OG 给服务器的参数
     */
    private int currPlayerIdx;

    /**
     * SG 玩家顺序列表: PLAYER_USER, PLAYER_ROBOT_1, PLAYER_ROBOT_2, PLAYER_ROBOT_3
     * OG 不用，本地只存储本玩家
     */
    private Player[] PlayerOrder;

    /**
     * SG 获取当前玩家
     * OG TODO: 向服务器提交请求获得当前玩家
     * @return
     */
    private Player getCurrPlayer() {
        return PlayerOrder[currPlayerIdx];
    }

    /**
     * SG 判断当前是否是该玩家出牌
     *
     * OG TODO: 向服务器提交请求获得当前玩家，并判断
     * @param player
     * @return
     */
    public boolean checkIsRound(Player player) {
        return getCurrPlayer() == player;
    }

    /**
     * SG 设置轮次顺序，并且指定初始玩家
     * 如果为机器人则让机器人出牌
     *
     * OG TODO: 不用设置出牌，但要设置倒计时
     *
     * @param players 考虑了顺序的玩家列表
     * @param CurrPlayerIndex
     */
    public void setInitPlayer(Player[] players, int CurrPlayerIndex) {
        if (GameSystem.getInstance().getIsSingle()) {
            this.PlayerOrder = players;
            this.currPlayerIdx = CurrPlayerIndex;

            Log.e("TAG", "currPlayerIdx: " + currPlayerIdx );

            // 机器人发完牌后会指定下一个出牌
            LetRobotShowCard();
        }
        else {
            // TODO: 只设置倒计时
        }

    }

    /**
     * SG 设置为下一个玩家
     * 如果为机器人则让机器人出牌
     *
     * OG 不进行操作
     */
    public void setNextPlayer() {
        currPlayerIdx = (currPlayerIdx + 1) % Constant.PlayerCnt; // 4
        Log.e("TAG", "currPlayerIdx: " + currPlayerIdx );

        LetRobotShowCard();
    }

    /**
     * SQ 机器人出牌，有时延
     * 伪外部事件 <<<<<<<<<<
     *
     * OG 不进行操作
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
