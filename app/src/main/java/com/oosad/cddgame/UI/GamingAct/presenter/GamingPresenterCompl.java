package com.oosad.cddgame.UI.GamingAct.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Controller.OnlineInfoMgr;
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

import org.json.JSONObject;

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

        if (!isSingle) {

            setupSocketListener();
        }
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
            // TODO
            Handle_ShowCardOnline(cardLayouts);
        }

    }

    /**
     * SO OG
     * 跳过出牌
     */
    @Override
    public void Handle_UserPassShowCard() {
        if (isSingle) {
            GameSystem.getInstance().canShowCardWithCheckTurn(null, GameSystem.getInstance().getCurrUser());
            m_GamingView.onPassShowCard(Constant.PLAYER_USER);
        }
        else {
            // TODO
            Handle_ShowCardOnline(new CardLayout[] {});
        }
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

    OnlineInfoMgr onlineInfoMgr = null;

    /**
     * 设置监听并启动 Socket
     */
    private void setupSocketListener() {
        onlineInfoMgr = GameSystem.getInstance().getOnlineInfoMgr();
        SocketHandlers.Connect(GameSystem.getInstance().getCurrUserToken());
        SocketHandlers.setmOnEndListener(this);
        SocketHandlers.setmOnPlayingListener(this);
        SocketHandlers.setmOnWaitingListener(this);
    }

    /**
     * 取消准备
     * @param progressDialog
     */
    @Override
    public void Handle_SetupPrepareDialogCancel(ProgressDialog progressDialog) {
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                SocketHandlers.EmitCanclePrepare();
            }
        });
    }

    @Override
    public void Handle_PrepareButton_Click() {
        SocketHandlers.EmitPrepare();
        m_GamingView.onShowProgressDialog();
    }

    private final int onWaitingFinish = 0;
    private final int onPlayingTochu = 1;
    private final int onEnded = 2;

    private final Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case onWaitingFinish: {

                    String currUserName = GameSystem.getInstance().getCurrUser().getName();
                    PlayerRoomInfoObj playerRoomInfo = (PlayerRoomInfoObj) msg.obj;
                    PlayerObj[] allplays = playerRoomInfo.getPlayers();

                    List<String> OthersUserNameList = onlineInfoMgr.getOthersUserNameList();

                    for (PlayerObj playerObj : allplays) {
                        String pname = playerObj.getUsername();
                        if (!pname.equals(currUserName)) {
                            OthersUserNameList.add(pname);

                        }
                    }

                    // 设置界面，用户名和牌数
                    m_GamingView.onSetUpOnlinePlayingLayout(
                            OthersUserNameList.get(Constant.Left_Player - 1),
                            OthersUserNameList.get(Constant.Up_Player - 1),
                            OthersUserNameList.get(Constant.Right_Player - 1)
                    );


//                    ShowLogE("handleMessage",
//                            OthersUserNameList.get(Constant.Left_Player - 1) +","+ OthersUserNameList.get(Constant.Up_Player - 1) +","+ OthersUserNameList.get(Constant.Right_Player - 1));

                }
                    break;
                case onPlayingTochu: {
                    ShowLogE("handleMessage", "onPlayingTochu");

/*                  {
                        "room_number":"29583",
                        "players":[
                            {"username":"user1","status":"Prepare"},
                            {"username":"1234","status":"Prepare"},
                            {"username":"testuser2","status":"Prepare"},
                            {"username":"testuser","status":"Prepare"}
                        ],
                        "status":"PLAYING",
                        "current":1,
                        "precard":[
                            {"number":1,"type":"RED HEART"}
                        ],
                        "prePlayer":0,
                        "rest_second":30
                    }
*/
                    Bundle bundle = msg.getData();
                    PlayerRoomInfoObj playerRoomInfo = (PlayerRoomInfoObj) bundle.getSerializable("PlayerRoomInfoObj");
                    Card[] cards = (Card[]) bundle.getSerializable("Card[]");
                    Card[] precards = PlayCardObj.toCardArr(playerRoomInfo.getPrecard());

                    // 发好牌，显示自己的牌

                    m_GamingView.onRemoveAllCards();
                    for (Card c : cards)
                        m_GamingView.onAddCardLayout(CardUtil.getCardLayoutFromCard(m_GamingView.getThisPtr(), c, false));

                    m_GamingView.onRefreshCardLayout();

                    int prePlayer = playerRoomInfo.getPrePlayer();

                    if (prePlayer != -1) {

                        PlayerObj prePlayerObj = playerRoomInfo.getPlayers()[prePlayer];

                        // 更新界面
                        m_GamingView.onUpdateOnlinePlayingLayout(getUserPosIdx(prePlayerObj.getUsername()), precards);

                    }

                    // 更新数据
                    onlineInfoMgr.setNowPlayRoomInfo(playerRoomInfo);
                    onlineInfoMgr.setPreCards(PlayCardObj.toCardArr(playerRoomInfo.getPrecard()));
                    onlineInfoMgr.setCurrPlayer(playerRoomInfo.getPlayers()[playerRoomInfo.getCurrent()]);
                    break;
                }
                case onEnded: {
                    Bundle bundle = msg.getData();
                    PlayerRoomInfoObj playerRoomInfo = (PlayerRoomInfoObj) bundle.getSerializable("PlayerRoomInfoObj");
                    List<Card[]> cardslist = new ArrayList<>();
                    cardslist.add((Card[]) bundle.getSerializable("Card[]0"));
                    cardslist.add((Card[]) bundle.getSerializable("Card[]1"));
                    cardslist.add((Card[]) bundle.getSerializable("Card[]2"));
                    cardslist.add((Card[]) bundle.getSerializable("Card[]3"));
                    m_GamingView.onShowWinAlert();

                    break;
                }
            }
            return false;
        }
    });

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
        if (!onlineInfoMgr.getIsPlayingGame()) {

            // 设置 等待对话框
            int cntForPrepare = 0;
            for (PlayerObj playerObj : playerRoomInfo.getPlayers()) {
                if (playerObj.getStatus().equals(JsonConst.PlayerStatus.Prepare))
                    cntForPrepare ++;
            }

            m_GamingView.onUpdateProgressDialog(cntForPrepare);

            onlineInfoMgr.setIsPlayingGame(playerRoomInfo.getStatus().equals(JsonConst.PlayerInRoomStatus.Playing));

            if (onlineInfoMgr.getIsPlayingGame()) {
                // 都进入准备

                // use handler
                Message message = new Message();
                message.what = onWaitingFinish;
                message.obj = playerRoomInfo;
                handler.sendMessage(message);
            }
        }
    }

    /**
     * 将 Json 内的数组对应到 List 内的顺序
     * @param UserName
     * @return
     */
    private int getUserPosIdx(String UserName) {
        String ThisUserName = GameSystem.getInstance().getCurrUser().getName();
        List<String> OthersUserNameList = onlineInfoMgr.getOthersUserNameList();

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
     *      {"number":1,"type":"RED HEART"}, ... <- prePlayer 出的牌
     *  ],
     *  "prePlayer":1,
     *  "rest_second":30
     * }
     *
     * [
     *  {"number":4,"type":"BLACK SPADE"}, ... <- 玩家出的牌
     * ]
     * @param playerRoomInfo
     * @param cards
     */
    @Override
    public void onPlaying(PlayerRoomInfoObj playerRoomInfo, Card[] cards) {
        // start timer
        ShowLogE("onPlaying", "" + playerRoomInfo.getCurrent());

        // 通知界面：将 prePlayer 的牌数减少 cards.len，并更新

        Message message = new Message();
        message.what = onPlayingTochu;

        Bundle bundle = new Bundle();
        bundle.putSerializable("PlayerRoomInfoObj", playerRoomInfo);
        bundle.putSerializable("Card[]", cards);
        message.setData(bundle);

        handler.sendMessage(message);
    }


    /**
     * 更新他人的出牌布局
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
     * OG 处理出牌，判断
     * @param cardLayouts
     */
    private void Handle_ShowCardOnline(CardLayout[] cardLayouts) {

        CardLayout[] cardSetLayout = CardUtil.getCardSetLayoutUp(cardLayouts);

        ShowLogE("Handle_ShowCardOnline", "cardSetLayout: " + cardLayouts.length);

        // 想要出的 Card[]
        Card[] cards = CardUtil.getCardsFromCardLayouts(cardSetLayout);

        ShowLogE("Handle_ShowCardOnline", "cards: " + cards.length);

        // TODO check rcvd

        // 当前轮到的玩家
        PlayerObj nowPlayer = onlineInfoMgr.getCurrPlayer();

        if (!nowPlayer.getUsername().equals(GameSystem.getInstance().getCurrUser().getName()))
            // ERR_NOT_ROUND
            m_GamingView.onShowCantShowCardForRoundAlert();
        else if (!GameSystem.getInstance().getRuleCheck().checkShowCardRule(onlineInfoMgr.getPreCards(), cards))
            // ERR_NOT_RULE
            m_GamingView.onShowCantShowCardForRuleAlert();
        else {
            // NO_ERR
            SocketHandlers.EmitShowCard(cards);
            m_GamingView.onUserShowCardSet(cardSetLayout);
        }
    }

    @Override
    public void onEnd(PlayerRoomInfoObj playerRoomInfo, List<Card[]> cards) {
        // start timer
        ShowLogE("onEnd", "" + playerRoomInfo.getCurrent());

        // 通知界面：将 prePlayer 的牌数减少 cards.len，并更新

        Message message = new Message();
        message.what = onEnded;

        Bundle bundle = new Bundle();
        bundle.putSerializable("PlayerRoomInfoObj", playerRoomInfo);
        bundle.putSerializable("Card[]0", cards.get(0));
        bundle.putSerializable("Card[]1", cards.get(1));
        bundle.putSerializable("Card[]2", cards.get(2));
        bundle.putSerializable("Card[]3", cards.get(3));
        message.setData(bundle);

        handler.sendMessage(message);
    }
}
