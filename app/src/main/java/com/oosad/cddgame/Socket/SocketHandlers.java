package com.oosad.cddgame.Socket;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class SocketHandlers {

    private static void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "SocketHandlers";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    private static String RegisterURL = "http://api.gajon.xyz:8888/cdd/user/register";
    private static String LoginURL = "http://api.gajon.xyz:8888/cdd/user/login";

    private static String URL = "http://api.gajon.xyz:8888";
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
            opts.query = "token=" + token.split(" ")[1];

            SetSocketOn(mSocket);
            mSocket = IO.socket(URL, opts);
        }
        catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 结束连接，退出游戏
     */
    public static void DisConnect() {
        mSocket.disconnect();
        SetSocketOff(mSocket);
    }

    ///////////////////////////////// post /////////////////////////////////

    private static String PostHandler(String Url, String UserName, String PassWord) {
        JSONObject UserPassInfo;
        try {
            UserPassInfo = new JSONObject();
            UserPassInfo.put("username", UserName);
            UserPassInfo.put("password", PassWord);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }

        String resp;
        try {
            resp = HttpUtil.HttpPost(new URL(Url), UserPassInfo.toString());
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
            return "";
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }

        return resp;
    }

    public static boolean PostLogin(String UserName, String PassWord) {
        String ret = PostHandler(LoginURL, UserName, PassWord);
        ShowLogE("PostLogin", "RET: " + ret);
        return true;
    }

    public static boolean PostReegister(String UserName, String PassWord) {
        String ret = PostHandler(RegisterURL, UserName, PassWord);
        ShowLogE("PostLogin", "RET: " + ret);
        return true;
    }

    ///////////////////////////////// emit /////////////////////////////////

    public static void EmitPrepare() {
        mSocket.emit(SocketConst.EmitEvent.Prepare);
    }

    public static void EmitCanclePrepare() {
        mSocket.emit(SocketConst.EmitEvent.CanclePrepare);
    }

    public static void EmitShowCard(Card[] cards) {
        SocketJsonObjs.PlayCardObj[] playCardObjs = new SocketJsonObjs.PlayCardObj[cards.length];
        for (int i = 0; i < cards.length; i++) {
            playCardObjs[i] = SocketJsonObjs.PlayCardObj.toPlayCardObj(cards[i]);
        }
        mSocket.emit(SocketConst.EmitEvent.PlayCard, playCardObjs);
    }

    ///////////////////////////////// listen /////////////////////////////////

    /**
     * 在连接之前设置监听
     * @param mSocket
     */
    private static void SetSocketOn(Socket mSocket) {
        mSocket.on(SocketConst.RcvdEvent.Waiting, onWaiting);
        mSocket.on(SocketConst.RcvdEvent.Playing, onPlaying);
        mSocket.on(SocketConst.RcvdEvent.End, onEnd);
    }

    /**
     * 在断开之后取消监听
     * @param mSocket
     */
    private static void SetSocketOff(Socket mSocket) {
        mSocket.off(SocketConst.RcvdEvent.Waiting, onWaiting);
        mSocket.off(SocketConst.RcvdEvent.Playing, onPlaying);
        mSocket.off(SocketConst.RcvdEvent.End, onEnd);
    }

    /**
     * Param: PlayerRoomInfo
     */
    private static Emitter.Listener onWaiting = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];

            try {
                SocketJsonObjs.PlayerRoomInfoObj playerRoomInfo =
                        SocketJsonObjs.PlayerRoomInfoObj.toPlayerRoomInfoObj(data);

                if (mOnWaitingListener != null)
                    mOnWaitingListener.onWaiting(playerRoomInfo);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Param: PlayerRoomInfo, PlayerCards
     */
    private static Emitter.Listener onPlaying = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject jsonPlayerRoomInfo = (JSONObject) args[0];
            JSONArray jsonPlayerCards = (JSONArray) args[1];

            try {
                SocketJsonObjs.PlayerRoomInfoObj playerRoomInfo =
                        SocketJsonObjs.PlayerRoomInfoObj.toPlayerRoomInfoObj(jsonPlayerRoomInfo);

                SocketJsonObjs.PlayCardObj playCard =
                        SocketJsonObjs.PlayCardObj.toPlayCardObj(jsonPlayerCards);

                Card card = SocketJsonObjs.PlayCardObj.toCard(playCard);

                if (mOnPlayingListener != null)
                    mOnPlayingListener.onPlaying(playerRoomInfo, card);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * Param: PlayerRoomInfo, PlayerCards
     */
    private static Emitter.Listener onEnd = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject jsonPlayerRoomInfo = (JSONObject) args[0];
            JSONArray jsonPlayerCards = (JSONArray) args[1];

            try {
                SocketJsonObjs.PlayerRoomInfoObj playerRoomInfo =
                        SocketJsonObjs.PlayerRoomInfoObj.toPlayerRoomInfoObj(jsonPlayerRoomInfo);

                SocketJsonObjs.PlayCardObj[] playCards =
                        SocketJsonObjs.PlayCardObj.toPlayCardObjArray(jsonPlayerCards);

                Card[] cards = SocketJsonObjs.PlayCardObj.toCardArr(playCards);

                if (mOnEndListener != null)
                    mOnEndListener.onEnd(playerRoomInfo, cards);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    ///////////////////////////////// listener interface /////////////////////////////////

    public interface onWaitingListener {
        void onWaiting(SocketJsonObjs.PlayerRoomInfoObj playerRoomInfo);
    }

    public interface onPlayingListener {
        void onPlaying(SocketJsonObjs.PlayerRoomInfoObj playerRoomInfo, Card card);
    }

    public interface onEndListener {
        void onEnd(SocketJsonObjs.PlayerRoomInfoObj playerRoomInfo, Card[] cards);
    }

    private static onWaitingListener mOnWaitingListener;
    private static onPlayingListener mOnPlayingListener;
    private static onEndListener mOnEndListener;

    public static void setmOnEndListener(onEndListener mOnEndListener) {
        SocketHandlers.mOnEndListener = mOnEndListener;
    }

    public static void setmOnPlayingListener(onPlayingListener mOnPlayingListener) {
        SocketHandlers.mOnPlayingListener = mOnPlayingListener;
    }

    public static void setmOnWaitingListener(onWaitingListener mOnWaitingListener) {
        SocketHandlers.mOnWaitingListener = mOnWaitingListener;
    }
}
