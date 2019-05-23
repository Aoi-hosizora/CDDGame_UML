package com.oosad.cddgame.UI.MainAct.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.UI.MainAct.view.IMainView;
import com.oosad.cddgame.UI.SettingAct.SettingActivity;
import com.oosad.cddgame.UI.SettingAct.presenter.SettingPresenterCompl;

import java.util.Set;

public class MainPresenterCompl implements IMainPresenter {

    IMainView m_mainView;

    public MainPresenterCompl(IMainView iMainView) {
        this.m_mainView = iMainView;
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "MainPresenterCompl";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    @Override
    public void Handle_StartGameButton_Click() {
        if (!hasUser()) {
            m_mainView.ShowNoneUserAlert();
            return;
        }
        ShowLogE("Handle_StartGameButton_Click", Setting.getInstance().getCurrUser().getName());
    }

    @Override
    public void Handle_SettingButton_Click() {
        Intent SettingIntent = new Intent(m_mainView.getThisPtr(), SettingActivity.class);
        Bundle SettingBuldle = new Bundle();

        Setting setting = Setting.getInstance();
        SettingBuldle.putSerializable(SettingPresenterCompl.INT_SETTING_INFO, setting);

        SettingIntent.putExtra(SettingPresenterCompl.INT_BUNDLE_INFO, SettingBuldle);
        m_mainView.getThisPtr().startActivity(SettingIntent);
    }

    private boolean hasUser() {
        return !Setting.getInstance().getCurrUser().getName().isEmpty();
    }
}
