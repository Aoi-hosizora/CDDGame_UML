package com.oosad.cddgame.Net.SocketJson;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Entity.CardSuit;
import com.oosad.cddgame.Net.JsonConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayCardObj {
    /**
     * 1 - 12
     */
    int number;
    /**
     * PlayCardType:
     * "BLACK SPADE" || "BLACK CLUB" || "RED HEART" || "RED DIAMOND"
     */
    String type;

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
     * JSONArray -> PlayCardObj
     * [{number: 1, type: "BLACK SPADE" }]
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    public static PlayCardObj toPlayCardObj(JSONArray jsonArray) throws JSONException {
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
    public static PlayCardObj[] toPlayCardObjArray(JSONArray jsonArray) throws JSONException {
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
    public static Card[] toCardArr(PlayCardObj[] playCardObjs) {
        Card[] cards = new Card[playCardObjs.length];
        for (int i = 0; i < playCardObjs.length; i++) {
            cards[i] = PlayCardObj.toCard(playCardObjs[i]);
        }
        return cards;
    }
}
