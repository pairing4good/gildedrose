package com.pairgood;

import com.pairgood.rule.quality.increase.BackstagePassSellInLessThanElevenRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GildedRoseTest {

    private GildedRose gildedRose;
    private Item firstItem;
    private MathUtil mathUtil;
    private QualityUtil qualityUtil;
    private StringUtil stringUtil;
    private List<BackstagePassSellInLessThanElevenRule> rules;

    @Before
    public void setUp(){
        mathUtil = new MathUtil();
        qualityUtil = new QualityUtil(mathUtil);
        stringUtil = new StringUtil();
        rules = Arrays.asList(new BackstagePassSellInLessThanElevenRule(stringUtil, qualityUtil));

    }


    @Test
    public void updateQuality_NotAgedBrie() {
        Item firstItem = new Item("Not Aged Brie", 1, 1);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Not Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(0, firstItem.getQuality());
    }

    @Test
    public void updateQuality_AgedBrie() {
        Item firstItem = new Item("Aged Brie", 1, 1);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(2, firstItem.getQuality());
    }


    @Test
    public void updateQuality_BackstagePasses() {
        Item firstItem = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 1);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Backstage passes to a TAFKAL80ETC concert", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(4, firstItem.getQuality());
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndNotMatch() {
        Item firstItem = new Item("Not a Match", -1, 10);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Not a Match", firstItem.getName());
        assertEquals(-2, firstItem.getSellIn());
        assertEquals(8, firstItem.getQuality());
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndBackstagePasses() {
        Item firstItem = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 10);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Backstage passes to a TAFKAL80ETC concert", firstItem.getName());
        assertEquals(-2, firstItem.getSellIn());
        assertEquals(0, firstItem.getQuality());
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndAgedBrie() {
        Item firstItem = new Item("Aged Brie", -1, 10);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Aged Brie", firstItem.getName());
        assertEquals(-2, firstItem.getSellIn());
        assertEquals(12, firstItem.getQuality());
    }

    @Test
    public void updateQuality_MultipleItems() {
        Item firstItem = new Item("Not Aged Brie", 1, 1);
        Item secondItem = new Item("Aged Brie", 1, 1);
        Item[] items = {firstItem, secondItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Not Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(0, firstItem.getQuality());

        assertEquals("Aged Brie", secondItem.getName());
        assertEquals(0, secondItem.getSellIn());
        assertEquals(2, secondItem.getQuality());
    }



    @Test
    public void updateQuality_AgedBrie_WithMaxQuality() {
        Item firstItem = new Item("Aged Brie", 1, 50);
        Item[] items = {firstItem};
        GildedRose gildedRose = new GildedRose(items, qualityUtil, mathUtil, stringUtil, rules);

        gildedRose.updateQuality();

        assertEquals("Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(50, firstItem.getQuality());
    }
}