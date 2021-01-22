package com.pairgood.rule.quality.increase;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import static com.pairgood.Names.AGED_BRIE;
import static com.pairgood.Names.BACKSTAGE_PASSES;

public class AgedBrieOrBackstagePassesRule implements QualityRule {

    private StringUtil stringUtil;
    private QualityUtil qualityUtil;

    public AgedBrieOrBackstagePassesRule(StringUtil stringUtil, QualityUtil qualityUtil){
        this.stringUtil = stringUtil;
        this.qualityUtil = qualityUtil;
    }

    @Override
    public int run(Item item) {
        if (stringUtil.matches(item.getName(), AGED_BRIE, BACKSTAGE_PASSES)) {
            return qualityUtil.increaseQuality(item.getQuality());
        }
        return item.getQuality();
    }
}
