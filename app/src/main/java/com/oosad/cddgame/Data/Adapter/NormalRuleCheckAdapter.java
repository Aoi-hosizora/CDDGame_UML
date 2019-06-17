package com.oosad.cddgame.Data.Adapter;

import android.util.Log;

import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Util.RuleUtil;

public class NormalRuleCheckAdapter implements RuleCheckAdapter {
    /**
     * SG/OG
     * 调用规则模块 待改
     * @param showcards 为 null 表示跳过
     * @return
     */
    @Override
    public boolean checkShowCardRule(Card[] upcards, Card[] showcards) {
        boolean ret = RuleUtil.judgement(upcards, showcards);
        Log.e("TAG", "checkShowCardThroughRule: " + ret);

        // TODO
        return true;
    }
}
