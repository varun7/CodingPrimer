package org.code.runs.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class SubsetSumTest {

    SubsetSum subsetSum = new SubsetSum();

    @Test
    public void testSubsetSum() {
        int[] input = { 6, 5, 7, 2, 3, 1 };
        int target = 11;
        System.out.println("There exist a subset with sum = " + target + " = " + subsetSum.isSubsetWithSum(input, target));
    }

    @Test
    public void testSubsetSumOptimized() {
        int[] input = { 6, 5, 7, 2, 3, 1 };
        int target = 11;
        System.out.println("There exist a subset with sum = " + target + " = " + subsetSum.optimizedIsSubsetWithSum(input, target));
    }

}