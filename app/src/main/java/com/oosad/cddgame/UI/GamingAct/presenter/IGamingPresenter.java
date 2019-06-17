package com.oosad.cddgame.UI.GamingAct.presenter;

import android.app.ProgressDialog;
import android.content.Intent;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.UI.Widget.CardLayout;
import com.oosad.cddgame.UI.Widget.CascadeLayout;

public interface IGamingPresenter {

    void Handle_SetupBundle(Intent intent);
    void Handle_PushCard(CardLayout[] cardLayouts);
    void Handle_DistributeCard();
    void Handle_UserPassShowCard();
    void Handle_SetupRobotShowCardLayout(CascadeLayout cascadeLayout, int RobotIdx);

    int Handle_GetCardCnt(int PlayerId);
    boolean Handle_GetIsSingle();
    void Handle_PrepareButton_Click();
    void Handle_OthersShowCard(CascadeLayout cascadeLayout, Card[] cards);
    void Handle_SetupPrepareDialogCancel(ProgressDialog progressDialog);

}
