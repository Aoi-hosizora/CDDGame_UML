package com.oosad.cddgame.UI.GamingAct.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Player.Player;
import com.oosad.cddgame.Data.Entity.Player.Robot;
import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.Entity.Player.User;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Net.JsonConst;
import com.oosad.cddgame.Net.SocketHandlers;
import com.oosad.cddgame.Net.SocketJson.PlayCardObj;
import com.oosad.cddgame.Net.SocketJson.PlayerObj;
import com.oosad.cddgame.Net.SocketJson.PlayerRoomInfoObj;
import com.oosad.cddgame.Util.CardUtil;
import com.oosad.cddgame.UI.Widget.CardLayout;
import com.oosad.cddgame.UI.Widget.CascadeLayout;
import com.oosad.cddgame.UI.GamingAct.view.IGamingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamingPresenterCompl implements IGamingPresenter,
        SocketHandlers.onWaitingListener, SocketHandlers.onEndListener, SocketHandlers.onPlayingListener {

    private IGamingView m_GamingView;

    public static final String INT_SINGLE_ONLINE = "SINGLE_ONLINE";
    public static final String INT_BUNDLE_INFO = "BUNDLE_INFO";

    /**
     * 当前是否为单机游戏
     */
    private boolean isSingle;

    public GamingPresenterCompl(IGamingView iGamingView) {
        this.m_GamingView = iGamingView;
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "GamingPresenterCompl";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    /**
     * !!!!!!!!!!
     * 返回是否是单机游戏，用在：
     *      setupPlayerLayout
     * @return
     */
    @Override
    public boolean Handle_GetIsSingle() {
        return isSingle;
    }

    /**
     * 处理从 MainAct 传递进来的 Bundle 数据
     * @param intent
     */
    @Override
    public void Handle_SetupBundle(Intent intent) {
        Bundle bundle = intent.getBundleExtra(INT_BUNDLE_INFO);
        isSingle = bundle.getBoolean(INT_SINGLE_ONLINE, true);
        GameSystem.getInstance().setIsSingle(isSingle);

        User currUser = GameSystem.getInstance().getCurrUser();

        m_GamingView.onSetupUI(currUser.getName(), isSingle);

        setupSocketListener();
    }

    /**
     * SG 本机发牌，返回玩家的牌显示
     *
     * OG 服务器请求发牌，返回本玩家的牌
     */
    @Override
    public void Handle_DistributeCard() {
        // 发牌前初始化游戏数据
        GameSystem.getInstance().initGame();

        Context context = m_GamingView.getThisPtr();
        // 记录：更新Cnt，调试问题
        CardLayout.clearCardUpCnt();
        Card[] cards = GameSystem.getInstance().DistributeCardForStartGame();

        for (Card c : cards)
            m_GamingView.onAddCardLayout(CardUtil.getCardLayoutFromCard(context, c, false));

        m_GamingView.onRefreshCardLayout();
    }

    /**
     * SG OG
     * 处理出牌，重要
     * @param cardLayouts
     */
    @Override
    public void Handle_PushCard(CardLayout[] cardLayouts) {

        CardLayout[] cardSetLayout = CardUtil.getCardSetLayoutUp(cardLayouts);

        // 想要出的 Card[]
        Card[] cards = CardUtil.getCardsFromCardLayouts(cardSetLayout);
        ShowLogE("Handle_PushCard", ""+cards.length);
        if (cards.length == 0) {
            // 没有选择出牌
            m_GamingView.onShowNoSelectCardAlert();
            return;
        }

        if (isSingle) {

            // 处理出牌规则判断 !!!!!!
            int ret = GameSystem.getInstance().canShowCardWithCheckTurn(cards, GameSystem.getInstance().getCurrUser());

            if (ret == Constant.NO_ERR)  // 允许这样出牌，并且已经在 CardMgr 内更新了相关信息，直接显示出牌更新界面
                m_GamingView.onUserShowCardSet(cardSetLayout);
            else if (ret == Constant.ERR_NOT_RULE)
                m_GamingView.onShowCantShowCardForRuleAlert();
            else if (ret == Constant.ERR_NOT_ROUND)
                m_GamingView.onShowCantShowCardForRoundAlert();

        }
        else {


            // todo
            
            // 处理出牌规则判断 !!!!!!
            int ret = GameSystem.getInstance().canShowCardWithCheckTurn(cards, GameSystem.getInstance().getCurrUser());

            if (ret == Constant.NO_ERR)  // 允许这样出牌，并且已经在 CardMgr 内更新了相关信息，直接显示出牌更新界面
                m_GamingView.onUserShowCardSet(cardSetLayout);
            else if (ret == Constant.ERR_NOT_RULE)
                m_GamingView.onShowCantShowCardForRuleAlert();
            else if (ret == Constant.ERR_NOT_ROUND)
                m_GamingView.onShowCantShowCardForRoundAlert();

        }

    }

    /**
     * 跳过出牌
     */
    @Override
    public void Handle_UserPassShowCard() {
        GameSystem.getInstance().canShowCardWithCheckTurn(null, GameSystem.getInstance().getCurrUser());
        m_GamingView.onPassShowCard(Constant.PLAYER_USER);
    }

    /**
     * 处理机器人出牌后数据的更新
     * @param cascadeLayout
     * @param RobotIdx
     */
    @Override
    public void Handle_SetupRobotShowCardLayout(final CascadeLayout cascadeLayout, final int RobotIdx) {

        Robot robot = GameSystem.getInstance().getRobot(RobotIdx);

        robot.setOnRobotShowCard(new Robot.OnRobotShowCardListener() {
            @Override
            public void onShowCard(final Card[] cards) {

                cascadeLayout.removeAllViews(); // 清除布局后添加时延

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (Card c : cards) {
                            // 获得机器人出的每一张牌，并转换成 ShowCardLayout
                            CardLayout cl = CardUtil.getCardLayoutFromCard(m_GamingView.getThisPtr(), c, true);

                            m_GamingView.onHidePassShowCard(RobotIdx);
                            cascadeLayout.addView(cl);
                        }
                        m_GamingView.onRefreshShowCardCnt();
                    }

                }, Constant.TIME_WaitByClearRobotShowCardLayout);


            }

            @Override
            public void onPassCard() {
                m_GamingView.onPassShowCard(RobotIdx);
            }
        });
    }

    /**
     * 通过 PlayerId 获得 剩下的牌数
     * @param PlayerId
     * @return
     */
    @Override
    public int Handle_GetCardCnt(int PlayerId) {
        if (PlayerId == 0) // User
            return GameSystem.getInstance().getPlayerCardsCnt(GameSystem.getInstance().getCurrUser());

        return GameSystem.getInstance().getPlayerCardsCnt(GameSystem.getInstance().getRobot(PlayerId));
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// Online ////////////////////////////////////////

    private boolean isPlayingGame = false;
    private List<String> OthersUserNameList = new ArrayList<>();

    /**
     * 设置监听并启动 Socket
     */
    private void setupSocketListener() {
        SocketHandlers.Connect(GameSystem.getInstance().getCurrUserToken());
        SocketHandlers.setmOnEndListener(this);
        SocketHandlers.setmOnPlayingListener(this);
        SocketHandlers.setmOnEndListener(this);
    }

    @Override
    public void Handle_PrepareButton_Click() {
        SocketHandlers.EmitPrepare();
    }

    /**
     * {
     *  "room_number":"46787",
     *  "players":[
     *      {"username":"testuser2","status":"Prepare"},
     *      {"username":"user1","status":"Prepare"},
     *      {"username":"user2","status":"Not Prepare"},
     *      {"username":"1234","status":"Not Prepare"}
     *  ],
     *  "status":"WAITING",
     *  "current":0,
     *  "precard":[],
     *  "prePlayer":-1,
     *  "rest_second":30
     * }
     * @param playerRoomInfo
     */
    @Override
    public void onWaiting(PlayerRoomInfoObj playerRoomInfo) {
        if (!isPlayingGame) {
            isPlayingGame = playerRoomInfo.getStatus().equals(JsonConst.PlayerInRoomStatus.Waiting);

            if (isPlayingGame) {
                String currUserName = GameSystem.getInstance().getCurrUser().getName();
                PlayerObj[] allplays = playerRoomInfo.getPlayers();

                for (PlayerObj playerObj : allplays) {
                    String pname = playerObj.getUsername();
                    if (!pname.equals(currUserName)) {
                        OthersUserNameList.add(pname);
                    }
                }

                m_GamingView.onSetUpOnlinePlayingLayout(
                        OthersUserNameList.get(Constant.Left_Player - 1),
                        OthersUserNameList.get(Constant.Up_Player - 1),
                        OthersUserNameList.get(Constant.Right_Player - 1)
                );

            }
        }

        if (!isPlayingGame) {
            boolean isPlayingGame = true;

            // TODO

        }
    }

    private int getUserPosIdx(String UserName) {
        String ThisUserName = GameSystem.getInstance().getCurrUser().getName();

        int flag = 1;
        for (int i = 0; i < OthersUserNameList.size() + 1; i++) {
            if (OthersUserNameList.get(i).equals(ThisUserName))
                flag = 0;

            if (OthersUserNameList.get(i).equals(UserName))
                return flag + i;
        }
        return Constant.Down_Player;
    }

    /**
     * {
     *  "room_number":"46787",
     *  "players":[...]
     *  "status":"PLAYING",
     *  "current":2,
     *  "precard":[
     *      {"number":1,"type":"RED HEART"}
     *  ],
     *  "prePlayer":1,
     *  "rest_second":30
     * }
     *
     * [
     *  {"number":4,"type":"BLACK SPADE"}, ...
     * ]
     * @param playerRoomInfo
     * @param card
     */
    @Override
    public void onPlaying(PlayerRoomInfoObj playerRoomInfo, Card card) {
        // start timer

        int current = playerRoomInfo.getCurrent();
        Card[] shownCards = PlayCardObj.toCardArr(playerRoomInfo.getPrecard());
        PlayerObj playerObjs = playerRoomInfo.getPlayers()[current];

        m_GamingView.onUpdateOnlinePlayingLayout(getUserPosIdx(playerObjs.getUsername()), shownCards);
    }


    /**
     * 更新出牌布局
     * @param cascadeLayout
     * @param cards
     */
    @Override
    public void Handle_OthersShowCard(CascadeLayout cascadeLayout, Card[] cards) {
        cascadeLayout.removeAllViews();
        for (Card c : cards) {
            CardLayout cl = CardUtil.getCardLayoutFromCard(m_GamingView.getThisPtr(), c, true);
            cascadeLayout.addView(cl);
        }
        cascadeLayout.refreshLayout();
    }

    /**
     *
     * @param playerRoomInfo
     * @param cards
     */
    @Override
    public void onEnd(PlayerRoomInfoObj playerRoomInfo, Card[] cards) {

    }
}
