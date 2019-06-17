package com.oosad.cddgame.Data.Controller;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Player.Player;
import com.oosad.cddgame.Data.Entity.Player.Robot;
import com.oosad.cddgame.Data.Entity.Player.User;
import com.oosad.cddgame.Data.Rules.NormalRuleCheckAdapter;

/**
 * PlayerMgr Controller:
 *
 * +setCurrUser / +getCurrUser
 * +getRobot / +getPlayers
 */
public class PlayerMgr {

    private PlayerMgr() {}

    private static volatile PlayerMgr instance;

    public static PlayerMgr getInstance() {
        if (instance == null)
            synchronized (PlayerMgr.class) {
                if (instance == null) {
                    instance = new PlayerMgr();
                    instance.RobotMgr = new Robot[] {
                            new Robot(Constant.PLAYER_ROBOT_1),
                            new Robot(Constant.PLAYER_ROBOT_2),
                            new Robot(Constant.PLAYER_ROBOT_3)
                    };
                }


            }
        return instance;
    }

    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////

    private Robot[] RobotMgr;
    private User GameUser;
    private String UserToken = "";

    /**
     * 设置当前玩家
     * @param user
     */
    public void setCurrUser(User user) {
        this.GameUser = user;
    }

    /**
     * OG 设置当前玩家 Token
     * @param Token
     */
    public void setCurrUserToken(String Token) {
        this.UserToken = Token;
    }

    /**
     * OG 获取当前玩家 Token
     */
    public String getCurrUserToken() {
        return UserToken;
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

    public Player[] getPlayers() {
        return new Player[] {
                getCurrUser(),
                RobotMgr[0], RobotMgr[1], RobotMgr[2]
        };
    }

}
