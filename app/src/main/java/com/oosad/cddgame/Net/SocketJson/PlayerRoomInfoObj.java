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