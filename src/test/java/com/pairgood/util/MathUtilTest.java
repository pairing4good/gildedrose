package com.pairgood.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathUtilTest {

    @Test
    public void zeroOut_Zero() {
        assertEquals(0, new MathUtil().zeroOut(0));
    }

    @Test
    public void zeroOut_NegativeNumber() {
        assertEquals(0, new MathUtil().zeroOut(-1));
    }

    @Test
    public void zeroOut_PositiveNumber() {
        assertEquals(0, new MathUtil().zeroOut(1));
    }

    @Test
    public void increaseByOne_Zero() {
        assertEquals(1, new MathUtil().increaseByOne(0));
    }

    @Test
    public void increaseByOne_NegativeNumber() {
        assertEquals(0, new MathUtil().increaseByOne(-1));
    }

    @Test
    public void increaseByOne_PositiveNumber() {
        assertEquals(2, new MathUtil().increaseByOne(1));
    }


    @Test
    public void decreaseByOne_Zero() {
        assertEquals(-1, new MathUtil().decreaseByOne(0));
    }

    @Test
    public void decreaseByOne_NegativeNumber() {
        assertEquals(-2, new MathUtil().decreaseByOne(-1));
    }

    @Test
    public void decreaseByOne_PositiveNumber() {
        assertEquals(0, new MathUtil().decreaseByOne(1));
    }
}