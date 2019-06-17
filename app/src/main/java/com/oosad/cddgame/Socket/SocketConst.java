package com.oosad.cddgame.Socket;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Data.Entity.CardSuit;

public class SocketConst {

    static class RcvdEvent {
        /**
         * Param: PlayerRoomInfoObj
         */
        static final String Waiting = "WAITING";
        /**
         * Param: PlayerRoomInfoObj, PlayCardObj[]
         */
        static final String Playing = "PLAYING";
        /**
         * Param: PlayerRoomInfoObj, PlayCardObj[][]
         */
        static final String End = "END";
    }

    static class EmitEvent {
        /**
         * No param
         */
        static final String Prepare = "Prepare";
        /**
         * No param
         */
        static final String PlayCard = "Play Card";
        /**
         * Param: PlayCardParamObj[]
         */
        static final String CanclePrepare = "Cancle Prepare";
    }

    static class PlayerStatus {
        static final String NotPrepare = "Not Prepare";
        static final String Prepare = "Prepare";
    }

    static class PlayerInRoomStatus {
        static final String Waiting = "WAITING";
        static final String Playing = "PLAYING";
    }

    static class PlayCardType {
        static final String Spade = "BLACK SPADE";
        static final String Club = "BLACK CLUB";
        static final String Heart = "RED HEART";
        static final String Diamond = "RED DIAMOND";
    }
}
