package com.oosad.cddgame.Data.Controller;

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
    }

    private boolean isPlayingGame = false;
    private List<String> OthersUserNameList = new ArrayList<>();

    private PlayerRoomInfoObj nowPlayRoomInfo;
    private Card[] preCards;
    private PlayerObj currPlayer;

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

}
