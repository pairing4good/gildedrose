package com.pairgood.util;

import org.junit.Before;
import org.junit.Test;

import static com.pairgood.util.QualityUtil.*;
import static org.junit.Assert.*;

public class QualityUtilTest {

    private QualityUtil qualityUtil;

    @Before
    public void setUp(){
        qualityUtil = new QualityUtil(new MathUtil());
    }

    @Test
    public void increaseQuality_WhenMaxQuality_ReturnsMax() {
        assertEquals(MAX_QUALITY, qualityUtil.increaseQuality(MAX_QUALITY));
    }

    @Test
    public void increaseQuality_WhenOneLessThanMaxQuality_ReturnsMax() {
        int oneLessThanMax = MAX_QUALITY - 1;
        assertEquals(MAX_QUALITY, qualityUtil.increaseQuality(oneLessThanMax));
    }

    @Test
    public void increaseQuality_PositiveNumber() {
        assertEquals(2, qualityUtil.increaseQuality(1));
    }

    @Test
    public void increaseQuality_Zero() {
        assertEquals(1, qualityUtil.increaseQuality(0));
    }

    @Test
    public void increaseQuality_NegativeNumber() {
        assertEquals(0, qualityUtil.increaseQuality(-1));
    }

    @Test
    public void decreaseQuality_WhenMinQuality_ReturnsMin() {
        assertEquals(MIN_QUALITY, qualityUtil.decreaseQuality(MIN_QUALITY));
    }

    @Test
    public void decreaseQuality_WhenOneGreaterThanMinQuality_ReturnsMin() {
        int oneGreaterThanMIN = MIN_QUALITY + 1;
        assertEquals(MIN_QUALITY, qualityUtil.decreaseQuality(oneGreaterThanMIN));
    }

    @Test
    public void decreaseQuality_PositiveNumber() {
        assertEquals(0, qualityUtil.decreaseQuality(1));
    }

    @Test
    public void decreaseQuality_WhenBelowMin_ThenReturnOriginalNumber() {
        int lessThanMin = MIN_QUALITY - 1;
        assertEquals(lessThanMin, qualityUtil.decreaseQuality(lessThanMin));
    }

    @Test
    public void isLessThanMax_WhenGreaterThanMax() {
        int greaterThanMax = MAX_QUALITY + 1;
        assertFalse(qualityUtil.isLessThanMax(greaterThanMax));
    }

    @Test
    public void isLessThanMax_WhenMax() {
        assertFalse(qualityUtil.isLessThanMax(MAX_QUALITY));
    }

    @Test
    public void isLessThanMax_WhenLessThanMax() {
        int lessThanMax = MAX_QUALITY -1;
        assertTrue(qualityUtil.isLessThanMax(lessThanMax));
    }

    @Test
    public void isGreaterThanMin_WhenGreaterThanMin() {
        int greaterThanMin = MIN_QUALITY + 1;
        assertTrue(qualityUtil.isGreaterThanMin(greaterThanMin));
    }

    @Test
    public void isGreaterThanMin_WhenMin() {
        assertFalse(qualityUtil.isGreaterThanMin(MIN_QUALITY));
    }

    @Test
    public void isGreaterThanMin_WhenLessThanMin() {
        int lessThanMin = MIN_QUALITY - 1;
        assertFalse(qualityUtil.isGreaterThanMin(lessThanMin));
    }
}