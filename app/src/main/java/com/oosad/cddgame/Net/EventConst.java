package com.oosad.cddgame.Net;

public class EventConst {

    static class RcvdEvent {
        /**
         * Param: PlayerRoomInfoObj
         */
        public static final String Waiting = "WAITING";
        /**
         * Param: PlayerRoomInfoObj, PlayCardObj[]
         */
        public static final String Playing = "PLAYING";
        /**
         * Param: PlayerRoomInfoObj, PlayCardObj[][]
         */
        public static final String End = "END";
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
}
