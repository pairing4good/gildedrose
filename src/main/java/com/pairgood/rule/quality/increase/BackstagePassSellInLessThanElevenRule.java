package com.pairgood.rule.quality.increase;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import static com.pairgood.Names.BACKSTAGE_PASSES;

public class BackstagePassSellInLessThanElevenRule implements QualityRule {

    private StringUtil stringUtil;
    private QualityUtil qualityUtil;

    public BackstagePassSellInLessThanElevenRule(StringUtil stringUtil, QualityUtil qualityUtil) {
        this.stringUtil = stringUtil;
        this.qualityUtil = qualityUtil;
    }

    public int run(Item item) {
        if (stringUtil.matches(item.getName(), BACKSTAGE_PASSES) && item.getSellIn() < 11) {
            return qualityUtil.increaseQuality(item.getQuality());
        }

        return item.getQuality();
    }
}
