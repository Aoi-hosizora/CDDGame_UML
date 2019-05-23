package com.oosad.cddgame.UI.MainAct.presenter;

import android.content.Intent;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.UI.MainAct.view.IMainView;
import com.oosad.cddgame.UI.SettingAct.SettingActivity;

public class MainPresenterCompl implements IMainPresenter {

    IMainView m_mainView;

    public MainPresenterCompl(IMainView iMainView) {
        this.m_mainView = iMainView;
    }

    @Override
    public void Handle_StartGameButton_Click() {
        if (!hasUser()) {
            m_mainView.ShowNoneUserAlert();
            return;
        }
        // start game act
    }

    @Override
    public void Handle_SettingButton_Click() {
        Intent SettingIntent = new Intent(m_mainView.getThisPtr(), SettingActivity.class);
        m_mainView.getThisPtr().startActivity(SettingIntent);
    }

    private boolean hasUser() {
        return Setting.getInstance().getCurrUser() != null;
    }
}
