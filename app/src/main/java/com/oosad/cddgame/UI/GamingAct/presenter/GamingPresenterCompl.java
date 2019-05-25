package com.oosad.cddgame.UI.GamingAct.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.User;
import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.util.CardUtil;
import com.oosad.cddgame.UI.GamingAct.view.CardLayout;
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
        User currUser = setting.getCurrUser();

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
        Context context = m_GamingView.getThisPtr();
        Card[] cards = CardUtil.DistributeCards();
        for (Card c : cards) {
            m_GamingView.onAddCardLayout(CardUtil.getCardLayoutFromCard(context, c, false));
        }
        m_GamingView.onRefreshCardLayout();
    }

    /**
     * 处理出牌，重要
     * @param cardLayouts
     */
    @Override
    public void Handle_PushCard(CardLayout[] cardLayouts) {
        // cardset.vvvv

        // Sys.出牌(cards, 用户) : bool

        CardLayout[] cardSetLayout = CardUtil.getCardSetLayoutUp(cardLayouts);
        m_GamingView.onShowCardSet(cardSetLayout);
    }
}
