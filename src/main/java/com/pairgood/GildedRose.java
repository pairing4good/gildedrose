package com.pairgood;

import com.pairgood.rule.quality.QualityRule;
import com.pairgood.rule.sellin.SellInRule;

import java.util.ArrayList;
import java.util.List;

public class GildedRose {
    private List<QualityRule> qualityRules;
    private List<SellInRule> sellInRules;

    public GildedRose(List<QualityRule> qualityRules, List<SellInRule> sellInRules) {
        this.qualityRules = qualityRules;
        this.sellInRules = sellInRules;
    }

    public Item[] updateQuality(Item[] items) {
        List<Item> updatedItems = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            Item existingItem = items[i];
            String name = existingItem.getName();

            for (SellInRule rule : sellInRules) {
                existingItem.setSellIn(rule.run(existingItem));
            }

            for (QualityRule rule : qualityRules) {
                existingItem.setQuality(rule.run(existingItem));
            }

            updatedItems.add(new Item(name, existingItem.getSellIn(), existingItem.getQuality()));
        }

        return updatedItems.toArray(new Item[]{});
    }
}