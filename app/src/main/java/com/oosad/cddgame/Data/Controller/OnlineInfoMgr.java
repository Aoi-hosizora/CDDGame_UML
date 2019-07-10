package com.oosad.cddgame.Data.Controller;

import android.util.Log;

import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Net.SocketJson.PlayerObj;
import com.oosad.cddgame.Net.SocketJson.PlayerRoomInfoObj;

import java.util.ArrayList;
import java.util.List;

public class OnlineInfoMgr {

    private static volatile OnlineInfoMgr Instnace;
    private OnlineInfoMgr() {}

    public static OnlineInfoMgr getInstance() {
        if (Instnace == null) {
            Instnace = new OnlineInfoMgr();

        }
        return Instnace;
    }

    public void initInstance() {
        Instnace.isPlayingGame = false;
        Instnace.OthersUserNameList = new ArrayList<>();
        Instnace.nowPlayRoomInfo = null;
        preCards = new Card[]{};
    }

    //////////

    private boolean isPlayingGame = false;
    private List<String> OthersUserNameList = new ArrayList<>();

    private PlayerRoomInfoObj nowPlayRoomInfo;
    private Card[] preCards = new Card[]{};
    private PlayerObj currPlayer;

    /**
     * 有人胜出后的 Card[] 列表
     */
    private List<Card[]> WinedCardsList;

    public List<Card[]> getWinedCardsList() {
        return WinedCardsList;
    }

    public void setWinedCardsList(List<Card[]> winedCardsList) {
        WinedCardsList = winedCardsList;
    }

    public PlayerObj getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(PlayerObj currPlayer) {
        this.currPlayer = currPlayer;
    }

    public static OnlineInfoMgr getInstnace() {
        return Instnace;
    }

    public static void setInstnace(OnlineInfoMgr instnace) {
        Instnace = instnace;
    }

    public boolean getIsPlayingGame() {
        return isPlayingGame;
    }

    public void setIsPlayingGame(boolean playingGame) {
        isPlayingGame = playingGame;
    }

    public List<String> getOthersUserNameList() {
        return OthersUserNameList;
    }

    public void setOthersUserNameList(List<String> othersUserNameList) {
        OthersUserNameList = othersUserNameList;
    }

    public PlayerRoomInfoObj getNowPlayRoomInfo() {
        return nowPlayRoomInfo;
    }

    public void setNowPlayRoomInfo(PlayerRoomInfoObj nowPlayRoomInfo) {
        this.nowPlayRoomInfo = nowPlayRoomInfo;
    }

    public Card[] getPreCards() {
        return preCards;
    }

    public void setPreCards(Card[] preCards) {
        this.preCards = preCards;
    }

    private PlayerObj winnerPlayer;

    public PlayerObj getWinnerPlayer() {
        return winnerPlayer;
    }

    /**
     * 胜出后处理设置 Winner
     * @param cardsList
     * @param roomInfoObj
     */
    public void handleWinner(List<Card[]> cardsList, PlayerRoomInfoObj roomInfoObj) {
        int idx = 0;
        for (int i = 0; i < cardsList.size(); i ++)
            if (cardsList.get(i).length == 0)
                idx = i;

        this.winnerPlayer = roomInfoObj.getPlayers()[idx];
    }

    /**
     * 将 Json 内的数组对应到 List 内的顺序
     * @param UserName
     * @return
     */
    public int getUserPosIdx(String UserName) {
        String ThisUserName = GameSystem.getInstance().getCurrUser().getName();
        if (UserName.equals(GameSystem.getInstance().getCurrUser().getName()))
            return Constant.Down_Player;

        List<String> OthersUserNameList = getOthersUserNameList();


        Log.e("", "getUserPosIdx: " + OthersUserNameList.size() + ", " + (ThisUserName == null));

        if (OthersUserNameList == null || OthersUserNameList.size() == 0)
            return -1;

        int flag = 1;


        for (int i = 0; i < OthersUserNameList.size() + 1; i++) {
            if (OthersUserNameList.get(i).equals(ThisUserName))
                flag = 0;

            if (OthersUserNameList.get(i).equals(UserName))
                return flag + i;
        }
        return -1;
    }

}
