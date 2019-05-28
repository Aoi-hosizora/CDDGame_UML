package com.oosad.cddgame.UI.GamingAct.model.system;

import android.os.Handler;
import android.util.Log;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Player;

public class GameRound {

    private GameRound() {}
    private static volatile GameRound Instance;

    public static GameRound getInstane() {
        if (Instance == null) synchronized (GameRound.class) {
            if (Instance == null) {
                Instance = new GameRound();
            }
        }
        return Instance;
    }

    /**
     * 当前玩家在列表中的idx
     */
    private int currPlayerIdx;
    /**
     * 玩家顺序列表: PLAYER_USER, PLAYER_ROBOT_1, PLAYER_ROBOT_2, PLAYER_ROBOT_3
     */
    private Player[] PlayerOrder;

    public Player getCurrPlayer() {
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
     * @param players 考虑了顺序的玩家列表
     * @param CurrPlayerIndex
     */
    public void setInitPlayer(Player[] players, int CurrPlayerIndex) {
        this.PlayerOrder = players;
        this.currPlayerIdx = CurrPlayerIndex;

        Log.e("TAG", "currPlayerIdx: " + currPlayerIdx );
        LetRobotShowCard();
    }

    public void setNextPlayer() {
        currPlayerIdx = (currPlayerIdx + 1) % Constant.PlayerCnt; // 4
        Log.e("TAG", "currPlayerIdx: " + currPlayerIdx );

        LetRobotShowCard();
    }

    private void LetRobotShowCard() {
        if (currPlayerIdx != Constant.PLAYER_USER) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    GameSystem.getInstance().getRobot(currPlayerIdx).showCard();
                }

            }, Constant.TIME_WaitBeforeRobotShowCard);

        }
    }
}
