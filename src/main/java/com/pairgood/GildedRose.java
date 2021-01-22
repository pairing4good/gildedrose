package com.pairgood;

import com.pairgood.rule.quality.QualityRule;
import com.pairgood.rule.sellin.SellInRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.pairgood.Names.*;

public class GildedRose {

    private MathUtil mathUtil;
    private StringUtil stringUtil;
    private List<QualityRule> qualityRules;
    private List<SellInRule> sellInRules;

    public GildedRose(MathUtil mathUtil, StringUtil stringUtil, List<QualityRule> qualityRules,
                      List<SellInRule> sellInRules) {
        this.mathUtil = mathUtil;
        this.stringUtil = stringUtil;
        this.qualityRules = qualityRules;
        this.sellInRules = sellInRules;
    }

    public Item[] updateQuality(Item[] items) {
        List<Item> updatedItems = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            Item existingItem = items[i];
            String name = existingItem.getName();

            for(SellInRule rule : sellInRules){
                existingItem.setSellIn(rule.run(existingItem));
            }

            for(QualityRule rule : qualityRules){
                existingItem.setQuality(rule.run(existingItem));
            }

            int quality = existingItem.getQuality();
            int sellIn = existingItem.getSellIn();

            if (stringUtil.matches(name, BACKSTAGE_PASSES) && sellIn < 0) {
                quality = mathUtil.zeroOut(quality);
            }

            updatedItems.add(new Item(name, sellIn, quality));
        }

        return updatedItems.toArray(new Item[]{});
    }
}