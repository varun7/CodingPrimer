package org.code.runs.algo;

import java.util.Random;

public class Algorithms {

    /**
     * Kadane's algorithm. Find the sum of contiguous subarray with 1D array
     * of integers which has the larges sum.
     */
    public static int maxSubarraySum(int[] array) {
        int subsetSum = array[0], maxSubsetSum = array[0];
        for (int i=1; i < array.length; i++) {
            if (subsetSum < 0) {
                subsetSum = array[i];
            } else {
                subsetSum += array[i];
            }
            maxSubsetSum = Math.max(subsetSum, maxSubsetSum);
        }
        return maxSubsetSum;
    }

    /**
     * Moore's Voting algorithm: A majority element in an array A[] of size n is an element
     * that appears more than n/2 times (and hence there is at most one such element).
     * This function returns Integer.MIN_VALUE if there's no majority element in the array.
     */
    public static int majorityElement(int[] array) {
        // Predict majority element.
        int mElement = array[0], mFrequency = 1;
        for (int i=1; i<array.length; i++) {
            if (array[i] != mElement) {
                mFrequency--;
            } else {
                mFrequency++;
            }
            if (mFrequency <= 0) {
                mElement = array[i];
                mFrequency = 1;
            }
        }

        // Validate the mElement is indeed the majority element.
        mFrequency = 0;
        for (int i=0; i<array.length; i++) {
            if (array[i] == mElement) {
                mFrequency++;
            }
        }

        if (mFrequency >= array.length/2) {
            return mElement;
        }
        return Integer.MIN_VALUE;
    }

    public static void shuffleArray(int[] array) {
        Random random = new Random(System.currentTimeMillis());
        for (int end = array.length-1; end > 0; end--) {
            int randomIndex = random.nextInt(end);
            Utils.swap(array, randomIndex, end);
        }
    }
}
