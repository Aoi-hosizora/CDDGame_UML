package com.oosad.cddgame.Data.Rules;

import com.oosad.cddgame.Data.Entity.Card;

public interface RuleCheckAdapter {

    boolean checkShowCardRule(Card[] upcards, Card[] showcards);
}
