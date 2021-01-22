package com.pairgood.rule.quality.increase;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.pairgood.Names.BACKSTAGE_PASSES;
import static org.junit.Assert.assertEquals;

public class BackstagePassSellInLessThanElevenRuleTest {

    private QualityRule rule;

    @Before
    public void setUp() {
        rule = new BackstagePassSellInLessThanElevenRule(new StringUtil(), new QualityUtil(new MathUtil()));
    }

    @Test
    public void run_WithNameMatch_AndSellInLessThanEleven() {
        Item item = new Item(BACKSTAGE_PASSES, 1, 1);
        assertEquals(2, rule.run(item));
    }

    @Test
    public void run_WithNameMatch_AndSellInEqualToEleven() {
        Item item = new Item(BACKSTAGE_PASSES, 11, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_WithNameMatch_AndSellInEGreaterThanEleven() {
        Item item = new Item(BACKSTAGE_PASSES, 12, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_WithNameNotMatch_AndSellInLessThanEleven() {
        Item item = new Item("Not a match", 1, 1);
        assertEquals(1, rule.run(item));
    }
}