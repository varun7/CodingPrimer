package org.code.runs.ds;


import org.junit.Test;

public class SkipListTest {

    @Test
    public void testAdd() {
        SkipList<Integer> skipList = new SkipList.RandomizedSkipList<>(10);
        skipList.add(10);
        skipList.add(100);
    }

}