package org.code.runs.dp;

/**
 * Question:
 * Given weights and values of n items, put these items in a knapsack of capacity W to get the maximum total value in the knapsack.
 * In other words, given two integer arrays val[0..n-1] and wt[0..n-1] which represent values and weights associated with n items respectively.
 * Also given an integer W which represents knapsack capacity, find out the maximum value subset of val[]
 * such that sum of the weights of this subset is smaller than or equal to W.
 * You cannot break an item, either pick the complete item, or don’t pick it (0-1 property).
 */
public class Knapsack {

    // bottom up
    public static int tabulatedMaximumValue(int values[], int weights[], int W) {
        // Initialize
        int itemCount = values.length;
        int [][] dp = new int [values.length + 1][ W + 1];
        int currentItemWeight, currentItemValue;

        // Compute
        for (int i=0; i <= itemCount; i++) {
            for (int j=0; j <= W; j++) {


                if (i ==0 || j == 0) {
                    dp[i][j] = 0;
                    continue;
                }

                currentItemWeight = weights[i-1];
                currentItemValue = values[i-1];

                if (j < currentItemWeight) {
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = Integer.max(dp[i-1][j-currentItemWeight] + currentItemValue, dp[i-1][j]);
                }
            }
        }
        return dp[itemCount][W];
    }

    public static int memoizedMaximumValue(int [] values, int [] weights, int W) {
        // Initialize
        int itemCount = values.length;
        int [][] dp = new int [values.length + 1][ W + 1];
        for (int i=0; i <= itemCount; i++) {
            for (int j=0; j <= W; j++) {
                dp [i][j] = -1;
            }
        }
        return _memoizedMaximumValue(values, weights, W, dp, 0);
    }

    private static int _memoizedMaximumValue(int [] values, int [] weights, int W, int [][] dp, int index) {
        if (index >= values.length) {
            return 0;
        }

        if (dp[index][W] != -1) {
            return dp[index][W];
        }

        // Compute
        int valueWhenExcluded = _memoizedMaximumValue(values, weights, W, dp, index + 1); // Not included.
        int valueWhenIncluded;
        if (weights[index] > W) {
            valueWhenIncluded = valueWhenExcluded;
        } else {
            valueWhenIncluded= _memoizedMaximumValue(values, weights, W - weights[index], dp, index + 1) + values[index]; // included.
        }
        dp[index][W] = Integer.max(valueWhenIncluded, valueWhenExcluded);
        return dp[index][W];
     }

}
