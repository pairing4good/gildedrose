package com.pairgood;

import com.pairgood.rule.quality.QualityRule;
import com.pairgood.rule.quality.decrease.BackstagePassSellInLessThanZeroRule;
import com.pairgood.rule.quality.decrease.NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRule;
import com.pairgood.rule.quality.decrease.NotAgedBrieOrBackstagePassesRule;
import com.pairgood.rule.quality.increase.AgedBrieOrBackstagePassesRule;
import com.pairgood.rule.quality.increase.AgedBrieSellInLessThanZeroRule;
import com.pairgood.rule.quality.increase.BackstagePassSellInLessThanElevenRule;
import com.pairgood.rule.quality.increase.BackstagePassSellInLessThanSixRule;
import com.pairgood.rule.sellin.SellInRule;
import com.pairgood.rule.sellin.decrease.NotSulfurasHandOfRagnorosRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class GildedRoseFactory {

    public GildedRose create() {
        MathUtil mathUtil = new MathUtil();
        StringUtil stringUtil = new StringUtil();
        QualityUtil qualityUtil = new QualityUtil(mathUtil);

        List<QualityRule> qualityRules = new ArrayList<>();
        qualityRules.add(new BackstagePassSellInLessThanElevenRule(stringUtil, qualityUtil));
        qualityRules.add(new BackstagePassSellInLessThanSixRule(stringUtil, qualityUtil));
        qualityRules.add(new AgedBrieOrBackstagePassesRule(stringUtil, qualityUtil));
        qualityRules.add(new AgedBrieSellInLessThanZeroRule(stringUtil, qualityUtil));
        qualityRules.add(new NotAgedBrieOrBackstagePassesRule(stringUtil, qualityUtil));
        qualityRules.add(new NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRule(
                stringUtil, qualityUtil));
        qualityRules.add(new BackstagePassSellInLessThanZeroRule(stringUtil, mathUtil));

        List<SellInRule> sellInRules = new ArrayList<>();
        sellInRules.add(new NotSulfurasHandOfRagnorosRule(stringUtil, mathUtil));

        return new GildedRose(qualityRules, sellInRules);
    }
}
