package com.pairgood;

import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

public class GildedRose {
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";

    private Item[] items;
    private QualityUtil qualityUtil;
    private MathUtil mathUtil;
    private StringUtil stringUtil;

    public GildedRose(Item[] items, QualityUtil qualityUtil, MathUtil mathUtil, StringUtil stringUtil) {
        this.items = items;
        this.qualityUtil = qualityUtil;
        this.mathUtil = mathUtil;
        this.stringUtil = stringUtil;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            String name = items[i].getName();
            int quality = items[i].getQuality();
            int sellIn = items[i].getSellIn();

            if (stringUtil.matches(name, BACKSTAGE_PASSES) && sellIn < 11) {
                quality = qualityUtil.increaseQuality(quality);
            }

            if (stringUtil.matches(name, BACKSTAGE_PASSES) && sellIn < 6) {
                quality = qualityUtil.increaseQuality(quality);
            }

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

            items[i].setQuality(quality);
            items[i].setSellIn(sellIn);
        }
    }
}