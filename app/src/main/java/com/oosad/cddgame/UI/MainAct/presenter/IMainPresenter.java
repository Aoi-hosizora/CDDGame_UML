package com.oosad.cddgame.UI.MainAct.presenter;

public interface IMainPresenter {

    void Handle_StartGameButton_Click(boolean isSingle);
    void Handle_SettingButton_Click();

    String Handle_GetUserName();
}
