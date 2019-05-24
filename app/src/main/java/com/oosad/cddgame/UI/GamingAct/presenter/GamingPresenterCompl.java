package com.oosad.cddgame.UI.GamingAct.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.User;
import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.util.CardUtil;
import com.oosad.cddgame.UI.GamingAct.view.CardLayout;
import com.oosad.cddgame.UI.GamingAct.view.IGamingView;

import java.util.ArrayList;
import java.util.List;

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
     * 处理出牌
     * @param cardLayouts
     */
    @Override
    public void Handle_PushCard(CardLayout[] cardLayouts) {
        CardLayout[] cardSetLayout = CardUtil.getCardSetLayoutUp(cardLayouts);
        for (CardLayout c : cardSetLayout) {
            if (c != null) {
                m_GamingView.onShowCardSet(c);
                ShowLogE("Handle_PushCardButton_Click", "" + c.getCard().getCardNum());
            }
        }
    }

    /**
     * 处理发牌，并显示
     */
    @Override
    public void Handle_DistributeCard() {
        Context context = m_GamingView.getThisPtr();
        Card[] cards = CardUtil.DistributeCards();
        for (Card c : cards) {
            m_GamingView.onAddCardLayout(CardUtil.getMainCardLayoutFromCard(context, c));
        }
        m_GamingView.onRefreshCardLayout();
    }
}
