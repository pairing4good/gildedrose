package com.pairgood.rule.quality.decrease;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.rule.quality.increase.AgedBrieOrBackstagePassesRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.pairgood.Names.AGED_BRIE;
import static com.pairgood.Names.BACKSTAGE_PASSES;
import static org.junit.Assert.*;

public class NotAgedBrieOrBackstagePassesRuleTest {

    private QualityRule rule;

    @Before
    public void setUp(){
        rule = new NotAgedBrieOrBackstagePassesRule(new StringUtil(), new QualityUtil(new MathUtil()));
    }

    @Test
    public void run_WithAgedBrieNameMatch(){
        Item item = new Item(AGED_BRIE, 1, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_WithBackstagePassesNameMatch(){
        Item item = new Item(BACKSTAGE_PASSES, 1, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_Without_BackstagePassesNameMatch_OrAgedBrieNameMatch(){
        Item item = new Item("not backstage passess or aged brie", 1, 1);
        assertEquals(0, rule.run(item));
    }
}