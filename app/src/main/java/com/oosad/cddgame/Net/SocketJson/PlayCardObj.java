package com.oosad.cddgame.Net.SocketJson;

import android.support.annotation.Nullable;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Entity.CardSuit;
import com.oosad.cddgame.Net.JsonConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayCardObj implements Serializable {
    /**
     * 1 - 12
     */
    private int number;
    /**
     * PlayCardType:
     * "BLACK SPADE" || "BLACK CLUB" || "RED HEART" || "RED DIAMOND"
     */
    private String type;

    @Override
    public String toString() {
        return String.format(Locale.CHINA,"{\"number\":%d,\"type\":\"%s\"}", number, type);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlayCardObj(int number, String type) {
        this.number = number;
        this.type = type;
    }

    /**
     * Card -> PlayCardObj
     * @param card
     * @return
     */
    public static PlayCardObj toPlayCardObj(Card card) {
        String type;
        switch (card.getCardSuit()) {
            case Spade:
                type = JsonConst.PlayCardType.Spade;
                break;
            case Heart:
                type = JsonConst.PlayCardType.Heart;
                break;
            case Club:
                type = JsonConst.PlayCardType.Club;
                break;
            default: // Diamond
                type = JsonConst.PlayCardType.Diamond;
                break;
        }
        return new PlayCardObj(card.getCardNum(), type);
    }

    /**
     * PlayCardObj -> Card
     * @param playCard
     * @return
     */
    public static Card toCard(PlayCardObj playCard) {
        CardSuit cardSuit;
        switch (playCard.type) {
            case JsonConst.PlayCardType.Spade:
                cardSuit = CardSuit.Spade;
                break;
            case JsonConst.PlayCardType.Heart:
                cardSuit = CardSuit.Heart;
                break;
            case JsonConst.PlayCardType.Club:
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
    public static PlayCardObj toPlayCardObj(JSONObject jsonObject) throws JSONException {
        return new PlayCardObj(
                jsonObject.getInt("number"),
                jsonObject.getString("type")
        );
    }

    /**
     * JSONArray -> PlayCardObj[]
     * [{number: 1, type: "BLACK SPADE" }, {number: 1, type: "BLACK SPADE" }]
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    public static PlayCardObj[] toPlayCardObjArray(JSONArray jsonArray) throws JSONException {
        PlayCardObj[] playCardObjs = new PlayCardObj[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i ++)
            playCardObjs[i] = PlayCardObj.toPlayCardObj(jsonArray.getJSONObject(i));
        return playCardObjs;
    }

    /**
     * Card[] -> PlayCardObj[]
     * @param cards
     * @return
     */
    public static PlayCardObj[] toPlayCardObjArray(Card[] cards) {
        if (cards == null)
            return new PlayCardObj[]{};
        PlayCardObj[] playCardObjs = new PlayCardObj[cards.length];
        for (int i = 0; i < cards.length; i++)
            playCardObjs[i] = toPlayCardObj(cards[i]);

        return playCardObjs;
    }

    /**
     * PlayCardObj[] -> Card[]
     * @param playCardObjs
     * @return
     */
    public static Card[] toCardArr(PlayCardObj[] playCardObjs) {
        if (playCardObjs == null || playCardObjs.length == 0)
            return new Card[]{};
        Card[] cards = new Card[playCardObjs.length];
        for (int i = 0; i < playCardObjs.length; i++) {
            cards[i] = PlayCardObj.toCard(playCardObjs[i]);
        }
        return cards;
    }

    /**
     * JSONArray(PlayCardObj[][]) -> List<PlayCardObj[]>
     * @param jsonArrays
     * @return
     * @throws JSONException
     */
    public static List<PlayCardObj[]> toPlayCardObj4Array(JSONArray jsonArrays) throws JSONException {
        List<PlayCardObj[]> playCardObjsList = new ArrayList<>();
        for (int i = 0; i < jsonArrays.length(); i++) {
            JSONArray jsonArray = (JSONArray) jsonArrays.get(i);
            PlayCardObj[] playCardObjs = toPlayCardObjArray(jsonArray);
            playCardObjsList.add(playCardObjs);
        }

        return playCardObjsList;
    }

    /**
     * List<PlayCardObj[]> -> List<Card[]>
     * @param playCardObjsList
     * @return
     */
    public static List<Card[]> toCard4Array(List<PlayCardObj[]> playCardObjsList) {
        List<Card[]> cardsList = new ArrayList<>();
        for (int i = 0; i < playCardObjsList.size(); i++) {
            Card[] cards = toCardArr(playCardObjsList.get(i));
            cardsList.add(cards);
        }
        return cardsList;
    }

}
