package com.pairgood.util;

public class QualityUtil {

    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;
    private MathUtil mathUtil;

    public QualityUtil(MathUtil mathUtil){

        this.mathUtil = mathUtil;
    }

    public int increaseQuality(int quality) {
        if (isLessThanMax(quality)) {
            quality = mathUtil.increaseByOne(quality);
        }
        return quality;
    }

    public int decreaseQuality(int quality) {
        if (isGreaterThanMin(quality)) {
            quality = mathUtil.decreaseByOne(quality);
        }
        return quality;
    }

    public boolean isLessThanMax(int value) {
        return value < MAX_QUALITY;
    }

    public boolean isGreaterThanMin(int value) {
        return value > MIN_QUALITY;
    }
}
