package com.orange.latex.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 小天
 * @date 2020/12/8 10:08
 */
public class CharacterUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isLatinAlphabet() {
    }

    @Test
    public void isUppercaseLatinAlphabet() {
    }

    @Test
    public void isLowercaseLatinAlphabet() {
    }

    @Test
    public void testCodePoint() {
        String plainTxt = "\\frac{1}{2} \\frac{1}{2}";
        int[] codePoints = plainTxt.codePoints().toArray();
        for (int codePoint : codePoints) {
            System.out.println(codePoint);
        }
    }
}