package com.pairgood;

public class GildedRose {
    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            String name = items[i].name;
            int quality = items[i].quality;
            int sellIn = items[i].sellIn;

            if (matches(name, BACKSTAGE_PASSES) && sellIn < 11) {
                quality = increaseQuality(quality);
            }

            if (matches(name, BACKSTAGE_PASSES) && sellIn < 6) {
                quality = increaseQuality(quality);
            }

            if (matches(name, AGED_BRIE, BACKSTAGE_PASSES)) {
                quality = increaseQuality(quality);
            }
            if (matches(name, AGED_BRIE) && sellIn < 0) {
                quality = increaseQuality(quality);
            }

            if (notMatch(name, AGED_BRIE, BACKSTAGE_PASSES)) {
                quality = decreaseQuality(quality);
            }

            if(notMatch(name, AGED_BRIE, BACKSTAGE_PASSES, SULFURAS_HAND_OF_RAGNAROS) && sellIn < 0){
                quality = decreaseQuality(quality);
            }

            if (notMatch(name, SULFURAS_HAND_OF_RAGNAROS)) {
                sellIn = decreaseByOne(sellIn);
            }

            if (matches(name, BACKSTAGE_PASSES) && sellIn < 0) {
                quality = zeroOut(quality);
            }

            items[i].quality = quality;
            items[i].sellIn = sellIn;
        }
    }

    private boolean matches(String value, String... patterns){
        boolean answer = false;

        for(String pattern : patterns){
            if(value.equals(pattern)){
                answer = true;
            }
        }

        return answer;
    }

    private boolean notMatch(String value, String... patterns){
        return !matches(value, patterns);
    }

    private int increaseQuality(int quality) {
        if (isLessThanMax(quality)) {
            quality = increaseByOne(quality);
        }
        return quality;
    }

    private int decreaseQuality(int quality) {
        if (isGreaterThanMin(quality)) {
            quality = decreaseByOne(quality);
        }
        return quality;
    }

    private boolean isLessThanMax(int value) {
        return value < MAX_QUALITY;
    }

    private boolean isGreaterThanMin(int value) {
        return value > MIN_QUALITY;
    }

    private int zeroOut(int value) {
        return value - value;
    }

    private int increaseByOne(int value) {
        return value + 1;
    }

    private int decreaseByOne(int value) {
        return value - 1;
    }
}