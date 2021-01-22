package com.pairgood;

import com.pairgood.rule.quality.QualityRule;
import com.pairgood.rule.quality.increase.BackstagePassSellInLessThanElevenRule;
import com.pairgood.rule.quality.increase.BackstagePassSellInLessThanSixRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GildedRoseTest {

    private MathUtil mathUtil;
    private QualityUtil qualityUtil;
    private StringUtil stringUtil;
    private List<QualityRule> rules;
    private GildedRose gildedRose;

    @Before
    public void setUp(){
        mathUtil = new MathUtil();
        qualityUtil = new QualityUtil(mathUtil);
        stringUtil = new StringUtil();
        rules = Arrays.asList(
                new BackstagePassSellInLessThanElevenRule(stringUtil, qualityUtil),
                new BackstagePassSellInLessThanSixRule(stringUtil, qualityUtil));
        gildedRose = new GildedRose(qualityUtil, mathUtil, stringUtil, rules);


    }

    @Test
    public void updateQuality_NotAgedBrie() {
        Item[] items = {new Item("Not Aged Brie", 1, 1)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];

        assertEquals("Not Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(0, firstItem.getQuality());
    }

    @Test
    public void updateQuality_AgedBrie() {
        Item[] items = {new Item("Aged Brie", 1, 1)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];

        assertEquals("Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(2, firstItem.getQuality());
    }


    @Test
    public void updateQuality_BackstagePasses() {
        Item[] items = {new Item("Backstage passes to a TAFKAL80ETC concert", 1, 1)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];

        assertEquals("Backstage passes to a TAFKAL80ETC concert", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(4, firstItem.getQuality());
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndNotMatch() {
        Item[] items = {new Item("Not a Match", -1, 10)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];

        assertEquals("Not a Match", firstItem.getName());
        assertEquals(-2, firstItem.getSellIn());
        assertEquals(8, firstItem.getQuality());
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndBackstagePasses() {
        Item[] items = {new Item("Backstage passes to a TAFKAL80ETC concert", -1, 10)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];

        assertEquals("Backstage passes to a TAFKAL80ETC concert", firstItem.getName());
        assertEquals(-2, firstItem.getSellIn());
        assertEquals(0, firstItem.getQuality());
    }

    @Test
    public void updateQuality_SellInNegativeNumber_AndAgedBrie() {
        Item[] items = {new Item("Aged Brie", -1, 10)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];

        assertEquals("Aged Brie", firstItem.getName());
        assertEquals(-2, firstItem.getSellIn());
        assertEquals(12, firstItem.getQuality());
    }

    @Test
    public void updateQuality_MultipleItems() {
        Item[] items = {
                new Item("Not Aged Brie", 1, 1),
                new Item("Aged Brie", 1, 1)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];
        Item secondItem = item[1];

        assertEquals("Not Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(0, firstItem.getQuality());

        assertEquals("Aged Brie", secondItem.getName());
        assertEquals(0, secondItem.getSellIn());
        assertEquals(2, secondItem.getQuality());
    }



    @Test
    public void updateQuality_AgedBrie_WithMaxQuality() {
        Item[] items = {new Item("Aged Brie", 1, 50)};

        Item[] item = gildedRose.updateQuality(items);

        Item firstItem = item[0];

        assertEquals("Aged Brie", firstItem.getName());
        assertEquals(0, firstItem.getSellIn());
        assertEquals(50, firstItem.getQuality());
    }
}