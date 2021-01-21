package com.pairgood.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void matches_One() {
        assertTrue(new StringUtil().matches("value", "value"));
    }

    @Test
    public void matches_OneOfList() {
        assertTrue(new StringUtil().matches("value", "value", "not value"));
    }

    @Test
    public void matches_NoneOfList() {
        assertFalse(new StringUtil().matches("value", "bad value", "not value"));
    }

    @Test
    public void notMatch_WithNoMatch() {
        assertTrue(new StringUtil().notMatch("value", "not value"));
    }

    @Test
    public void notMatch_WithMatch() {
        assertFalse(new StringUtil().notMatch("value", "value"));
    }

    @Test
    public void notMatch_WithSomeMatch() {
        assertFalse(new StringUtil().notMatch("value", "value", "not value"));
    }

    @Test
    public void notMatch_WithNoMatchInList() {
        assertTrue(new StringUtil().notMatch("value", "bad value", "not value"));
    }
}