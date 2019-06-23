package com.oosad.cddgame.Net.SocketJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class PlayerObj implements Serializable {

    private String username;
    /**
     * PlayerStatus:
     * "Not Prepare" || "Prepare"
     */
    private String status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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