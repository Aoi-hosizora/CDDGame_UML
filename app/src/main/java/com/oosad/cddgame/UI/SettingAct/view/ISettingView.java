package com.oosad.cddgame.UI.SettingAct.view;

public interface ISettingView {

    void onBackToMainActivity();
    void onResetUI();
    void onShowNoneUserAlert();
    void onSetupUI(String UserName, int BGMVoloum, int OtoVoloum);
}
