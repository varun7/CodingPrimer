package org.code.runs.algo;

import org.junit.Assert;
import org.junit.Test;

public class KMPTest {

    @Test
    public void testWhenContainsPattern() {
        Assert.assertTrue(KMP.contains("AAABAAGAAABAACAABAADEFG", "AABAACAABAA"));
        Assert.assertTrue(KMP.contains("A", "A"));
        Assert.assertTrue(KMP.contains("ABCD", "ABCD"));
        Assert.assertTrue(KMP.contains("ABCDDABCDEBD", "ABCDE"));
    }

    @Test
    public void testWhenDontContainsPattern() {
        Assert.assertFalse(KMP.contains("AAABAAGAAABBAACAABAADEFG", "AABAACAABAA"));
        Assert.assertFalse(KMP.contains("B", "A"));
        Assert.assertFalse(KMP.contains("ABC", "ABCD"));
        Assert.assertFalse(KMP.contains("ABCDDABCDBD", "ABCDE"));
    }
}