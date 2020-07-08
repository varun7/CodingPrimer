package org.code.runs.dp;

import org.code.runs.algo.Utils;

public class UnclassifiedDP {

    private final static int SECOND = 1;
    private final static int FIRST = 0;

    /**
     * You are given n pair of number. In every pair, the first number is always smaller than the second number.
     * A pair (c,d) can follow another pair (a,c) if b < c. Chain of pairs can be formed in this fashion.
     * Find the longest chain which can be formed from a given set of pairs.
     * @return
     */
    public static int maxChainLength() {
        int [][] pairs = {{5,24}, {39,60}, {15,28}, {27,40}, {50,90}};
//        return _maxChainLength(pairs, -1, 0);
        return tabulatedMaxChainLength(pairs);
    }

    // Brute force solution.
    private static int _maxChainLength(int[][] pairs, int lastIndex, int index) {
        if (index >= pairs.length) {
            return 0;
        }
        if (lastIndex != -1 && pairs[index][FIRST] <= pairs[lastIndex][SECOND]) {
            return _maxChainLength(pairs, index, index +1);
        }
        return Integer.max(_maxChainLength(pairs, lastIndex, index + 1), _maxChainLength(pairs, index, index + 1) + 1);
    }

    // This is very similar to Longest Increasing Subsequence.
    private static int tabulatedMaxChainLength(int [][] pairs) {
        int dp[] = new int[pairs.length];

        int maxChain = Integer.MIN_VALUE;
        for (int i=0; i<pairs.length; i++) {
            dp[i] = maximumChainSizeWithSmallerEndLink(pairs, dp, i);
            maxChain = dp[i] > maxChain ? dp[i] : maxChain;
        }
        return maxChain;
    }

    private static int maximumChainSizeWithSmallerEndLink(int [][] pairs, int []dp, int index) {
        int max = Integer.MIN_VALUE;
        for (int i=0; i < index; i++) {
            if (pairs[i][SECOND] < pairs[index][FIRST]) {
                max = dp[i] > max ? dp[i] : max;
            }
        }
        return max == Integer.MIN_VALUE ? 1 : max + 1;
    }

    /**
     * There is an integer array consisting of positive numbers only. Find maximum possible
     * sum of elements such that there are no 2 consecutive elements present in the sum.
     * @return
     */
    public static int maximumNonConsecutiveSum() {
        int array[] = {1,2,3,4,5,6,7,8,9};
        int dp[] = new int[array.length];
        dp [0] = array[0];
        dp [1] = array[0] > array[1] ? array[0] : array[1];

        for (int i = 2; i < array.length; i++) {
            dp [i] = Integer.max(dp[i-1], dp[i-2] + array[i]);
        }
        return dp[array.length-1];
    }

    /**
     * Given an array of integers where each elements represents the max number of steps that can be
     * made forward from that element. Write a function to return the minimum number of jumps to reach
     * the end of the array (starting from the first element). If an element is 0, then cannot move through that element.
     */
    public static int minimumJumps(int[] array) {
        int dp[] = new int[array.length];
        dp[0] = 0;

        for (int i=1; i<dp.length; i++) {
            int min = Integer.MAX_VALUE - 1;
            for (int j=i-1; j >=0 ; j--) {
                if (i - j <= array[j] && dp[j] < min) {
                    min = dp[j];
                }
            }
            dp[i] = min + 1;
        }

        return dp[array.length-1];
    }

    /**
     * Given a binary matrix, find out the maximum size squar sub-matrix with all 1s.
     */
    public static int maximumSquareMatrix(int [][]matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int [][]dp = new int[rows][cols];

        int maxSubMatrixSize = Integer.MIN_VALUE;
        for (int i=0; i < rows; i++) {
            for (int j=0; j<cols; j++) {
                if (i==0 || j ==0) {
                    dp[i][j] = matrix[i][j];
                } else if (matrix[i][j] == 1) {
                    dp[i][j] = Utils.minimum(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1;
                } else {
                    dp[i][j] = 0;
                }
                if (dp[i][j] > maxSubMatrixSize) {
                    maxSubMatrixSize = dp[i][j];
                }
            }
        }
        return maxSubMatrixSize;
    }


    /**
     * https://leetcode.com/problems/coin-change-2/
     * You are given coins of different denominations and a total amount of money.
     * Write a function to compute the number of combinations that make up that amount.
     * You may assume that you have infinite number of each kind of coin.
     *
     * Example 1:
     *
     * Input: amount = 5, coins = [1, 2, 5]
     * Output: 4
     * Explanation: there are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     * Example 2:
     *
     * Input: amount = 3, coins = [2]
     * Output: 0
     * Explanation: the amount of 3 cannot be made up just with coins of 2.
     */
    class CoinChange2 {

        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    dp[i] += dp[i-coin];
                }
            }
            return dp[amount];
        }
    }


    /**
     * https://leetcode.com/problems/coin-change/
     * You are given coins of different denominations and a total amount of money amount.
     * Write a function to compute the fewest number of coins that you need to make up that amount.
     * If that amount of money cannot be made up by any combination of the coins, return -1.
     *
     * Example 1:
     *
     * Input: coins = [1, 2, 5], amount = 11
     * Output: 3
     * Explanation: 11 = 5 + 5 + 1
     * Example 2:
     *
     * Input: coins = [2], amount = 3
     * Output: -1
     */
    class CoinChange {
        public int coinChange(int[] coins, int amount) {
            return tabulated(coins, amount);
        }

        private int tabulated2D(int[] coins, int amount) {
            int[][] dp = new int[coins.length][amount+1];

            for (int i=0; i<dp.length; i++) {
                for (int j=0; j < dp[0].length; j++) {
                    if (j==0) {
                        dp[i][j] = 0;
                    } else if(i==0 && j % coins[0] == 0) {
                        dp[i][j] = j/coins[0];
                    } else {
                        dp[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            for (int i=1; i < dp.length; i++) {
                for (int j=1; j < dp[0].length; j++) {
                    dp[i][j] = Math.min(dp[i-1][j], Integer.MAX_VALUE);
                    if (j >= coins[i] && dp[i][j-coins[i]] != Integer.MAX_VALUE) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][j-coins[i]]+ 1);
                    }
                }
            }
            return dp[coins.length-1][amount] == Integer.MAX_VALUE ? - 1: dp[coins.length-1][amount];
        }

        private int tabulated(int[] coins, int amount) {
            int[] cache = new int[amount+1];
            cache[0] = 0;
            for (int i=1; i<=amount; i++) {
                cache[i] = Integer.MAX_VALUE;
                for (int coin: coins) {
                    if (i >= coin && cache[i-coin] != Integer.MAX_VALUE) {
                        cache[i] = Math.min(cache[i], cache[i-coin] + 1);
                    }
                }
            }
            return cache[amount] == Integer.MAX_VALUE ? -1 : cache[amount];
        }
    }
}
