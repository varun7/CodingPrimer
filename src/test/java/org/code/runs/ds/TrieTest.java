package org.code.runs.ds;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TrieTest {

    PrefixTree tree = new Trie();

    @Before
    public void init() {
        tree.add("apple");
        tree.add("xerox");
        tree.add("application");
        tree.add("apt");
    }

    @Test
    public void testIsWord() {
        assertTrue(tree.isWord("apple"));
        assertTrue(tree.isWord("xerox"));
        assertFalse(tree.isWord("ball"));
    }

    @Test
    public void testWordsWithPrefix() {
        List<String> words = tree.wordsWithPrefix("app");
        assertEquals(words.size(), 2);
        assertTrue(words.contains("application"));
        assertTrue(words.contains("apple"));
    }

}