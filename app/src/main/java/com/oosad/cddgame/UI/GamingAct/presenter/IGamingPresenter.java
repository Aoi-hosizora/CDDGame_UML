package com.oosad.cddgame.UI.GamingAct.presenter;

import android.content.Intent;

import com.oosad.cddgame.UI.GamingAct.view.CardLayout;

public interface IGamingPresenter {

    void Handle_SetupBundle(Intent intent);
    void Handle_PauseGameButton_Click();
    void Handle_PushCard(CardLayout[] cardLayouts);
    void Handle_DistributeCard();
    void Handle_JumpShowCard();

}
