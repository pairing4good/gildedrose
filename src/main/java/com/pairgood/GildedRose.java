package com.pairgood;

import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import java.util.List;

import static com.pairgood.Names.BACKSTAGE_PASSES;

public class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";

    private QualityUtil qualityUtil;
    private MathUtil mathUtil;
    private StringUtil stringUtil;
    private List<QualityRule> rules;

    public GildedRose(QualityUtil qualityUtil, MathUtil mathUtil, StringUtil stringUtil, List<QualityRule> rules) {
        this.qualityUtil = qualityUtil;
        this.mathUtil = mathUtil;
        this.stringUtil = stringUtil;
        this.rules = rules;
    }

    public void updateQuality(Item[] items) {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            String name = item.getName();

            for(QualityRule rule : rules){
                item.setQuality(rule.run(item));
            }

            int quality = item.getQuality();
            int sellIn = item.getSellIn();

            if (stringUtil.matches(name, AGED_BRIE, BACKSTAGE_PASSES)) {
                quality = qualityUtil.increaseQuality(quality);
            }
            if (stringUtil.matches(name, AGED_BRIE) && sellIn < 0) {
                quality = qualityUtil.increaseQuality(quality);
            }

            if (stringUtil.notMatch(name, AGED_BRIE, BACKSTAGE_PASSES)) {
                quality = qualityUtil.decreaseQuality(quality);
            }

            if(stringUtil.notMatch(name, AGED_BRIE, BACKSTAGE_PASSES, SULFURAS_HAND_OF_RAGNAROS) && sellIn < 0){
                quality = qualityUtil.decreaseQuality(quality);
            }

            if (stringUtil.notMatch(name, SULFURAS_HAND_OF_RAGNAROS)) {
                sellIn = mathUtil.decreaseByOne(sellIn);
            }

            if (stringUtil.matches(name, BACKSTAGE_PASSES) && sellIn < 0) {
                quality = mathUtil.zeroOut(quality);
            }

            item.setQuality(quality);
            item.setSellIn(sellIn);
        }
    }
}