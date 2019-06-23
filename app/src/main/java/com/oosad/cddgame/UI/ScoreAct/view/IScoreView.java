package com.oosad.cddgame.UI.ScoreAct.view;

import android.view.View;

import com.oosad.cddgame.UI.ScoreAct.ScoreActivity;

public interface IScoreView {
    ScoreActivity getThisPtr();
    void onBackToMainActivity();
    void onSetupFlag(boolean isSingle);

    void onAddCardLayout(int idx, View cardLayout);
    void onSetupUserName(int idx, String UserName, int CardCnt);

    void onRefreshCardLayout();
}
