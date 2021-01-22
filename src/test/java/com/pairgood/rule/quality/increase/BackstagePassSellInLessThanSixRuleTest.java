package com.pairgood.rule.quality.increase;

import com.pairgood.Item;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.pairgood.Names.BACKSTAGE_PASSES;
import static org.junit.Assert.*;

public class BackstagePassSellInLessThanSixRuleTest {

    private BackstagePassSellInLessThanSixRule rule;

    @Before
    public void setUp(){
        rule = new BackstagePassSellInLessThanSixRule(new StringUtil(), new QualityUtil(new MathUtil()));
    }

    @Test
    public void run_WithNameMatch_AndSellInLessThanSix() {
        Item item = new Item(BACKSTAGE_PASSES, 1, 1);
        assertEquals(2, rule.run(item));
    }

    @Test
    public void run_WithNameMatch_AndSellInEqualToSix() {
        Item item = new Item(BACKSTAGE_PASSES, 6, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_WithNameMatch_AndSellInEGreaterThanSix() {
        Item item = new Item(BACKSTAGE_PASSES, 7, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_WithNameNotMatch_AndSellInLessThanSix() {
        Item item = new Item("Not a match", 1, 1);
        assertEquals(1, rule.run(item));
    }
}