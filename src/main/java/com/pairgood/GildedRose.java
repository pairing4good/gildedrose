package com.pairgood;

import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.pairgood.Names.*;

public class GildedRose {

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

    public Item[] updateQuality(Item[] items) {
        List<Item> updatedItems = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            Item existingItem = items[i];
            String name = existingItem.getName();

            for(QualityRule rule : rules){
                existingItem.setQuality(rule.run(existingItem));
            }

            int quality = existingItem.getQuality();
            int sellIn = existingItem.getSellIn();



            if (stringUtil.notMatch(name, SULFURAS_HAND_OF_RAGNAROS)) {
                sellIn = mathUtil.decreaseByOne(sellIn);
            }

            if (stringUtil.matches(name, BACKSTAGE_PASSES) && sellIn < 0) {
                quality = mathUtil.zeroOut(quality);
            }

            updatedItems.add(new Item(name, sellIn, quality));
        }

        return updatedItems.toArray(new Item[]{});
    }
}