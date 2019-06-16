package com.oosad.cddgame.Socket;

import android.support.annotation.NonNull;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import com.oosad.cddgame.Data.Entity.Card;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketHandlers {


    private static String URL = "http://localhost:8888";
    private static String Path = "/cdd/room";
    private static Socket mSocket;

    /**
     * 建立连接，初始
     * @param token access_token=.....
     */
    public static void Connect(String token) {
        try {
            IO.Options opts = new IO.Options();
            opts.path = Path;
            opts.query = "auth_token=" + token.split("access_token=")[1];

            SetSocketOn(mSocket);
            mSocket = IO.socket(URL, opts);
        }
        catch (URISyntaxException e) {

        }
    }

    /**
     * 结束连接，退出游戏
     */
    public static void DisConnect() {
        mSocket.disconnect();
        SetSocketOff(mSocket);
    }

    ///////////////////////////////// emit /////////////////////////////////

    public static void EmitRegister(String UserName, String PlainPassWord) {
        mSocket.emit(SocketObjects.EmitEvent.Register, new SocketObjects.UserInfoParamObj(UserName, PlainPassWord));
    }

    public static void EmitLogin(String UserName, String PlainPassWord) {
        mSocket.emit(SocketObjects.EmitEvent.Login, new SocketObjects.UserInfoParamObj(UserName, PlainPassWord));
    }

    public static void EmitShowCard(@NonNull Card[] cards) {
        for (Card card : cards) {
            mSocket.emit(SocketObjects.EmitEvent.PlayCard, SocketObjects.getCardParamObj(card));
        }
    }

    ///////////////////////////////// listen /////////////////////////////////

    /**
     * 在连接之前设置监听
     * @param mSocket
     */
    private static void SetSocketOn(Socket mSocket) {
        mSocket.on(SocketObjects.RcvdEvent.Prepare, onNewMessage);
    }

    /**
     * 在断开之后取消监听
     * @param mSocket
     */
    private static void SetSocketOff(Socket mSocket) {
        mSocket.off(SocketObjects.RcvdEvent.Prepare, onNewMessage);
    }

    private static Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];
            try {
                data.getString("xxx");
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
