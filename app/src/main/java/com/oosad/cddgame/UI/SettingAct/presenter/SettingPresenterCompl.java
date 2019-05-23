package com.oosad.cddgame.UI.SettingAct.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.User;
import com.oosad.cddgame.UI.SettingAct.view.ISettingView;

public class SettingPresenterCompl implements ISettingPresenter {

    public static final String INT_SETTING_INFO = "SETTING_INFO";
    public static final String INT_BUNDLE_INFO = "BUNDLE_INFO";

    private ISettingView m_settingView;

    public SettingPresenterCompl(ISettingView iSettingView) {
        this.m_settingView = iSettingView;
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "SettingPresenterCompl";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    @Override
    public void Handle_SetupBundle(Intent intent) {
        Bundle bundle = intent.getBundleExtra(INT_BUNDLE_INFO);
        Setting setting = (Setting) bundle.getSerializable(INT_SETTING_INFO);
        User currUser = setting.getCurrUser();

        m_settingView.onSetupUI(currUser.getName(), setting.getGameBGMVoloum(), setting.getGameOtoVoloum());
    }

    @Override
    public void Handle_BackButton_Click() {
        m_settingView.onBackToMainActivity();
    }

    @Override
    public void Handle_OKButton_Click(String userName, int BGMVoloum, int OtoVoloum) {
        CommitSetting(userName, BGMVoloum, OtoVoloum);
        m_settingView.onBackToMainActivity();
    }

    @Override
    public void Handle_ResetButton_Click() {
        // Setting.getInstance().resetSetting();
        m_settingView.onResetUI();
    }

    private void CommitSetting(String userName, int BGMVoloum, int OtoVoloum) {
        User currUser = new User(userName);

        Setting setting = Setting.getInstance();
        setting.setCurrUser(currUser);
        setting.setGameBGMVoloum(BGMVoloum);
        setting.setGameOtoVoloum(OtoVoloum);
    }
}
