package org.code.runs.ds;

import org.junit.Assert;
import org.junit.Test;

public class SegmentTreeTest {

    @Test
    public void testMinSegTreeQuery() {
        int [] input = {1,3,5,8,9,2};
        SegmentTree minSegTree = new SegmentTree.MinSegTree(input);
        Assert.assertEquals(minSegTree.query(0,5), 1);
        Assert.assertEquals(minSegTree.query(1,5), 2);
        Assert.assertEquals(minSegTree.query(4,4), 9);
        Assert.assertEquals(minSegTree.query(3,4), 8);
        Assert.assertEquals(minSegTree.query(3,5), 2);
    }

    @Test
    public void testMaxSegTreeQuery() {
        int [] input = {1,3,5,8,9,2};
        SegmentTree maxSegTree = new SegmentTree.MaxSegTree(input);
        Assert.assertEquals(maxSegTree.query(0,5), 9);
        Assert.assertEquals(maxSegTree.query(1,5), 9);
        Assert.assertEquals(maxSegTree.query(4,4), 9);
        Assert.assertEquals(maxSegTree.query(2,3), 8);
    }
}
