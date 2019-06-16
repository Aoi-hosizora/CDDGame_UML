package com.oosad.cddgame.UI.GamingAct.presenter;

import android.content.Intent;

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

}
