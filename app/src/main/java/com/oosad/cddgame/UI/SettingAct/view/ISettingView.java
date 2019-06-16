package com.oosad.cddgame.UI.SettingAct.view;

import com.oosad.cddgame.UI.SettingAct.SettingActivity;

public interface ISettingView {

    void onBackToMainActivity();
    void onResetUI();
    void onSetupUI(int BGMVoloum, int OtoVoloum);

    SettingActivity getThisPtr();
}
