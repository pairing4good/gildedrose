package com.pairgood.rule.quality.decrease;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import static com.pairgood.Names.AGED_BRIE;
import static com.pairgood.Names.BACKSTAGE_PASSES;

public class NotAgedBrieOrBackstagePassesRule implements QualityRule {

    private StringUtil stringUtil;
    private QualityUtil qualityUtil;

    public NotAgedBrieOrBackstagePassesRule(StringUtil stringUtil, QualityUtil qualityUtil) {
        this.stringUtil = stringUtil;
        this.qualityUtil = qualityUtil;
    }


    @Override
    public int run(Item item) {
        if (stringUtil.notMatch(item.getName(), AGED_BRIE, BACKSTAGE_PASSES)) {
            return qualityUtil.decreaseQuality(item.getQuality());
        }

        return item.getQuality();
    }
}
