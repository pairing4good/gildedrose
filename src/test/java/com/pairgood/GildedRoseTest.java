package com.pairgood;

import org.junit.Test;

import static org.junit.Assert.*;

public class GildedRoseTest {


    @Test
    public void updateQuality_NotAgedBrie() {
        Item firstItem = new Item("Not Aged Brie", 1, 1);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals("Not Aged Brie", firstItem.name);
        assertEquals(0, firstItem.sellIn);
        assertEquals(0, firstItem.quality);
    }

    @Test
    public void updateQuality_AgedBrie() {
        Item firstItem = new Item("Aged Brie", 1, 1);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals("Aged Brie", firstItem.name);
        assertEquals(0, firstItem.sellIn);
        assertEquals(2, firstItem.quality);
    }


    @Test
    public void updateQuality_BackstagePasses() {
        Item firstItem = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 1);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals("Backstage passes to a TAFKAL80ETC concert", firstItem.name);
        assertEquals(0, firstItem.sellIn);
        assertEquals(4, firstItem.quality);
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndNotMatch() {
        Item firstItem = new Item("Not a Match", -1, 10);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals("Not a Match", firstItem.name);
        assertEquals(-2, firstItem.sellIn);
        assertEquals(8, firstItem.quality);
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndBackstagePasses() {
        Item firstItem = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 10);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals("Backstage passes to a TAFKAL80ETC concert", firstItem.name);
        assertEquals(-2, firstItem.sellIn);
        assertEquals(0, firstItem.quality);
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndAgedBrie() {
        Item firstItem = new Item("Aged Brie", -1, 10);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals("Aged Brie", firstItem.name);
        assertEquals(-2, firstItem.sellIn);
        assertEquals(12, firstItem.quality);
    }

    @Test
    public void updateQuality_MultipleItems() {
        Item firstItem = new Item("Not Aged Brie", 1, 1);
        Item secondItem = new Item("Aged Brie", 1, 1);
        Item[] items = {firstItem, secondItem};
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals("Not Aged Brie", firstItem.name);
        assertEquals(0, firstItem.sellIn);
        assertEquals(0, firstItem.quality);

        assertEquals("Aged Brie", secondItem.name);
        assertEquals(0, secondItem.sellIn);
        assertEquals(2, secondItem.quality);
    }
}