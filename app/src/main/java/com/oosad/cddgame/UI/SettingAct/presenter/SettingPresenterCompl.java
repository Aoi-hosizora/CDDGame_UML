package com.oosad.cddgame.UI.SettingAct.presenter;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.User;
import com.oosad.cddgame.UI.SettingAct.view.ISettingView;

public class SettingPresenterCompl implements ISettingPresenter {

    private ISettingView m_settingView;

    public SettingPresenterCompl(ISettingView iSettingView) {
        this.m_settingView = iSettingView;
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
        ResetSetting();
    }

    private void CommitSetting(String userName, int BGMVoloum, int OtoVoloum) {
        User currUser = new User(userName);

        Setting setting = Setting.getInstance();
        setting.setCurrUser(currUser);
        setting.setGameBGMVoloum(BGMVoloum);
        setting.setGameOtoVoloum(OtoVoloum);
    }

    private void ResetSetting() {
        m_settingView.onResetSetting();
    }

}
