package com.pasha.efebudak.stringcounter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void tenthChar_emptyString_returnsEmpty() {
        assertEquals("", StringCounterUtils.findTenthChar(""));
    }

    @Test
    public void tenthChar_null_returnsEmpty() {
        assertEquals("", StringCounterUtils.findTenthChar(null));
    }

    @Test
    public void tenthChar_arbitraryString_returnsS() {
        assertEquals("S", StringCounterUtils.findTenthChar("arbitraryString"));
    }

    @Test
    public void everyTenthChar_emptyString_returnsEmpty() {
        assertEquals("", StringCounterUtils.findEveryTenthChar(""));
    }

    @Test
    public void everyTenthChar_null_returnsEmpty() {
        assertEquals("", StringCounterUtils.findEveryTenthChar(null));
    }

    @Test
    public void everyTenthChar_arbitraryString_returnsS() {
        assertEquals("S", StringCounterUtils.findEveryTenthChar("arbitraryString"));
    }

    @Test
    public void everyTenthChar_arbitraryStringForThreeTenthChar_returns_Shh() {
        assertEquals("Shh", StringCounterUtils.findEveryTenthChar("arbitraryStringForThreeTenthChar"));
    }

}