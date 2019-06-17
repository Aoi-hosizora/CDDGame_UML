package com.oosad.cddgame.Net.SocketJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerRoomInfoObj {
    /**
     * len 5
     */
    String room_number;
    /**
     * PlayerObj[]
     */
    PlayerObj[] players;
    /**
     * PlayerInRoomStatus
     * "WAITING" || "PLAYING"
     */
    String status;
    /**
     * 0 - 3
     */
    int current;
    /**
     * PlayCard[]
     */
    PlayCardObj[] precard;
    /**
     * 0 - 3
     */
    int prePlayer;
    /**
     * 0 - 30
     */
    int rest_second;

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public PlayerObj[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerObj[] players) {
        this.players = players;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public PlayCardObj[] getPrecard() {
        return precard;
    }

    public void setPrecard(PlayCardObj[] precard) {
        this.precard = precard;
    }

    public int getPrePlayer() {
        return prePlayer;
    }

    public void setPrePlayer(int prePlayer) {
        this.prePlayer = prePlayer;
    }

    public int getRest_second() {
        return rest_second;
    }

    public void setRest_second(int rest_second) {
        this.rest_second = rest_second;
    }

    public PlayerRoomInfoObj(String room_number, PlayerObj[] players, String status, int current, PlayCardObj[] precard, int prePlayer, int rest_second) {
        this.room_number = room_number;
        this.players = players;
        this.status = status;
        this.current = current;
        this.precard = precard;
        this.prePlayer = prePlayer;
        this.rest_second = rest_second;
    }

    public static PlayerRoomInfoObj toPlayerRoomInfoObj(JSONObject jsonObject) throws JSONException {

        JSONArray jsonPlayers = jsonObject.getJSONArray("players");
        PlayerObj[] players = new PlayerObj[jsonPlayers.length()];
        for (int i=0; i<jsonPlayers.length(); i++)
            players[i] = PlayerObj.toPlayerObj(jsonPlayers.getJSONObject(i));

        JSONArray jsonPreCard = jsonObject.getJSONArray("precard");
        PlayCardObj[] playCards = new PlayCardObj[jsonPreCard.length()];
        for (int i = 0; i < jsonPreCard.length(); i++)
            playCards[i] = PlayCardObj.toPlayCardObj(jsonPreCard.getJSONObject(i));

        return new PlayerRoomInfoObj(
                jsonObject.getString("room_number"),
                players,
                jsonObject.getString("status"),
                jsonObject.getInt("current"),
                playCards,
                jsonObject.getInt("prePlayer"),
                jsonObject.getInt("rest_second")
        );
    }
}