package com.oosad.cddgame.Socket;

import android.support.annotation.NonNull;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Entity.CardSuit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketJsonObjs {

    static class PlayCardObj {
        /**
         * 1 - 12
         */
        int number;
        /**
         * PlayCardType:
         * "BLACK SPADE" || "BLACK CLUB" || "RED HEART" || "RED DIAMOND"
         */
        String type;

        PlayCardObj(int number, String type) {
            this.number = number;
            this.type = type;
        }

        /**
         * Card -> PlayCardObj
         * @param card
         * @return
         */
        static PlayCardObj toPlayCardObj(Card card) {
            String type;
            switch (card.getCardSuit()) {
                case Spade:
                    type = SocketConst.PlayCardType.Spade;
                break;
                case Heart:
                    type = SocketConst.PlayCardType.Heart;
                break;
                case Club:
                    type = SocketConst.PlayCardType.Club;
                break;
                default: // Diamond
                    type = SocketConst.PlayCardType.Diamond;
                break;
            }
            return new PlayCardObj(card.getCardNum(), type);
        }

        /**
         * PlayCardObj -> Card
         * @param playCard
         * @return
         */
        static Card toCard(PlayCardObj playCard) {
            CardSuit cardSuit;
            switch (playCard.type) {
                case SocketConst.PlayCardType.Spade:
                    cardSuit = CardSuit.Spade;
                break;
                case SocketConst.PlayCardType.Heart:
                    cardSuit = CardSuit.Heart;
                break;
                case SocketConst.PlayCardType.Club:
                    cardSuit = CardSuit.Club;
                break;
                default: //Diamond
                    cardSuit = CardSuit.Diamond;
                break;
            }
            return new Card(playCard.number, cardSuit);
        }

        /**
         * JSONObject -> PlayCardObj
         * {number: 1, type: "BLACK SPADE" }
         * @param jsonObject
         * @return
         * @throws JSONException
         */
        static PlayCardObj toPlayCardObj(JSONObject jsonObject) throws JSONException {
            return new PlayCardObj(
                    jsonObject.getInt("number"),
                    jsonObject.getString("type")
            );
        }

        /**
         * JSONArray -> PlayCardObj
         * [{number: 1, type: "BLACK SPADE" }]
         * @param jsonArray
         * @return
         * @throws JSONException
         */
        static PlayCardObj toPlayCardObj(JSONArray jsonArray) throws JSONException {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            return PlayCardObj.toPlayCardObj(jsonObject);
        }

        /**
         * JSONArray -> PlayCardObj[]
         * [{number: 1, type: "BLACK SPADE" }, {number: 1, type: "BLACK SPADE" }]
         * @param jsonArray
         * @return
         * @throws JSONException
         */
        static PlayCardObj[] toPlayCardObjArray(JSONArray jsonArray) throws JSONException {
            PlayCardObj[] playCardObjs = new PlayCardObj[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i ++)
                playCardObjs[i] = PlayCardObj.toPlayCardObj(jsonArray.getJSONObject(i));
            return playCardObjs;
        }

        /**
         * PlayCardObj[] -> Card[]
         * @param playCardObjs
         * @return
         */
        static Card[] toCardArr(PlayCardObj[] playCardObjs) {
            Card[] cards = new Card[playCardObjs.length];
            for (int i = 0; i < playCardObjs.length; i++) {
                cards[i] = PlayCardObj.toCard(playCardObjs[i]);
            }
            return cards;
        }
     }

    static class PlayerObj {
        String username;
        /**
         * PlayerStatus:
         * "Not Prepare" || "Prepare"
         */
        String status;

        PlayerObj(String username, String status) {
            this.username = username;
            this.status = status;
        }

        static PlayerObj toPlayerObj(JSONObject jsonObject) throws JSONException {
            return new PlayerObj(
                    jsonObject.getString("username"),
                    jsonObject.getString("status")
            );
        }
    }

    static class PlayerRoomInfoObj {
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

        PlayerRoomInfoObj(String room_number, PlayerObj[] players, String status, int current, PlayCardObj[] precard, int prePlayer, int rest_second) {
            this.room_number = room_number;
            this.players = players;
            this.status = status;
            this.current = current;
            this.precard = precard;
            this.prePlayer = prePlayer;
            this.rest_second = rest_second;
        }

        static PlayerRoomInfoObj toPlayerRoomInfoObj(JSONObject jsonObject) throws JSONException {

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
}
