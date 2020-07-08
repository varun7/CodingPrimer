package org.code.runs.ds;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class UnionFindTest {
    UnionFind<Integer> rankedUnion = new UnionFind.RankedUnion<>(Arrays.asList(1,2,3,4,5,6,7));

    @Test
    public void test() {
        Assert.assertEquals(rankedUnion.find(1), (Integer) 1);
        Assert.assertEquals(rankedUnion.find(2), (Integer) 2);

        rankedUnion.union(1,2);
        Assert.assertEquals(rankedUnion.find(2), (Integer) 1);
        Assert.assertEquals(rankedUnion.find(1), (Integer) 1);

        rankedUnion.union(3,4);
        Assert.assertEquals(rankedUnion.find(3), (Integer) 3);
        Assert.assertEquals(rankedUnion.find(4), (Integer) 3);

        rankedUnion.union(2,3);
        Assert.assertEquals(rankedUnion.find(1), (Integer) 1);
        Assert.assertEquals(rankedUnion.find(2), (Integer) 1);
        Assert.assertEquals(rankedUnion.find(3), (Integer) 1);
        Assert.assertEquals(rankedUnion.find(4), (Integer) 1);

        Assert.assertEquals(rankedUnion.find(5), (Integer) 5);
        Assert.assertEquals(rankedUnion.find(6), (Integer) 6);
    }
}
