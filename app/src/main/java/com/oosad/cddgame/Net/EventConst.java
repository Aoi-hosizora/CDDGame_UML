package com.oosad.cddgame.Net;

class EventConst {

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
         * Param: PlayCardParamObj[]
         */
        static final String PlayCard = "Play Card";
        /**
         * No param
         */
        static final String CanclePrepare = "Cancle Prepare";
    }
}
