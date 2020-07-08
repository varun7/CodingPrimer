package org.code.runs.dp;

import java.util.ArrayList;
import java.util.List;

public class SubsetSum {

    /**
     * Given a set of non-negative integers, and a value sum, determine
     * if there is a subset of the given set with sum equal to given sum.
     * Input:  set[] = {3, 34, 4, 12, 5, 2}, sum = 9
     * Output:  True  //There is a subset (4, 5) with sum 9.
     *
     * Complexity:
     *   Time : O(n * targetSum)
     *   Space: O(n * targetSum)
     */
    public boolean isSubsetWithSum(int[] set, int target) {
        boolean[][] dp = new boolean[set.length +1][target+1];

        dp[0][0] = true;
        for (int i=0; i <= set.length; i++) {
            for (int j=0; j <= target; j++) {

                // Initialize first row and column.
                if (j==0) {
                    dp[i][j] = true;
                    continue;
                }
                if (i==0) {
                    dp[i][j] = false;
                    continue;
                }
                dp[i][j] = dp[i-1][j] || (j - set[i-1] >= 0 && dp[i-1][j-set[i-1]]);
            }
        }
        printAllSubsetWithGivenSum(set, dp, target);
        return dp[set.length][target];
    }

    /**
     * This solution is space optimized. Idea behind it is that
     * we only need access to previous row, so we can alternate between 2 rows.
     * Complexity:
     *   Time : O(n * targetSum)
     *   Space: O(n)
     */
    public boolean optimizedIsSubsetWithSum(int[] set, int target) {
        boolean [][] dp = new boolean[2][target+1];

        dp[0][0] = true;
        dp[1][0] = true;
        for (int j=1; j <=target; j++) {
            dp[0][j] = false;
        }

        for (int i=1; i <= set.length; i++) {
            for (int j=1; j <= target; j++) {
                int currentRow = i % 2;
                int prevRow = currentRow != 0 ? 0 : 1;
                dp[currentRow][j] = dp[prevRow][j] || (j-set[i-1] >=0 && dp[prevRow][j-set[i-1]]);
            }
        }
        return set.length % 2 == 0 ? dp[0][target] : dp[1][target];
    }

    private void printAllSubsetWithGivenSum(int[] set, boolean [][]dp, int target) {
        if (!dp[set.length][target]) {
            System.out.println("No subset sum to " + target);
            return;
        }
        List<Integer> result = new ArrayList<>();
        backTrackAndPrint(dp, set, target, result, set.length, target);
    }

    private void backTrackAndPrint(boolean [][]dp, int[] set, int target, List<Integer> result, int i, int j) {
        if (!dp[i][j]) {
            return;
        }

        if (i == 0) {
            if (target != 0 && dp[0][target]) {
                result.add(target);
            }
            printList(result);
            return;
        }

        if (dp[i-1][j]) {
            // Doesn't include current.
            backTrackAndPrint(dp, set, target, result, i-1, j);
        }

        if (j - set[i-1] >=0 && dp[i-1][j-set[i-1]]) {
            result.add(set[i-1]);
            backTrackAndPrint(dp, set, target - set[i-1], result, i-1, j-set[i-1]);
            result.remove((Integer) set[i-1]);
        }
    }

    private void printList(List<Integer> list) {
        System.out.println();
        list.stream().forEach(i -> System.out.print(i + " "));
    }
}
