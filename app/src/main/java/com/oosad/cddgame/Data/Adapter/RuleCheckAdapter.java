package com.oosad.cddgame.Data.Adapter;

import com.oosad.cddgame.Data.Entity.Card;

public interface RuleCheckAdapter {

    boolean checkShowCardRule(Card[] upcards, Card[] showcards);
}
