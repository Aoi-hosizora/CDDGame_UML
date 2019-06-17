package com.oosad.cddgame.Net.SocketJson;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerObj {

    String username;
    /**
     * PlayerStatus:
     * "Not Prepare" || "Prepare"
     */
    String status;

    public PlayerObj(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public static PlayerObj toPlayerObj(JSONObject jsonObject) throws JSONException {
        return new PlayerObj(
                jsonObject.getString("username"),
                jsonObject.getString("status")
        );
    }
}