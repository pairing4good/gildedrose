package com.pairgood;

import org.junit.Test;

import static org.junit.Assert.*;

public class GildedRoseFactoryTest {

    @Test
    public void create() {
        GildedRose gildedRose = new GildedRoseFactory().create();

        assertNotNull(gildedRose);
    }
}