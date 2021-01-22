package com.pairgood.rule.quality.decrease;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.pairgood.Names.*;
import static org.junit.Assert.*;

public class BackstagePassSellInLessThanZeroRuleTest {

    private QualityRule rule;

    @Before
    public void setUp() {
        rule = new BackstagePassSellInLessThanZeroRule(new StringUtil(), new MathUtil());
    }

    @Test
    public void run_WithBackstagePassesNameMatchAndSellInLessThanZero() {
        Item item = new Item(BACKSTAGE_PASSES, -1, 10);
        assertEquals(0, rule.run(item));
    }

    @Test
    public void run_WithBackstagePassesNameMatchAndSellInEqualZero() {
        Item item = new Item(BACKSTAGE_PASSES, 0, 10);
        assertEquals(10, rule.run(item));
    }

    @Test
    public void run_WithBackstagePassesNameMatchAndSellInGreaterThanZero() {
        Item item = new Item(BACKSTAGE_PASSES, 1, 10);
        assertEquals(10, rule.run(item));
    }

    @Test
    public void run_WithBackstagePassesNameNotMatchAndSellInLessThanZero() {
        Item item = new Item("not backstage pass", -1, 10);
        assertEquals(10, rule.run(item));
    }
}