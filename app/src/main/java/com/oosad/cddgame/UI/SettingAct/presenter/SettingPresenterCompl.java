package com.oosad.cddgame.UI.SettingAct.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.Entity.Player.User;
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

    /**
     * 处理从 MainAct 传递进的 Bundle 数据，并设置用户界面
     * @param intent
     */
    @Override
    public void Handle_SetupBundle(Intent intent) {
        Bundle bundle = intent.getBundleExtra(INT_BUNDLE_INFO);
        Setting setting = (Setting) bundle.getSerializable(INT_SETTING_INFO);
        User currUser = setting.getCurrUser();

        m_settingView.onSetupUI(currUser.getName(), setting.getGameBGMVoloum(), setting.getGameOtoVoloum());
    }

    /**
     * 返回主界面
     */
    @Override
    public void Handle_BackButton_Click() {
        m_settingView.onBackToMainActivity();
    }

    /**
     * 提交设置，从用户键入的数据设置Setting
     * @param userName
     * @param BGMVoloum
     * @param OtoVoloum
     */
    @Override
    public void Handle_OKButton_Click(String userName, int BGMVoloum, int OtoVoloum) {
        User currUser = new User(userName);

        Setting setting = Setting.getInstance();
        setting.setCurrUser(currUser);
        setting.setGameBGMVoloum(BGMVoloum);
        setting.setGameOtoVoloum(OtoVoloum);

        m_settingView.onBackToMainActivity();
    }

    /**
     * 重置用户显示的设置，但不重置已经提交的Handle_BackButton_Click设置
     */
    @Override
    public void Handle_ResetButton_Click() {
        // Setting.getInstance().resetSetting();
        m_settingView.onResetUI();
    }
}
