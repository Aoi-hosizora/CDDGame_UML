package com.oosad.cddgame.Net;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Net.SocketJson.PlayCardObj;
import com.oosad.cddgame.Net.SocketJson.PlayerRoomInfoObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketHandlers {

    private static void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "SocketHandlers";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

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

    ///////////////////////////////// emit /////////////////////////////////

    public static void EmitPrepare() {
        mSocket.emit(EventConst.EmitEvent.Prepare);
    }

    public static void EmitCanclePrepare() {
        mSocket.emit(EventConst.EmitEvent.CanclePrepare);
    }

    public static void EmitShowCard(Card[] cards) {
        PlayCardObj[] playCardObjs = new PlayCardObj[cards.length];
        for (int i = 0; i < cards.length; i++) {
            playCardObjs[i] = PlayCardObj.toPlayCardObj(cards[i]);
        }
        mSocket.emit(EventConst.EmitEvent.PlayCard, playCardObjs);
    }

    ///////////////////////////////// listen /////////////////////////////////

    /**
     * 在连接之前设置监听
     * @param mSocket
     */
    private static void SetSocketOn(Socket mSocket) {
        mSocket.on(EventConst.RcvdEvent.Waiting, onWaiting);
        mSocket.on(EventConst.RcvdEvent.Playing, onPlaying);
        mSocket.on(EventConst.RcvdEvent.End, onEnd);
    }

    /**
     * 在断开之后取消监听
     * @param mSocket
     */
    private static void SetSocketOff(Socket mSocket) {
        mSocket.off(EventConst.RcvdEvent.Waiting, onWaiting);
        mSocket.off(EventConst.RcvdEvent.Playing, onPlaying);
        mSocket.off(EventConst.RcvdEvent.End, onEnd);
    }

    /**
     * Param: PlayerRoomInfo
     */
    private static Emitter.Listener onWaiting = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];

            try {
                PlayerRoomInfoObj playerRoomInfo =
                        PlayerRoomInfoObj.toPlayerRoomInfoObj(data);

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
                PlayerRoomInfoObj playerRoomInfo =
                        PlayerRoomInfoObj.toPlayerRoomInfoObj(jsonPlayerRoomInfo);

                PlayCardObj playCard =
                        PlayCardObj.toPlayCardObj(jsonPlayerCards);

                Card card = PlayCardObj.toCard(playCard);

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
                PlayerRoomInfoObj playerRoomInfo =
                        PlayerRoomInfoObj.toPlayerRoomInfoObj(jsonPlayerRoomInfo);

                PlayCardObj[] playCards =
                        PlayCardObj.toPlayCardObjArray(jsonPlayerCards);

                Card[] cards = PlayCardObj.toCardArr(playCards);

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
        void onWaiting(PlayerRoomInfoObj playerRoomInfo);
    }

    public interface onPlayingListener {
        void onPlaying(PlayerRoomInfoObj playerRoomInfo, Card card);
    }

    public interface onEndListener {
        void onEnd(PlayerRoomInfoObj playerRoomInfo, Card[] cards);
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
