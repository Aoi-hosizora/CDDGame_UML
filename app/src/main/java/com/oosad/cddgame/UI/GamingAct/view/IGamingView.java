package com.oosad.cddgame.UI.GamingAct.view;

import android.view.View;

import com.oosad.cddgame.UI.GamingAct.GamingActivity;

public interface IGamingView {

    void onSetupUI(String UserName);
    void onBackToMainActivity();
    GamingActivity getThisPtr();
    void onAddCardLayout(View cardLayoutArrayList);
    void onRefreshCardLayout();
    void onShowCardSet(CardLayout[] cardLayout);
    void onShowCantShowCardAlert();
    void onShowNoSelectCardAlert();
    void onShowWinAlert();
}
