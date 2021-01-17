package com.pairgood;

public class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            String name = items[i].name;
            int quality = items[i].quality;
            int sellIn = items[i].sellIn;

            if (!name.equals("Aged Brie")
                    && !name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (quality > 0) {
                    if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                        quality = decreaseByOne(quality);
                    }
                }
            } else {
                if (quality < 50) {
                    quality = quality + 1;

                    if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (sellIn < 11) {
                            if (quality < 50) {
                                quality = quality + 1;
                            }
                        }

                        if (sellIn < 6) {
                            if (quality < 50) {
                                quality = quality + 1;
                            }
                        }
                    }
                }
            }

            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                sellIn = decreaseByOne(sellIn);
            }

            if (sellIn < 0) {
                if (!name.equals("Aged Brie")) {
                    if (!name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (quality > 0) {
                            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                                quality = decreaseByOne(quality);
                            }
                        }
                    } else {
                        quality = quality - quality;
                    }
                } else {
                    if (quality < 50) {
                        quality = quality + 1;
                    }
                }
            }
            items[i].quality = quality;
            items[i].sellIn = sellIn;
        }
    }

    private int decreaseByOne(int value) {
        return value - 1;
    }
}