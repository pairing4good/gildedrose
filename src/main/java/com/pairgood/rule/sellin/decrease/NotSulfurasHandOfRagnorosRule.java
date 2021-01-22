package com.pairgood.rule.sellin.decrease;

import com.pairgood.Item;
import com.pairgood.rule.sellin.SellInRule;
import com.pairgood.util.MathUtil;
import com.pairgood.util.QualityUtil;
import com.pairgood.util.StringUtil;

import static com.pairgood.Names.SULFURAS_HAND_OF_RAGNAROS;

public class NotSulfurasHandOfRagnorosRule implements SellInRule {

    private StringUtil stringUtil;
    private MathUtil mathUtil;

    public NotSulfurasHandOfRagnorosRule(StringUtil stringUtil, MathUtil mathUtil){
        this.stringUtil = stringUtil;
        this.mathUtil = mathUtil;
    }


    @Override
    public int run(Item item) {
        if (stringUtil.notMatch(item.getName(), SULFURAS_HAND_OF_RAGNAROS)) {
            return mathUtil.decreaseByOne(item.getSellIn());
        }

        return item.getSellIn();
    }
}
