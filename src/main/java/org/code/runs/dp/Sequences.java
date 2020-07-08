package org.code.runs.dp;

import org.code.runs.algo.Utils;

public class Sequences {

    /**
     * Given two strings of size m,n and set of operations replace (R), insert (I) and delete (D)
     * all at equal cost. Find minimum number of edits (oeprations) required to convert one string into another.
     */
    public static int levenshteinDistances(String s1, String s2) {
        int [][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i=1; i <= s1.length(); i++) {
            for (int j =1 ; j <= s2.length(); j++) {
                if (i ==0) {
                    dp[i][0] = i;
                } else if (j == 0) {
                    dp[i][j] = j;
                } else if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = Utils.minimum(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1;
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    /**
     * Given a string, find the minimum number of characters to be inserted to convert it to palindrome.
     */
    public static int minimumInsertionForPalindrom(String s) {
        // Initialize.
        int dp[][] = new int[s.length()][s.length()];
        for (int i=0; i< s.length(); i++) {
            for (int j=0; j<s.length(); j++) {
                dp[i][j] = -1;
            }
        }
        return memoizedPalindrome(s, 0, s.length()-1, dp);
    }

    public static int minimumInsertionForPalindromTabulated(String s) {
        int [][] dp = new int[s.length()][s.length()];
        for (int len=2; len <= s.length(); len++) {
            for (int i=0, j=i+len-1; j < s.length(); i++, j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1];
                } else {
                    dp[i][j] = Math.min(dp[i+1][j], dp[i][j-1]) + 1;
                }
            }
        }
        return dp[0][s.length()-1];
    }

    private static int memoizedPalindrome(String s, int i, int j, int [][]dp) {
        if (i >= j) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        } else if (s.charAt(i) == s.charAt(j)) {
            return memoizedPalindrome(s, i+1, j-1, dp);
        } else {
            return Integer.min(memoizedPalindrome(s, i+1, j, dp), memoizedPalindrome(s, i, j-1, dp)) + 1;
        }
    }

    public static int tabulatedLongestPalindromicSubsequence(String s) {
        int[][] dp = new int[s.length()][s.length()];

        for (int i=0; i < dp.length; i++) {
            dp[i][i] = 1;
        }

        int max = 0;
        for (int len=2; len <= s.length(); len++) {
            for (int i=0, j=i+len-1; j < s.length(); i++, j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }

    /**
     * Longest increasing subsequence problem is to find a sub sequence of a give sequence in which the
     * sub sequence elements are in sorted order lowest to highest, and in which the subsequence is
     * as long as possible. This subsequence is not necessarily contiguous or unique.
     *
     */
    public static int longestIncreasingSubsequence() {
        int input[] ={0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15};
        int dp [] = new int[input.length];

        // Compute and Initialize
        int max = Integer.MIN_VALUE;
        for (int i=0; i<input.length; i++) {
            dp [i] = getMaximumSequenceWithSmallerEnd(input, dp, i);
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }

    private static int getMaximumSequenceWithSmallerEnd(int [] input, int [] dp, int index) {
        int max = -1;
        for (int i=0; i< index; i++) {
            if (input[index] > input[i]) {
                if (dp[i] > max) {
                    max = dp[i];
                }
            }
        }
        return max == -1? 1 : max + 1;
    }

    /**
     * The longest common subsequence problem is to find the longest subsequence common to
     * all subsequences in a set of sequences (often just two). Note that subsequences is different
     * from a substring.
     * It is the basis of file comparison programs such as diff, and has application in bioinformatics.
     */
    public static int longestCommonSubsequence(String s1, String s2) {
        int [][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i=0; i<=s1.length(); i++) {
            for (int j=0; j<=s2.length(); j++) {
                if (i==0 || j==0) {
                    dp[i][j] = 0;
                } else if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Integer.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    static class Node {
        public static final int LEFT = -1, TOP = 1, DIAGONAL = 0;
        public int value;
        public int direction; // -1 means left, 1 top and 0 means diagonal.

        public Node (int value, int direction) {
            this.value = value;
            this.direction = direction;
        }
    }

    // This print only one but can be augmented to print all longest subsequences.
    // when both left and top are equal weight we are always moving to LEFT
    // By making direction as list, we can move both top and left.
    public static void printLongestCommonSubsequence(String s1, String s2) {

        Node [][] dp = new Node[s1.length() + 1][s2.length() + 1];
        for (int i=0; i<=s1.length(); i++) {
            for (int j=0; j<=s2.length(); j++) {
                if (i==0 || j==0) {
                    dp[i][j] = new Node(0, Node.LEFT);
                } else if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = new Node(1 + dp[i-1][j-1].value, Node.DIAGONAL);
                } else {
                    if (dp[i-1][j].value > dp[i][j-1].value) {
                        dp[i][j] = new Node(dp[i-1][j].value, Node.LEFT);
                    } else {
                        dp[i][j] = new Node(dp[i][j-1].value, Node.TOP);
                    }

                }
            }
        }
        followAndPrint(s1, s2, dp);
    }

    private static void followAndPrint(String s1, String s2, Node[][] dp) {

        int i = s1.length(), j = s2.length();
        while (i > 0 && j > 0) {
            if (dp[i][j].direction == Node.LEFT) {
                i--;
            } else if (dp[i][j].direction == Node.TOP) {
                j--;
            } else {
                System.out.print(s1.charAt(i-1));
                i--; j--;
            }
        }
    }

    /**
     * Given a string print the longest palindromic subsequence in it.
     */
    public static void printLongestPalindromicSequence(String str) {
        printLongestCommonSubsequence(str, Utils.reverseString(str));
    }
}
