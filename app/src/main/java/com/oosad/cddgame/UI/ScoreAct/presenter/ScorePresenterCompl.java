package com.oosad.cddgame.UI.ScoreAct.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Data.Controller.OnlineInfoMgr;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Entity.Player.Player;
import com.oosad.cddgame.Net.SocketJson.PlayerObj;
import com.oosad.cddgame.UI.ScoreAct.view.IScoreView;
import com.oosad.cddgame.UI.Widget.CardLayout;
import com.oosad.cddgame.Util.CardUtil;

import java.util.List;

public class ScorePresenterCompl implements IScorePresenter {

    private IScoreView m_scoreView;

    public static String INT_ISSINGLE = "Int_IsSingle";
    public static String INT_BUNDLE = "Int_Bundle";
    private boolean isSingle;

    public ScorePresenterCompl(IScoreView scoreView) {
        this.m_scoreView = scoreView;
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "ScorePresenterCompl";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    /**
     * 设置 Bundle
     * @param intent
     */
    @Override
    public void Handle_SetupBundle(Intent intent) {
        Bundle bundle = intent.getBundleExtra(INT_BUNDLE);
        isSingle = bundle.getBoolean(INT_ISSINGLE, true);

        // 设置界面
        m_scoreView.onSetupFlag(isSingle);
        if (isSingle)
            Handle_SetupCardsAndNameforSingle();
        else
            Handle_SetupCardsAndName();
    }

    /**
     * OG 设置用户卡牌排行榜和用户名
     */
    private void Handle_SetupCardsAndNameforSingle() {
        // GameSystem.getInstance().

//        for (int i = 0; i < playerObjs.length; i ++) {
//            Card[] cards = WinnerCards.get(i);
//            PlayerObj playerObj = playerObjs[i];
//            int idx = onlineInfoMgr.getUserPosIdx(playerObj.getUsername());
//
//            for (Card c : cards) {
//                CardLayout cardLayout = CardUtil.getCardLayoutFromCard(m_scoreView.getThisPtr(), c, true);
//                m_scoreView.onAddCardLayout(idx, cardLayout);
//            }
//            m_scoreView.onSetupUserName(idx, playerObj.getUsername(), cards.length);
//        }
//        m_scoreView.onRefreshCardLayout();
    }

    /**
     * OG 设置用户卡牌排行榜和用户名
     */
    private void Handle_SetupCardsAndName() {
        OnlineInfoMgr onlineInfoMgr = GameSystem.getInstance().getOnlineInfoMgr();
        List<Card[]> WinnerCards = onlineInfoMgr.getWinedCardsList();
        PlayerObj[] playerObjs = onlineInfoMgr.getNowPlayRoomInfo().getPlayers();

        for (int i = 0; i < playerObjs.length; i ++) {
            Card[] cards = WinnerCards.get(i);
            PlayerObj playerObj = playerObjs[i];
            int idx = onlineInfoMgr.getUserPosIdx(playerObj.getUsername());

            for (Card c : cards) {
                CardLayout cardLayout = CardUtil.getCardLayoutFromCard(m_scoreView.getThisPtr(), c, true, false);
                m_scoreView.onAddCardLayout(idx, cardLayout);
            }
            m_scoreView.onSetupUserName(idx, playerObj.getUsername(), cards.length);
        }
        m_scoreView.onRefreshCardLayout();
        m_scoreView.onSetupHighLight(onlineInfoMgr.getUserPosIdx(onlineInfoMgr.getWinnerPlayer().getUsername()));
    }

    /**
     * 返回 isSingle
     * @return
     */
    @Override
    public boolean Handle_getSingle() {
        return isSingle;
    }

}
