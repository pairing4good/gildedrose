package com.pairgood;

public class GildedRose {
    public static final int MAX_QUALITY = 50;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            String name = items[i].name;
            int quality = items[i].quality;
            int sellIn = items[i].sellIn;

            if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (sellIn < 11) {
                    quality = increaseQuality(quality);
                }

                if (sellIn < 6) {
                    quality = increaseQuality(quality);
                }
            }

            if (name.equals("Aged Brie")
                    || name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                quality = increaseQuality(quality);
            } else {
                if (quality > 0) {
                    if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                        quality = decreaseByOne(quality);
                    }
                }
            }

            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                sellIn = decreaseByOne(sellIn);
            }

            if (sellIn < 0) {
                if (name.equals("Aged Brie")) {
                    quality = increaseQuality(quality);
                } else {
                    if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        quality = zeroOut(quality);
                    } else {
                        if (quality > 0) {
                            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                                quality = decreaseByOne(quality);
                            }
                        }
                    }
                }
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

    private boolean isLessThanMax(int value) {
        return value < MAX_QUALITY;
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