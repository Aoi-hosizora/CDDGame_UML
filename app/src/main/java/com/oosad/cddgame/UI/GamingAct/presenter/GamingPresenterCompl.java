package com.oosad.cddgame.UI.GamingAct.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Player.Robot;
import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.Entity.Player.User;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Util.CardUtil;
import com.oosad.cddgame.UI.Widget.CardLayout;
import com.oosad.cddgame.UI.Widget.CascadeLayout;
import com.oosad.cddgame.UI.GamingAct.view.IGamingView;

public class GamingPresenterCompl implements IGamingPresenter {

    private IGamingView m_GamingView;

    public static final String INT_SETTING_INFO = "SETTING_INFO";
    public static final String INT_BUNDLE_INFO = "BUNDLE_INFO";


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
     * 处理从 MainAct 传递进来的 Bundle 数据
     * @param intent
     */
    @Override
    public void Handle_SetupBundle(Intent intent) {
        Bundle bundle = intent.getBundleExtra(INT_BUNDLE_INFO);
        Setting setting = (Setting) bundle.getSerializable(INT_SETTING_INFO);
        User currUser = GameSystem.getInstance().getCurrUser();

        m_GamingView.onSetupUI(currUser.getName());
    }

    /**
     * 暂停游戏
     */
    @Override
    public void Handle_PauseGameButton_Click() {

    }

    /**
     * 处理发牌，并显示
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

        // 处理出牌规则判断 !!!!!!
        int ret = GameSystem.getInstance().canShowCardWithCheckTurn(cards, GameSystem.getInstance().getCurrUser());

        if (ret == Constant.NO_ERR)  // 允许这样出牌，并且已经在 CardMgr 内更新了相关信息，直接显示出牌更新界面
            m_GamingView.onUserShowCardSet(cardSetLayout);
        else if (ret == Constant.ERR_NOT_RULE)
            m_GamingView.onShowCantShowCardForRuleAlert();
        else if (ret == Constant.ERR_NOT_ROUND)
            m_GamingView.onShowCantShowCardForRoundAlert();
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
}
