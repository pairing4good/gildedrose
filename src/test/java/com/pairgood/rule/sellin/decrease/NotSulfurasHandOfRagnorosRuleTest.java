package com.pairgood.rule.sellin.decrease;

import com.pairgood.Item;
import com.pairgood.rule.sellin.SellInRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.pairgood.Names.SULFURAS_HAND_OF_RAGNAROS;
import static org.junit.Assert.assertEquals;

public class NotSulfurasHandOfRagnorosRuleTest {

    private SellInRule rule;

    @Before
    public void setUp() {
        rule = new NotSulfurasHandOfRagnorosRule(new StringUtil(), new MathUtil());
    }

    @Test
    public void run_WithSulfurasHandOfRagnarosNameMatch() {
        Item item = new Item(SULFURAS_HAND_OF_RAGNAROS, 1, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_Without_BackstagePassesNameMatch_OrAgedBrieNameMatch() {
        Item item = new Item("not sulfuras hand of ragnaros", 1, 1);
        assertEquals(0, rule.run(item));
    }
}