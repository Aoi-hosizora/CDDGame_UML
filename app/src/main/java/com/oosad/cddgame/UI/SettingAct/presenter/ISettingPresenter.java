package com.oosad.cddgame.UI.SettingAct.presenter;

import android.content.Intent;

public interface ISettingPresenter {

    void Handle_BackButton_Click();
    void Handle_OKButton_Click(String userName, int BGMVoloum, int OtoVoloum);
    void Handle_ResetButton_Click();

    void Handle_SetupBundle(Intent intent);

}
