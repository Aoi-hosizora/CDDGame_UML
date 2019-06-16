package com.oosad.cddgame.Socket;

import com.oosad.cddgame.Data.Entity.Card;

public class SocketObjects {

    static class RcvdEvent {
        static String Prepare = "prepare";
    }

    static class EmitEvent {
        static String Login = "login";
        static String Register = "register";
        static String PlayCard = "play card";
    }

    static class PlayCardParamObj {

        private int number;
        private String type;

        PlayCardParamObj(int number, String type) {
            this.number = number;
            this.type = type;
        }

    }

    static class UserInfoParamObj {

        private String UserName;
        private String PassWord;

        UserInfoParamObj(String UserName, String PassWord) {
            this.UserName = UserName;
            this.PassWord = PassWord;
        }

    }

    static PlayCardParamObj getCardParamObj(Card card) {
        String type;
        switch (card.getCardSuit()) {
            case Diamond:
                type = "";
                break;
            case Club:
                type = "";
                break;
            case Heart:
                type = "";
                break;
            default: // Spade
                type = "";
                break;
        }
        return new PlayCardParamObj(card.getCardNum(), type);
    }
}
