package com.pairgood.rule.quality.decrease;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import static com.pairgood.Names.*;

public class NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRule implements QualityRule {

    private StringUtil stringUtil;
    private QualityUtil qualityUtil;

    public NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRule(StringUtil stringUtil,
                                                                                         QualityUtil qualityUtil){
        this.stringUtil = stringUtil;
        this.qualityUtil = qualityUtil;
    }


    @Override
    public int run(Item item) {
        if(stringUtil.notMatch(item.getName(), AGED_BRIE, BACKSTAGE_PASSES, SULFURAS_HAND_OF_RAGNAROS) &&
                item.getSellIn() < 0){
            return qualityUtil.decreaseQuality(item.getQuality());
        }

        return item.getQuality();
    }
}
