package com.pairgood;

import com.pairgood.rule.quality.QualityRule;
import com.pairgood.rule.quality.decrease.NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRule;
import com.pairgood.rule.quality.decrease.NotAgedBrieOrBackstagePassesRule;
import com.pairgood.rule.quality.increase.AgedBrieOrBackstagePassesRule;
import com.pairgood.rule.quality.increase.AgedBrieSellInLessThanZeroRule;
import com.pairgood.rule.quality.increase.BackstagePassSellInLessThanElevenRule;
import com.pairgood.rule.quality.increase.BackstagePassSellInLessThanSixRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class GildedRoseFactory {

    public GildedRose create(){
        MathUtil mathUtil = new MathUtil();
        StringUtil stringUtil = new StringUtil();
        QualityUtil qualityUtil = new QualityUtil(mathUtil);

        List<QualityRule> rules = new ArrayList<>();
        rules.add(new BackstagePassSellInLessThanElevenRule(stringUtil, qualityUtil));
        rules.add(new BackstagePassSellInLessThanSixRule(stringUtil, qualityUtil));
        rules.add(new AgedBrieOrBackstagePassesRule(stringUtil, qualityUtil));
        rules.add(new AgedBrieSellInLessThanZeroRule(stringUtil, qualityUtil));
        rules.add(new NotAgedBrieOrBackstagePassesRule(stringUtil, qualityUtil));
        rules.add(new NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRule(
                stringUtil, qualityUtil));

        return new GildedRose(qualityUtil, mathUtil, stringUtil, rules);
    }
}
