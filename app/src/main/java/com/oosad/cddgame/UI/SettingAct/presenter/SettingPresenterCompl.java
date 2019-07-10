package com.oosad.cddgame.UI.SettingAct.presenter;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.Entity.Player.User;
import com.oosad.cddgame.UI.LoginAct.LoginActivity;
import com.oosad.cddgame.UI.SettingAct.view.ISettingView;

import java.util.Set;

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

        m_settingView.onSetupUI(setting.getGameBGMVoloum(), setting.getGameOtoVoloum());
    }

    /**
     * 返回主界面
     */
    @Override
    public void Handle_BackButton_Click() {
        m_settingView.onSetupUI(Setting.getInstance().getGameBGMVoloum(), Setting.getInstance().getGameOtoVoloum());
        m_settingView.onBackToMainActivity();
    }

    /**
     * 提交设置，从用户设置Setting
     * @param BGMVoloum
     * @param OtoVoloum
     */
    @Override
    public void Handle_OKButton_Click(int BGMVoloum, int OtoVoloum) {
        Setting setting = Setting.getInstance();
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

    @Override
    public void Handle_ReLoginButton_Click() {
        Intent intent = new Intent(m_settingView.getThisPtr(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        GameSystem.getInstance().setCurrUserToken("");
        m_settingView.getThisPtr().startActivity(intent);
    }

    /**
     * 调整 BGM 音量
     * @param progress
     */
    public void Handle_AdjustBGMVoloum(int progress) {
        // https://blog.csdn.net/weixin_37577039/article/details/78775931

        /*
        UI: 15 -> Sys: 100
        UI: 0 -> Sys: 0
         */

        AudioManager audioManager = (AudioManager) m_settingView.getThisPtr().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(progress * Constant.VoloumRate_100), AudioManager.FLAG_PLAY_SOUND);
    }
}
