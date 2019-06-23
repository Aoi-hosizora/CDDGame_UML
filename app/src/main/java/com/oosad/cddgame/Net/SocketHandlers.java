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
import java.util.List;

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

            ShowLogE("Connect", opts.query);
            mSocket = IO.socket(URL, opts);
            SetSocketOn(mSocket);
            mSocket.connect();
        }
        catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 结束连接，退出游戏
     */
    public static void DisConnect() {
        if (mSocket != null) {
            SetSocketOff(mSocket);
            mSocket.disconnect();
        }
    }

    ///////////////////////////////// emit /////////////////////////////////

    public static void EmitPrepare() {
        mSocket.emit(EventConst.EmitEvent.Prepare);
    }

    public static void EmitCanclePrepare() {
        mSocket.emit(EventConst.EmitEvent.CanclePrepare);
    }

    /**
     * 以序列化 PlayCardObj[] 发送
     * @param cards
     */
    public static void EmitShowCard(Card[] cards) {
        ShowLogE("", "cards: " + cards.length);

        // 考虑了 cards == null
        PlayCardObj[] playCardObjs = PlayCardObj.toPlayCardObjArray(cards);

        ShowLogE("", "playCardObjs: " + playCardObjs.length);

        try {

            String cardsStr = "[";

            if (playCardObjs.length != 0) {
                for (int i = 0; i < playCardObjs.length; i ++)
                    cardsStr += playCardObjs[i].toString() + ", ";

                cardsStr = cardsStr.substring(0, cardsStr.length() - 2);
            }

            cardsStr += "]";

//            String cardsStr = new JSONArray(cardJsons).toString();
            // [null,null]
            // JSON.stringify([{"number":3,"type":"RED DIAMOND"}, ...])

            ShowLogE("EmitShowCard", cardsStr);

            mSocket.emit(EventConst.EmitEvent.PlayCard, cardsStr);
        }
        catch (/*JSONException*/ Exception ex) {
            ex.printStackTrace();
        }
    }

    ///////////////////////////////// listen /////////////////////////////////

    /**
     * 在连接之后设置监听
     * @param mSocket
     */
    private static void SetSocketOn(Socket mSocket) {
        mSocket.on(EventConst.RcvdEvent.Waiting, onWaiting);
        mSocket.on(EventConst.RcvdEvent.Playing, onPlaying);
        mSocket.on(EventConst.RcvdEvent.End, onEnd);
    }

    /**
     * 在断开之前取消监听
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

            try {
                JSONObject data = new JSONObject((String) args[0]);
                ShowLogE("Waiting", (String) args[0]);

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
     * Param: PlayerRoomInfo, PlayerCards[]
     */
    private static Emitter.Listener onPlaying = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            try {
                JSONObject jsonPlayerRoomInfo = new JSONObject((String) args[0]);
                JSONArray jsonPlayerCards = new JSONArray((String) args[1]);

                ShowLogE("Playing", (String) args[0]);

                PlayerRoomInfoObj playerRoomInfo =
                        PlayerRoomInfoObj.toPlayerRoomInfoObj(jsonPlayerRoomInfo);

                PlayCardObj[] playCard =
                        PlayCardObj.toPlayCardObjArray(jsonPlayerCards);

                Card[] card = PlayCardObj.toCardArr(playCard);

                if (mOnPlayingListener != null)
                    mOnPlayingListener.onPlaying(playerRoomInfo, card);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Param: PlayerRoomInfo, PlayerCards[][]
     */
    private static Emitter.Listener onEnd = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            try {

                JSONObject jsonPlayerRoomInfo = new JSONObject((String) args[0]);
                JSONArray jsonPlayerCards = new JSONArray((String) args[1]);

                PlayerRoomInfoObj playerRoomInfo =
                        PlayerRoomInfoObj.toPlayerRoomInfoObj(jsonPlayerRoomInfo);

                List<PlayCardObj[]> playCards =
                        PlayCardObj.toPlayCardObj4Array(jsonPlayerCards);

                List<Card[]> cards = PlayCardObj.toCard4Array(playCards);

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
        void onPlaying(PlayerRoomInfoObj playerRoomInfo, Card[] card);
    }

    public interface onEndListener {
        void onEnd(PlayerRoomInfoObj playerRoomInfo, List<Card[]> cards);
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
