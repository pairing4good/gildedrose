package com.pairgood.rule.quality.decrease;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.StringUtil;

import static com.pairgood.Names.BACKSTAGE_PASSES;

public class BackstagePassSellInLessThanZeroRule implements QualityRule {

    private StringUtil stringUtil;
    private MathUtil mathUtil;

    public BackstagePassSellInLessThanZeroRule(StringUtil stringUtil, MathUtil mathUtil) {
        this.stringUtil = stringUtil;
        this.mathUtil = mathUtil;
    }


    @Override
    public int run(Item item) {
        if (stringUtil.matches(item.getName(), BACKSTAGE_PASSES) && item.getSellIn() < 0) {
            return mathUtil.zeroOut(item.getQuality());
        }

        return item.getQuality();
    }
}
