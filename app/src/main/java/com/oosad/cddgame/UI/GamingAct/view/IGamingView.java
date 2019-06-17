package com.oosad.cddgame.UI.GamingAct.view;

import android.view.View;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.UI.GamingAct.GamingActivity;
import com.oosad.cddgame.UI.Widget.CardLayout;

public interface IGamingView {

    void onSetupUI(String UserName, boolean isSingle);
    void onBackToMainActivity();
    GamingActivity getThisPtr();
    void onAddCardLayout(View cardLayoutArrayList);
    void onRefreshCardLayout();
    void onUserShowCardSet(CardLayout[] cardLayout);
    void onShowCantShowCardForRuleAlert();
    void onShowCantShowCardForRoundAlert();
    void onShowNoSelectCardAlert();
    void onShowWinAlert();
    void onRefreshShowCardCnt();
    void onPassShowCard(int PlayerId);
    void onHidePassShowCard(int PlayerId);
    void onSetUpOnlinePlayingLayout(String UserNameLeft, String UserNameUp, String UserNameRight);
    void onUpdateOnlinePlayingLayout(int idx, Card[] cards);
    void onUpdateProgressDialog(int cnt);
    void onShowProgressDialog();
}
