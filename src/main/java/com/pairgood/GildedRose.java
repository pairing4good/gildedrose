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

            if (!name.equals("Aged Brie")
                    && !name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (quality > 0) {
                    if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                        quality = quality - 1;
                    }
                }
            } else {
                if (quality < 50) {
                    quality = quality + 1;

                    if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (quality < 50) {
                                quality = quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (quality < 50) {
                                quality = quality + 1;
                            }
                        }
                    }
                }
            }

            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!name.equals("Aged Brie")) {
                    if (!name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (quality > 0) {
                            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                                quality = quality - 1;
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
        }
    }
}