package org.code.runs.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InversionPair {

    public int countInversions(int [] array) {
        Map<Integer, Integer> ranks = coordinateCompression(array);
        return createBITAndCountInversions(array, ranks);
    }

    /**
     * This method creates a BIT with the help of rank Map. And count the inversions during insertion.
     * Step:
     * 1. Iterate over original input array from right to left. i -> [size-1...0]
     * 1.a. Find index/rank of current element array[i] in sorted array. rankInSortedArray
     * 1.b. Find all elements with rankInSortedArray greater than current element's rankInSortedArray. i.e. bit.query([0..rankInSortedArray-1]).
     * 1.c. Now add current element's rank in BIT.
     */
    private int createBITAndCountInversions(int[] array, Map<Integer, Integer> ranks) {
        int result = 0;
        int rankInSortedArray;
        BIT bit = new BIT(ranks.size() + 1);
        for (int i=array.length -1; i > -1; i--) {
            rankInSortedArray = ranks.get(array[i]);
            result += bit.query(rankInSortedArray -1);
            bit.update(rankInSortedArray, 1);
        }
        return result;
    }

    /**
     * This method applies coordinate compression.
     * Steps:
     * 1. Copy the input array to copy[]
     * 2. Sort the copy array.
     * 3. Iterate over sorted array and create a map of key -> element and value -> position in sorted array.
     * When array contain duplicate values then first occurrence is put in map.
     *
     * Example
     * input =     [92, 631, 50, 7]
     * copy =      [7, 50, 92, 631]
     * rankMap     7 => 1, 50 => 2, 92 => 3, 631 => 4
     */
    private Map<Integer, Integer> coordinateCompression(int[] array) {
        int [] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);

        Map<Integer, Integer> rankMap = new HashMap<>();
        for (int i=0, rank = 1; i<copy.length; i++) {
            if (!rankMap.containsKey(copy[i])) {
                rankMap.put(copy[i], rank++);
            }
        }
        return rankMap;
    }

    class BIT {
        private int[] tree;

        public BIT(int size) {
            this.tree = new int[size];
        }

        public void update(int index, int val) {
            for (int i=index; i < tree.length; i += (i & -i)) {
                tree[i] += val;
            }
        }

        public int query(int index) {
            int val = 0;
            for (int i=index; i > 0; i -= (i & -i)) {
                val += tree[i];
            }
            return val;
        }
    }
}
