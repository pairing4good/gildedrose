package com.pairgood;

public class GildedRose {
    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            String name = items[i].name;
            int quality = items[i].quality;
            int sellIn = items[i].sellIn;

            if (name.equals("Backstage passes to a TAFKAL80ETC concert") && sellIn < 11) {
                quality = increaseQuality(quality);
            }
            if (name.equals("Backstage passes to a TAFKAL80ETC concert") && sellIn < 6) {
                quality = increaseQuality(quality);
            }

            if (name.equals("Aged Brie") || name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                quality = increaseQuality(quality);
            }
            if (name.equals("Aged Brie") && sellIn < 0) {
                quality = increaseQuality(quality);
            }

            if (!name.equals("Aged Brie") && !name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                quality = decreaseByOne(quality);
            }

            if(!name.equals("Aged Brie") &&
                    !name.equals("Backstage passes to a TAFKAL80ETC concert") &&
                    !name.equals("Sulfuras, Hand of Ragnaros") &&
                    sellIn < 0){
                quality = decreaseByOne(quality);
            }

            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                sellIn = decreaseByOne(sellIn);
            }

            if (name.equals("Backstage passes to a TAFKAL80ETC concert") && sellIn < 0) {
                quality = zeroOut(quality);
            }

            items[i].quality = quality;
            items[i].sellIn = sellIn;
        }
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