package com.pairgood.rule.quality.decrease;

import com.pairgood.Item;
import com.pairgood.rule.quality.QualityRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.pairgood.Names.*;
import static org.junit.Assert.assertEquals;

public class NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRuleTest {

    private QualityRule rule;

    @Before
    public void setUp() {
        rule = new NotAgedBrieOrBackstagePassesOrSulfurasHandOfRagnarosAndSellInLessThanZeroRule(new StringUtil(),
                new QualityUtil(new MathUtil()));
    }

    @Test
    public void run_WithAgedBrieNameMatch() {
        Item item = new Item(AGED_BRIE, 1, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_WithBackstagePassesNameMatch() {
        Item item = new Item(BACKSTAGE_PASSES, 1, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_WithSulfurasHandOfRagnarosNameMatch() {
        Item item = new Item(SULFURAS_HAND_OF_RAGNAROS, 1, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_Without_BackstagePassesNameMatch_OrAgedBrieNameMatch_OrSulfurasHandOfRagnaros_AndSellInLessThanZero() {
        Item item =
                new Item("not backstage passess or aged brie or sulfuras hand of ragnoros", -1, 1);
        assertEquals(0, rule.run(item));
    }

    @Test
    public void run_Without_BackstagePassesNameMatch_OrAgedBrieNameMatch_OrSulfurasHandOfRagnaros_AndSellInEqualToZero() {
        Item item =
                new Item("not backstage passess or aged brie or sulfuras hand of ragnoros", 0, 1);
        assertEquals(1, rule.run(item));
    }

    @Test
    public void run_Without_BackstagePassesNameMatch_OrAgedBrieNameMatch_OrSulfurasHandOfRagnaros_AndSellInGreaterThanZero() {
        Item item =
                new Item("not backstage passess or aged brie or sulfuras hand of ragnoros", 1, 1);
        assertEquals(1, rule.run(item));
    }
}