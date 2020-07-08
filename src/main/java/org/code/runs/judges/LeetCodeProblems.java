package org.code.runs.judges;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LeetCodeProblems {

    /**
     * https://leetcode.com/problems/minimum-window-substring/
     * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
     * <p>
     * Example:
     * <p>
     * Input: S = "ADOBECODEBANC", T = "ABC"
     * Output: "BANC"
     * Note:
     * <p>
     * If there is no such window in S that covers all characters in T, return the empty string "".
     * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
     */
    static class MinimumWindowSubString {
        public String minWindow(String s, String t) {

            if (t.length() > s.length()) {
                return "";
            }

            // crete hash.
            Map<Character, Integer> patternMap = createPatternMap(t);
            Map<Character, LinkedList<Integer>> occurrenceMap = new HashMap<>();
            int gStart = 0, gEnd = Integer.MAX_VALUE;
            boolean found = false;

            for (int i = 0; i < s.length(); i++) {
                Character ch = s.charAt(i);
                if (patternMap.containsKey(ch)) {
                    checkAndUpdateCharacterOccurrence(occurrenceMap, patternMap, i, ch);
                    if (hasAllItemsOccurred(occurrenceMap, t)) {
                        found = true;

                        // Find minimum and maximum
                        int minIndex = Integer.MAX_VALUE, index;
                        Character windowStartChar = null;
                        for (Map.Entry<Character, LinkedList<Integer>> entry : occurrenceMap.entrySet()) {
                            index = entry.getValue().get(0);
                            if (index < minIndex) {
                                minIndex = index;
                                windowStartChar = entry.getKey();
                            }
                        }

                        // Remove the first occurrence char index.
                        if (occurrenceMap.get(windowStartChar).size() == 1) {
                            occurrenceMap.remove(windowStartChar);
                        } else {
                            occurrenceMap.get(windowStartChar).remove(0);
                        }

                        if (gEnd - gStart > i - minIndex) {
                            gStart = minIndex;
                            gEnd = i;
                        }
                    }
                }
            }
            if (found) {
                return s.substring(gStart, gEnd + 1);
            }
            return "";
        }

        private void checkAndUpdateCharacterOccurrence(Map<Character, LinkedList<Integer>> occurrenceMap, Map<Character, Integer> patternMap, int i, Character ch) {
            // Has not occurred previously
            if (!occurrenceMap.containsKey(ch)) {
                LinkedList<Integer> list = new LinkedList<>();
                list.add(i);
                occurrenceMap.put(ch, list);
            } else {
                LinkedList<Integer> occurrenceList = occurrenceMap.get(ch);

                if (occurrenceList.size() >= patternMap.get(ch)) {
                    // Remove first occurrence and insert i at the end.
                    occurrenceList.remove(0);
                    occurrenceList.addLast(i);
                } else {
                    occurrenceList.addLast(i);
                }
            }
        }

        private boolean hasAllItemsOccurred(Map<Character, LinkedList<Integer>> occurrenceMap, String t) {
            int count = 0;
            for (Map.Entry<Character, LinkedList<Integer>> entry : occurrenceMap.entrySet()) {
                count += entry.getValue().size();
            }
            if (count == t.length()) {
                return true;
            }
            return false;
        }

        private Map<Character, Integer> createPatternMap(String t) {
            Map<Character, Integer> patternMap = new HashMap<>();
            for (int i = 0; i < t.length(); i++) {
                Character ch = t.charAt(i);
                if (patternMap.containsKey(ch)) {
                    patternMap.put(ch, patternMap.get(ch) + 1);
                } else {
                    patternMap.put(ch, 1);
                }
            }
            return patternMap;
        }

    }


    /**
     * https://leetcode.com/problems/trapping-rain-water/
     * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
     * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
     * <p>
     * Example:
     * <p>
     * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     */
    static class TrappingRainWater {
        public int trap(int[] height) {

            if (height.length <= 2) {
                return 0;
            }

            int[] left = new int[height.length];
            int[] right = new int[height.length];

            // Greater on left
            int max = height[0];
            left[0] = height[0];
            for (int i = 1; i < left.length; i++) {
                if (max < height[i]) {
                    max = height[i];
                }
                left[i] = max;
            }

            // Greater on right
            right[height.length - 1] = height[height.length - 1];
            max = height[height.length - 1];
            for (int i = height.length - 2; i >= 0; i--) {
                if (max < height[i]) {
                    max = height[i];
                }
                right[i] = max;
            }

            int area = 0;
            for (int i = 0; i < height.length; i++) {
                area += Math.min(left[i], right[i]) - height[i];
            }
            return area;
        }

    }


    /**
     * https://leetcode.com/problems/sliding-window-maximum/
     * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
     * <p>
     * Example:
     * <p>
     * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation:
     * <p>
     * Window position                Max
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     * Note:
     * You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.
     * <p>
     * Follow up:
     * Could you solve it in linear time?
     * <p>
     * <p>
     * Time Complexity: O(n).
     * It seems more than O(n) at first look. If we take a closer look, we can observe
     * that every element of array is added and removed at most once.
     * So there are total 2n operations.
     */
    static class SlidingWindowMaximum {

        class Pair<K,V> {
            K key;
            V value;

            Pair(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
                return key;
            }

            public V getValue() {
                return value;
            }
        }

        public int[] maxSlidingWindow(int[] nums, int k) {

            if (k == 0 || k > nums.length) {
                return new int[0];
            }

            Deque<Pair<Integer, Integer>> window = new ArrayDeque<>();
            int[] newarray = new int[nums.length - k + 1];

            // Push first k-1 numbers into window.
            for (int i = 0; i < k; i++) {
                updateWindow(window, k, i, nums[i]);
            }

            for (int i = k - 1; i < nums.length; i++) {
                updateWindow(window, k, i, nums[i]);
                newarray[i - k + 1] = window.peekFirst().getValue();
            }
            return newarray;
        }

        private void updateWindow(Deque<Pair<Integer, Integer>> window, int windowSize, int index, int value) {
            // Remove elements from front of window that are more than windowSize distance from index.
            while (!window.isEmpty() && windowSize <= index - window.peekFirst().getKey()) {
                window.removeFirst();
            }

            while (!window.isEmpty() && value > window.peekLast().getValue()) {
                window.removeLast();
            }

            window.addLast(new Pair<>(index, value));
        }
    }


    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
     * (i.e., buy one and sell one share of the stock multiple times).
     *
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     *
     * Example 1:
     *
     * Input: [7,1,5,3,6,4]
     * Output: 7
     * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
     *              Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
     * Example 2:
     *
     * Input: [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
     *              Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
     *              engaging multiple transactions at the same time. You must sell before buying again.
     * Example 3:
     *
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     */
    static class BestTimeToBuyAndSellII {
        public int maxProfit(int[] prices) {
            if (prices.length == 0 || prices.length ==1) {
                return 0;
            }
            int profit = 0;
            int i = 0, buyingPrice = prices[0];
            while (i < prices.length-1) {
                while (i < prices.length-1 && prices[i] >= prices[i+1]) {
                    i++;
                }
                buyingPrice = prices[i];

                while (i < prices.length-1 && prices[i] <= prices[i+1]) {
                    i++;
                }
                profit += prices[i] - buyingPrice;
            }
            return profit;
        }
    }


    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p>
     * Design an algorithm to find the maximum profit. You may complete at most two transactions.
     * <p>
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     * <p>
     * Example 1:
     * <p>
     * Input: [3,3,5,0,0,3,1,4]
     * Output: 6
     * Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
     * Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
     * Example 2:
     * <p>
     * Input: [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
     * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
     * engaging multiple transactions at the same time. You must sell before buying again.
     * Example 3:
     * <p>
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     */
    static class BestTimeToBuyAndSellIII {
        public int maxProfit(int[] prices) {
            if (prices.length < 2) {
                return 0;
            }

            int[] maxLeft = new int[prices.length];
            int[] maxRight = new int[prices.length];

            // Find max profit from 0 to i.
            int min = prices[0];
            maxLeft[0] = 0; // one cannot buy and sell on same day.
            for (int i = 1; i < prices.length; i++) {
                min = Math.min(prices[i], min);
                maxLeft[i] = Math.max(maxLeft[i - 1], prices[i] - min);
            }

            int max = prices[prices.length - 1];
            maxRight[prices.length - 1] = 0; // one cannot buy and sell on last day.
            for (int i = prices.length - 2; i >= 0; i--) {
                maxRight[i] = Math.max(maxRight[i + 1], max - prices[i]);
                max = Math.max(prices[i], max); // i is not included.
            }

            int maxProfit = 0;
            for (int i = 0; i < prices.length; i++) {
                maxProfit = Math.max(maxProfit, maxRight[i] + maxLeft[i]);
            }
            return maxProfit;
        }

        public int maxProfitDP(int[] prices) {

            if (prices.length < 2) {
                return 0;
            }

            int transactions = 2;
            int dp[][] = new int[transactions+1][prices.length];

            for (int t=1; t < dp.length; t++) {
                for (int d = 1; d < dp[0].length; d++) {

                    // When transacting on day-d.
                    int max = Integer.MIN_VALUE;
                    for (int i=0; i<d; i++) {
                        max = Math.max(max, dp[t-1][i] + prices[d] - prices[i]);
                    }

                    // dp[t][d-1] means not transacting on day d.
                    dp[t][d] = Math.max(dp[t][d-1], max);
                }
            }
            return dp[transactions][dp[0].length-1];
        }
    }


    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete at most k transactions.
     *
     * Note:
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     *
     * Example 1:
     *
     * Input: [2,4,1], k = 2
     * Output: 2
     * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
     * Example 2:
     *
     * Input: [3,2,6,5,0,3], k = 2
     * Output: 7
     * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
     *              Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
     */
    static class BestTimeToBuyAndSellIV {
        public int maxProfit(int k, int[] prices) {

            if (k == 0 || prices.length < 2) {
                return 0;
            }

            if (k > prices.length/2) {
                return maxTransactionProfit(prices);
            }

            int transactions = k;
            int dp[][] = new int[transactions+1][prices.length];

            for (int t=1; t < dp.length; t++) {
                for (int d = 1; d < dp[0].length; d++) {
                    for (int i=0; i<d; i++) {
                        dp[t][d] = Math.max(dp[t][d], Math.max(dp[t][d-1], dp[t-1][i] + prices[d] - prices[i]));
                    }
                }
            }
            return dp[transactions][dp[0].length-1];
        }

        private int maxTransactionProfit(int[] prices) {
            int maxProfit = 0;
            for (int i=1; i<prices.length; i++) {
                if (prices[i] > prices[i-1]) {
                    maxProfit += prices[i] - prices[i-1];
                }
            }
            return maxProfit;
        }
    }


    /**
     * https://leetcode.com/problems/dungeon-game/
     * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
     * The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in the
     * top-left room and must fight his way through the dungeon to rescue the princess.
     *
     * The knight has an initial health point represented by a positive integer. If at any point his health point drops
     * to 0 or below, he dies immediately. Some of the rooms are guarded by demons, so the knight loses
     * health (negative integers) upon entering these rooms; other rooms are either empty (0's) or contain magic orbs
     * that increase the knight's health (positive integers).
     *
     * In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
     *
     * Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.
     * For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.
     * -2 (K) -3	3
     * -5	 -10	1
     * 10	  30	-5 (P)
     *
     * Note:
     * The knight's health has no upper bound.
     * Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
     */
    static class DungeonGame {
        public int calculateMinimumHP(int[][] dungeon) {
            int rows = dungeon.length;
            int cols = dungeon[0].length;

            int[][] health = new int[rows][cols];

            // We will fill the matrix from right-most, bottom corner to leftmost top corner cell
            health[rows - 1][cols - 1] = Math.max(1 - dungeon[rows - 1][cols - 1], 1);
            for (int c = cols - 2; c >= 0; c--) {
                // If dungeon[rows-1][c] < 0 then health[rows-1][c+1] - dungeon[rows-1][c] will be greater than 1
                health[rows - 1][c] = Math.max(health[rows - 1][c + 1] - dungeon[rows - 1][c], 1);
            }

            for (int r = rows - 2; r >= 0; r--) {
                health[r][cols - 1] = Math.max(health[r + 1][cols - 1] - dungeon[r][cols - 1], 1);
            }

            for (int r = rows - 2; r >= 0; r--) {
                for (int c = cols - 2; c >= 0; c--) {
                    health[r][c] = Math.min(
                            Math.max(health[r + 1][c] - dungeon[r][c], 1),
                            Math.max(health[r][c + 1] - dungeon[r][c], 1)
                    );
                }
            }
            return health[0][0];
        }
    }


    /**
     * https://leetcode.com/problems/shortest-palindrome/
     * Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it.
     * Find and return the shortest palindrome you can find by performing this transformation.
     * <p>
     * Example 1:
     * <p>
     * Input: "aacecaaa"
     * Output: "aaacecaaa"
     * Example 2:
     * <p>
     * Input: "abcd"
     * Output: "dcbabcd"
     */
    static class ShortestPalindrome {
        public String shortestPalindrome(String s) {
            String reverse = reverseString(s);

            // Best case when string is palindrome.
            if (s.equals(reverse)) {
                return reverse;
            }

            int matchOnwards = -1;
            for (int i = 1; i < s.length(); i++) {
                String substr = reverse.substring(i);
                if (s.startsWith(substr)) {
                    matchOnwards = i;
                    break;
                }
            }

            StringBuilder palindrome = new StringBuilder();
            palindrome.append(reverse.substring(0, matchOnwards)).append(s);
            return palindrome.toString();
        }

        private String reverseString(String s) {
            StringBuilder builder = new StringBuilder();
            for (int i = s.length() - 1; i >= 0; i--) {
                builder.append(s.charAt(i));
            }
            return builder.toString();
        }
    }


    /**
     * https://leetcode.com/problems/the-skyline-problem/
     * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).
     * <p>
     * Buildings  Skyline Contour
     * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
     * <p>
     * For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
     * <p>
     * The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
     * <p>
     * For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
     * <p>
     * Notes:
     * <p>
     * The number of buildings in any input list is guaranteed to be in the range [0, 10000].
     * The input list is already sorted in ascending order by the left x position Li.
     * The output list must be sorted by the x position.
     * There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
     */
    static class SkyLineProblem {

        static class Line {
            int left;
            int height;

            public Line(int l, int h) {
                this.left = l;
                this.height = h;
            }
        }

        public List<int[]> getSkyline(int[][] buildings) {
            if (buildings.length == 0) {
                return new ArrayList<>();
            }
            return convert(findSkyLines(buildings, 0, buildings.length - 1));
        }

        private List<int[]> convert(List<Line> lines) {
            List<int[]> result = new ArrayList<>();
            for (Line line : lines) {
                int[] array = new int[2];
                array[0] = line.left;
                array[1] = line.height;
                result.add(array);
            }
            return result;
        }

        private List<Line> findSkyLines(int[][] buildings, int low, int high) {
            if (low == high) {
                // Only one element is there
                List<Line> list = new ArrayList<>();
                Line one = new Line(buildings[low][0], buildings[low][2]);
                Line two = new Line(buildings[low][1], 0);
                list.add(one);
                list.add(two);
                return list;
            }
            int mid = low + (high - low) / 2;
            List<Line> leftSkyLine = findSkyLines(buildings, low, mid);
            List<Line> rightSkyLine = findSkyLines(buildings, mid + 1, high);
            return mergeSkyLines(leftSkyLine, rightSkyLine);
        }

        private List<Line> mergeSkyLines(List<Line> leftList, List<Line> rightList) {
            List<Line> mergedSkyLine = new ArrayList<>();

            Line prev = null, leftLine, rightLine;
            int lh = -1, rh = -1, maxH = -1;
            while (!leftList.isEmpty() && !rightList.isEmpty()) {
                leftLine = leftList.get(0);
                rightLine = rightList.get(0);

                if (leftLine.left < rightLine.left) {
                    lh = leftLine.height;
                    maxH = Math.max(lh, rh);
                    if (isPartOfSkyLine(leftLine.left, maxH, prev)) {
                        prev = new Line(leftLine.left, maxH);
                        mergedSkyLine.add(prev);
                    }
                    leftList.remove(0);
                } else if (leftLine.left > rightLine.left) {
                    rh = rightLine.height;
                    maxH = Math.max(lh, rh);

                    if (isPartOfSkyLine(rightLine.left, maxH, prev)) {
                        prev = new Line(rightLine.left, maxH);
                        mergedSkyLine.add(prev);
                    }
                    rightList.remove(0);
                } else {
                    int l;
                    lh = leftLine.height;
                    rh = rightLine.height;
                    if (leftLine.height > rightLine.height) {
                        l = leftLine.left;
                    } else {
                        l = rightLine.left;
                    }
                    maxH = Math.max(lh, rh);
                    if (isPartOfSkyLine(l, maxH, prev)) {
                        prev = new Line(l, maxH);
                        mergedSkyLine.add(prev);
                    }
                    leftList.remove(0);
                    rightList.remove(0);
                }
            }

            while (!leftList.isEmpty()) {
                leftLine = leftList.get(0);
                lh = leftLine.height;
                maxH = Math.max(lh, rh);
                if (isPartOfSkyLine(leftLine.left, maxH, prev)) {
                    prev = new Line(leftLine.left, maxH);
                    mergedSkyLine.add(prev);
                }
                leftList.remove(0);
            }

            while (!rightList.isEmpty()) {
                rightLine = rightList.get(0);
                rh = rightLine.height;
                maxH = Math.max(lh, rh);
                if (isPartOfSkyLine(rightLine.left, maxH, prev)) {
                    prev = new Line(rightLine.left, maxH);
                    mergedSkyLine.add(prev);
                }
                rightList.remove(0);
            }

            return mergedSkyLine;
        }

        private boolean isPartOfSkyLine(int left, int height, Line prev) {
            if (prev != null && (prev.height == height || prev.left == left)) {
                return false;
            }
            return true;
        }
    }


    /**
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
     * You are given an integer array nums and you have to return a new counts array. The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
     * <p>
     * Example:
     * <p>
     * Input: [5,2,6,1]
     * Output: [2,1,1,0]
     * Explanation:
     * To the right of 5 there are 2 smaller elements (2 and 1).
     * To the right of 2 there is only 1 smaller element (1).
     * To the right of 6 there is 1 smaller element (1).
     * To the right of 1 there is 0 smaller element.
     */
    static class CountSmallerAfterSelf {
        public List<Integer> countSmaller(int[] nums) {
            return divideAndConquerSolution(nums);
        }

        private List<Integer> divideAndConquerSolution(int[] nums) {

            List<Integer> result = new ArrayList<>(nums.length);
            if (nums.length == 0) {
                return result;
            }

            int[] count = new int[nums.length]; // All elements are 0 by default.
            int[] index = new int[nums.length];
            mergeSort(index, count, nums, 0, nums.length - 1);


            for (int i = 0; i < nums.length; i++) {
                result.add(count[i]);
            }
            return result;

        }

        private void mergeSort(int[] index, int[] count, int[] input, int lo, int hi) {
            if (lo == hi) {
                index[lo] = lo;
                return;
            }
            int mid = lo + (hi - lo) / 2;
            mergeSort(index, count, input, lo, mid);
            mergeSort(index, count, input, mid + 1, hi);
            merge(index, count, input, lo, mid, hi);
        }

        private void merge(int[] index, int[] count, int[] input, int lo, int mid, int hi) {

            List<Integer> indexCopy = new ArrayList<>(hi - lo + 1);
            List<Integer> inputCopy = new ArrayList<>(hi - lo + 1);

            int i = lo, j = mid + 1, c = 0;

            while (i <= mid && j <= hi) {
                // Less than equal is important, having only less than will not lead desired result.
                if (input[i] <= input[j]) {
                    indexCopy.add(index[i]);
                    inputCopy.add(input[i]);
                    // Increment the count by C.
                    count[index[i]] += c;
                    i++;
                } else {
                    indexCopy.add(index[j]);
                    inputCopy.add(input[j]);
                    j++;
                    // Every time I copy from right sub-array, keep a count.
                    c++;
                }
            }

            while (i <= mid) {
                indexCopy.add(index[i]);
                inputCopy.add(input[i]);
                count[index[i]] += c;
                i++;
            }

            while (j <= mid) {
                indexCopy.add(index[j]);
                inputCopy.add(input[j]);
                j++;
            }

            for (int k = 0; k < inputCopy.size(); k++) {
                index[lo + k] = indexCopy.get(k);
                input[lo + k] = inputCopy.get(k);
            }
        }

        private List<Integer> bitSolution(int[] nums) {
            if (nums.length == 0) {
                return new ArrayList<>();
            }
            return createBITAndCount(nums, coordinateCompression(nums));
        }


        /**
         * This method creates a BIT with the help of rank Map. And count the inversions during insertion.
         * Step:
         * 1. Iterate over original input array from right to left. i -> [size-1...0]
         * 1.a. Find index/rank of current element array[i] in sorted array. rankInSortedArray
         * 1.b. Find all elements with rankInSortedArray greater than current element's rankInSortedArray. i.e. bit.query([0..rankInSortedArray-1]).
         * 1.c. Now add current element's rank in BIT.
         */
        private List<Integer> createBITAndCount(int[] array, Map<Integer, Integer> ranks) {
            List<Integer> count = new ArrayList<>(array.length);
            int rankInSortedArray;
            BIT bit = new BIT(ranks.size() + 1);
            for (int i = array.length - 1; i > -1; i--) {
                rankInSortedArray = ranks.get(array[i]);
                count.add(0, bit.query(rankInSortedArray - 1));
                bit.update(rankInSortedArray, 1);
            }
            return count;
        }

        /**
         * This method applies coordinate compression.
         * Steps:
         * 1. Copy the input array to copy[]
         * 2. Sort the copy array.
         * 3. Iterate over sorted array and create a map of key -> element and value -> position in sorted array.
         * When array contain duplicate values then first occurrence is put in map.
         * <p>
         * Example
         * input =     [92, 631, 50, 7]
         * copy =      [7, 50, 92, 631]
         * rankMap     7 => 1, 50 => 2, 92 => 3, 631 => 4
         */
        private Map<Integer, Integer> coordinateCompression(int[] array) {
            int[] copy = Arrays.copyOf(array, array.length);
            Arrays.sort(copy);

            Map<Integer, Integer> rankMap = new HashMap<>();
            for (int i = 0, rank = 1; i < copy.length; i++) {
                if (!rankMap.containsKey(copy[i])) {
                    rankMap.put(copy[i], rank++);
                }
            }
            return rankMap;
        }

        static class BIT {
            private int[] tree;

            public BIT(int size) {
                this.tree = new int[size];
            }

            public void update(int index, int val) {
                for (int i = index; i < tree.length; i += (i & -i)) {
                    tree[i] += val;
                }
            }

            public int query(int index) {
                int val = 0;
                for (int i = index; i > 0; i -= (i & -i)) {
                    val += tree[i];
                }
                return val;
            }
        }
    }


    /**
     * https://leetcode.com/problems/reverse-pairs/
     * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
     * <p>
     * You need to return the number of important reverse pairs in the given array.
     * <p>
     * Example1:
     * <p>
     * Input: [1,3,2,3,1]
     * Output: 2
     * Example2:
     * <p>
     * Input: [2,4,3,5,1]
     * Output: 3
     */
    static class ReversePairs {
        public int reversePairs(int[] nums) {
            if (nums.length <= 1) {
                return 0;
            }

            int[] sortedCopy = Arrays.copyOf(nums, nums.length);
            Arrays.sort(sortedCopy);
            int copyLength = 0;

            // Remove the duplicates from sorted array.
            for (int i = 0; i < sortedCopy.length; i++) {
                if (copyLength == 0 || sortedCopy[copyLength - 1] != sortedCopy[i]) {
                    sortedCopy[copyLength++] = sortedCopy[i];
                }
            }

            BIT bit = new BIT(copyLength + 1);
            int rank, rankOfHalf, result = 0;
            for (int i = nums.length - 1; i > -1; i--) {
                rankOfHalf = getRank(sortedCopy, 0, copyLength, 1.0 * nums[i] / 2);
                rank = getRank(sortedCopy, 0, copyLength - 1, nums[i]);
                result += bit.query(rankOfHalf);
                bit.update(rank + 1, 1);
            }
            return result;
        }

        /**
         * Finds the first index with nums[index] * 2 >= val
         */
        private int getRank(int[] nums, int lo, int hi, double val) {
            int mid;
            while (lo < hi) {
                mid = lo + (hi - lo) / 2;
                if (nums[mid] >= val) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }

        static class BIT {
            private int[] tree;

            public BIT(int size) {
                this.tree = new int[size];
            }

            public void update(int index, int val) {
                for (int i = index; i < tree.length; i += (i & -i)) {
                    tree[i] += val;
                }
            }

            public int query(int index) {
                int val = 0;
                for (int i = index; i > 0; i -= (i & -i)) {
                    val += tree[i];
                }
                return val;
            }
        }
    }


    /**
     * https://leetcode.com/problems/regular-expression-matching/
     * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
     * <p>
     * '.' Matches any single character.
     * '*' Matches zero or more of the preceding element.
     * The matching should cover the entire input string (not partial).
     * <p>
     * Note:
     * <p>
     * s could be empty and contains only lowercase letters a-z.
     * p could be empty and contains only lowercase letters a-z, and characters like . or *.
     * Example 1:
     * <p>
     * Input:
     * s = "aa"
     * p = "a"
     * Output: false
     * Explanation: "a" does not match the entire string "aa".
     */
    static class RegularExpressionMatching {
        public boolean isMatch(String s, String p) {
            return dpPatternMatching(s, p);
        }

        private boolean recursivePatternMatching(String text, String pattern) {
            if (pattern.isEmpty()) {
                return text.isEmpty();
            }

            boolean startMatch = (!text.isEmpty() && doMatch(text.charAt(0), pattern.charAt(0)));

            // When there is no wild-card after first character.
            if (pattern.length() == 1 || pattern.charAt(1) != '*') {
                return startMatch && recursivePatternMatching(text.substring(1), pattern.substring(1));
            }

            /*
             * When there is wild card after first character
             * There are 2 possibilities:
             * 1. Zero occurrence.
             * 2. 1 or more occurrence.
             */
            return recursivePatternMatching(text, pattern.substring(2)) ||
                    (startMatch && recursivePatternMatching(text.substring(1), pattern));
        }

        private boolean dpPatternMatching(String text, String pattern) {
            int rows = text.length() + 1, cols = pattern.length() + 1;
            boolean dp[][] = new boolean[rows][cols];

            // Initialize dp array.
            dp[0][0] = true;
            for (int i = 1; i < rows; i++) {
                dp[i][0] = false;
            }

            for (int j = 1; j < cols; j++) {
                dp[0][j] = pattern.charAt(j - 1) == '*' ? dp[0][j - 2] : false;
            }

            for (int i = 1; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    char p = pattern.charAt(j - 1);
                    char t = text.charAt(i - 1);
                    if (p == '*') {
                        char charBeforeStar = pattern.charAt(j - 2);

                        // if pattern is xa* and text is xaaa
                        // dp [i-1][j] (i=3, j =3) represents that xa* has matched xaa
                        dp[i][j] = dp[i][j - 2]  // zero occurrence
                                || (doMatch(t, charBeforeStar) && dp[i - 1][j]); // 1 or more occurrence
                    } else {
                        dp[i][j] = dp[i - 1][j - 1] && doMatch(t, p);
                    }
                }
            }
            return dp[rows - 1][cols - 1];
        }

        private boolean doMatch(char t, char p) {
            if (t == p || p == '.' || t == '.') {
                return true;
            }
            return false;
        }
    }


    /**
     * https://leetcode.com/problems/partition-equal-subset-sum/
     * Given a non-empty array containing only positive integers, find if the array can be partitioned into
     * two subsets such that the sum of elements in both subsets is equal.
     * <p>
     * Note:
     * Each of the array element will not exceed 100.
     * The array size will not exceed 200.
     * Example 1:
     * <p>
     * Input: [1, 5, 11, 5]
     * <p>
     * Output: true
     * <p>
     * Explanation: The array can be partitioned as [1, 5, 5] and [11].
     */
    static class PartitionEqualSubsetSum {

        /**
         * Question can be converted to find subset of array with sum = total sum of array /2.
         */
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if (sum % 2 != 0) {
                return false;
            }
            return isSubsetWithSum(nums, sum / 2);
        }

        private boolean isSubsetWithSum(int[] set, int target) {
            boolean[][] dp = new boolean[set.length + 1][target + 1];

            dp[0][0] = true;
            for (int i = 0; i <= set.length; i++) {
                for (int j = 0; j <= target; j++) {

                    // Initialize first row and column.
                    if (j == 0) {
                        dp[i][j] = true;
                        continue;
                    }
                    if (i == 0) {
                        dp[i][j] = false;
                        continue;
                    }
                    dp[i][j] = dp[i - 1][j] || (j - set[i - 1] >= 0 && dp[i - 1][j - set[i - 1]]);
                }
            }
            return dp[set.length][target];
        }

        private boolean isSubsetWithSpaceOptimized(int[] nums, int target) {
            boolean dp[] = new boolean[target+1];

            dp[0] = true;
            for (int i=0; i < nums.length; i++) {
                for (int t=target; t > 0; t--) {
                    dp[t] = (dp[t] || (t >= nums[i] && dp[t-nums[i]]));
                }
            }
            return dp[target];
        }
    }


    /**
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k
     * non-empty subsets whose sums are all equal.
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     * 1 <= k <= len(nums) <= 16.
     * 0 < nums[i] < 10000.
     */
    static class PartiotionKEqualSums {

        class DFSSolution {
            public boolean canPartitionKSubsets(int[] nums, int k) {
                int sum = 0;
                boolean[] visited = new boolean[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    sum += nums[i];
                    visited[i] = false;
                }
                if (sum % k != 0) {
                    return false;
                }
                return isSubsetWithSum(nums, visited, sum / k, k, sum / k, 0);
            }

            /**
             * At first this seems like subset sum partitioning problem but backtracking approach failed
             * as there can be multiple sets:
             * Example:
             * For {10,10,10,7,7,7,7,7,7,6,6,6}
             * if the first set you take is 10, 10, 10 = 30 then there is no other solution but if you take
             * 10 + 6 + 14 (7,7)
             * 10 + 6 + 14 (7,7)
             * 10 + 6 + 14 (7,7)
             * Then this array can be partitioned into 3 subset of equal sum.
             * <p>
             * This solution performs simple dfs.
             */
            private boolean isSubsetWithSum(int[] set, boolean[] visited, int target, int k, int targetSum, int index) {
                if (k == 1) {
                    return true;
                }

                if (target < 0) {
                    return false;
                }

                if (target == 0) {
                    return isSubsetWithSum(set, visited, targetSum, k - 1, targetSum, 0);
                }

                for (int i = index; i < set.length; i++) {
                    if (!visited[i]) {
                        visited[i] = true;
                        if (isSubsetWithSum(set, visited, target - set[i], k, targetSum, index + 1)) {
                            return true;
                        }
                        visited[i] = false;
                    }
                }
                return false;
            }

        }

        class ExhaustiveChoiceSolution {

            private int[] nums;
            private int target;
            private int groups;

            public boolean canPartitionKSubsets(int[] nums, int k) {
                this.nums = nums;
                this.groups = k;

                int sum = Arrays.stream(nums).sum();
                if (sum % k != 0) {
                    return false;
                }
                this.target = sum/k;
                return isSubsetWithSum(new int[groups], 0);
            }

            private boolean isSubsetWithSum(int[] subsets, int index) {
                if (index == nums.length) {
                    return isSolved(subsets);
                }

                // As per free choice, a index in array can be part of any of the k subset
                // Try all of them
                for (int k=0; k<subsets.length; k++) {
                    if (subsets[k] + nums[index] <= target) {

                        // Make a choice to add index to subset-k
                        subsets[k] += nums[index];
                        if (isSubsetWithSum(subsets, index+1)) {
                            return true;
                        }

                        // If this doesn't work out then undo this choice.
                        subsets[k] -= nums[index];
                    }

                }
                return false;
            }

            private boolean isSolved(int[] subsets) {
                for (int i: subsets) {
                    if (i != target) {
                        return false;
                    }
                }
                return true;
            }

        }

        enum Result { TRUE, FALSE }
        class ExhaustiveChoiceSolutionsOptimized {

            boolean search(int used, int todo, Result[] memo, int[] nums, int target) {
                if (memo[used] == null) {
                    memo[used] = Result.FALSE;
                    int targ = (todo - 1) % target + 1;
                    for (int i = 0; i < nums.length; i++) {
                        if ((((used >> i) & 1) == 0) && nums[i] <= targ) {
                            if (search(used | (1<<i), todo - nums[i], memo, nums, target)) {
                                memo[used] = Result.TRUE;
                                break;
                            }
                        }
                    }
                }
                return memo[used] == Result.TRUE;
            }

            public boolean canPartitionKSubsets(int[] nums, int k) {
                int sum = Arrays.stream(nums).sum();
                if (sum % k > 0) return false;

                Result[] memo = new Result[1 << nums.length];
                memo[(1 << nums.length) - 1] = Result.TRUE;
                return search(0, sum, memo, nums, sum / k);
            }
        }
    }


    /**
     * https://leetcode.com/problems/russian-doll-envelopes/
     * You have a number of envelopes with widths and heights given as a pair of integers (w, h).
     * One envelope can fit into another if and only if both the width and height of one envelope
     * is greater than the width and height of the other envelope.
     *
     * What is the maximum number of envelopes can you Russian doll? (put one inside other)
     *
     * Note:
     * Rotation is not allowed.
     *
     * Example:
     * Input: [[5,4],[6,4],[6,7],[2,3]]
     * Output: 3
     * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
     */
    static class RussianDollEnvelopes {
        private static final int WIDTH = 0;
        private static final int HEIGHT = 1;

        public int maxEnvelopes(int[][] envelopes) {
            if (envelopes.length == 0) {
                return 0;
            }
            Arrays.sort(envelopes, (a,b) -> Integer.compare(a[0], b[0]));

            int[] dp = new int[envelopes.length];
            int max = 0;
            dp[0] = 0;

            for (int i = 1; i < envelopes.length; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (envelopes[i][WIDTH] > envelopes[j][WIDTH] && envelopes[i][HEIGHT] > envelopes[j][HEIGHT]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                max = Math.max(max, dp[i]);
            }
            return max + 1;
        }
    }


    /**
     * https://leetcode.com/problems/burst-balloons/
     * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums.
     * You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins.
     * Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
     * <p>
     * Find the maximum coins you can collect by bursting the balloons wisely.
     * <p>
     * Note:
     * <p>
     * You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
     * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
     * Example:
     * <p>
     * Input: [3,1,5,8]
     * Output: 167
     * Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
     * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
     */
    static class BurstBalloons {
        public int maxCoins(int[] coins) {
            if (coins.length == 0) {
                return 0;
            }
            return maxCoinsBottomUp(coins);
        }

        private int maxCoinsBottomUp(int[] coins) {
            int[][] dp = new int[coins.length][coins.length];

            for (int e = 0; e < dp.length; e++) {
                for (int s = e; s >= 0; s--) {
                    for (int i = s; i <= e; i++) {
                        int leftSum = sumAt(dp, s, i - 1);
                        int rightSum = sumAt(dp, i + 1, e);
                        dp[s][e] = Math.max(dp[s][e], leftSum + rightSum + coinAt(coins, s - 1) * coinAt(coins, i) * coinAt(coins, e + 1));
                    }
                }
            }
            return dp[0][dp.length - 1];
        }

        private int maxCoinsTopDown(int[][] dp, int[] coins, int start, int end) {
            if (start > end) {
                return 0;
            }

            if (dp[start][end] != 0) {
                return dp[start][end];
            }

            for (int i = start; i <= end; i++) {
                int val = maxCoinsTopDown(dp, coins, start, i - 1) +
                        coinAt(coins, start - 1) * coinAt(coins, i) * coinAt(coins, end + 1) +
                        maxCoinsTopDown(dp, coins, i + 1, end);
                dp[start][end] = Math.max(dp[start][end], val);
            }
            return dp[start][end];
        }

        private int sumAt(int[][] dp, int r, int c) {
            if (r < 0 || c < 0 || r >= dp.length || c >= dp.length) {
                return 0;
            }
            return dp[r][c];
        }

        private int coinAt(int[] coins, int index) {
            if (index < 0 || index >= coins.length) {
                return 1;
            }
            return coins[index];
        }
    }


    /**
     * https://leetcode.com/problems/queue-reconstruction-by-height/
     * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k),
     * where h is the height of the person and k is the number of people in front of this person who have a height greater
     * than or equal to h. Write an algorithm to reconstruct the queue.
     * <p>
     * Note: The number of people is less than 1,100.
     * <p>
     * Example
     * Input:
     * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     * Output:
     * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     */
    static class QueueReconstructionByHeight {
        public int[][] reconstructQueue(int[][] people) {
            Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
            int[][] result = new int[people.length][2];
            initializeMatrix(result);

            for (int i = 0; i < people.length; i++) {
                int pos = findPosition(result, people[i][0], people[i][1]);
                result[pos][0] = people[i][0];
                result[pos][1] = people[i][1];
            }
            return result;
        }

        private int findPosition(int[][] result, int height, int pos) {
            int emptySpaces = -1;
            for (int i = 0; i < result.length; i++) {
                if (result[i][0] == -1 || result[i][0] == height) {
                    emptySpaces++;
                }

                if (emptySpaces == pos) {
                    return i;
                }
            }
            return result.length - 1;
        }

        private void initializeMatrix(int[][] result) {
            for (int i = 0; i < result.length; i++) {
                result[i][0] = -1;
                result[i][1] = -1;
            }
        }
    }


    /**
     * https://leetcode.com/problems/median-of-two-sorted-arrays/submissions/
     * There are two sorted arrays nums1 and nums2 of size m and n respectively.
     * <p>
     * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
     * You may assume nums1 and nums2 cannot be both empty.
     * <p>
     * Example 1:
     * nums1 = [1, 3]
     * nums2 = [2]
     * <p>
     * The median is 2.0
     * Example 2:
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     * <p>
     * The median is (2 + 3)/2 = 2.5
     */
    static class MedianOfTwoSortedArrays {

        int[] a; // a is always the smaller array.
        int[] b;
        int medianElement;
        boolean isEven;

        public double findMedianSortedArrays(int[] num1, int[] num2) {
            // Ensure a is always the smaller one.
            if (num1.length < num2.length) {
                a = num1;
                b = num2;
            } else {
                a = num2;
                b = num1;
            }

            medianElement = (a.length + b.length + 1) / 2;
            isEven = (a.length + b.length) % 2 == 0;

            // case-0: when one of the array is empty
            Double potentialMedian = medianWhenAisEmpty();
            if (potentialMedian != null) {
                return potentialMedian;
            }

            // case-1: when all elements from A are included.
            potentialMedian = medianIfAllElementsFromA();
            if (potentialMedian != null) {
                return potentialMedian;
            }

            // case-2: when no element from A is included.
            potentialMedian = medianIfNoElementFromA();
            if (potentialMedian != null) {
                return potentialMedian;
            }

            // case-3: when some elements from A are included and some (one/more) from B is included.
            return distributedMedian();
        }

        private Double medianIfNoElementFromA() {
            if (medianElement <= b.length && a[0] >= b[medianElement - 1]) {
                if (!isEven) {
                    return Double.valueOf(b[medianElement - 1]);
                } else if (medianElement == b.length) {
                    return Double.valueOf((b[medianElement - 1] + a[0]) / 2.0);
                } else {
                    return Double.valueOf((b[medianElement - 1] + Math.min(b[medianElement], a[0])) / 2.0);
                }
            }
            return null;
        }

        private Double medianIfAllElementsFromA() {
            int indexB = medianElement == a.length ? 0 : medianElement - a.length - 1;
            // if below condition is true then all elements of A are included.
            if (medianElement >= a.length && a[a.length - 1] <= b[indexB]) {
                if (!isEven) {
                    if (medianElement == a.length) {
                        return Double.valueOf(a[a.length - 1]);
                    } else {
                        return Double.valueOf(b[indexB]);
                    }
                } else {
                    if (medianElement == a.length) {
                        return Double.valueOf((a[a.length - 1] + b[0]) / 2.0);
                    } else {
                        return Double.valueOf((b[indexB] + b[indexB + 1]) / 2.0);
                    }
                }
            }
            return null;
        }

        private Double medianWhenAisEmpty() {
            if (a.length == 0) {
                if (isEven) {
                    return Double.valueOf((b[medianElement] + b[medianElement - 1]) / 2.0);
                }
                return Double.valueOf(b[medianElement - 1]);
            }
            return null;
        }

        private Double distributedMedian() {
            int posA = 0, posB = 0;
            int lo = 0, hi = a.length;

            while (lo <= hi) {
                posA = lo + (hi - lo) / 2;
                posB = medianElement - (posA + 1) - 1;

                // case-1: shift left in A.
                if (posB < b.length - 1 && a[posA] > b[posB + 1]) {
                    hi = posA - 1;
                } else if (posA < a.length - 1 && a[posA + 1] < b[posB]) {
                    lo = posA + 1;
                } else {
                    int maxLeft = Math.max(a[posA], b[posB]);
                    if (!isEven) {
                        return Double.valueOf(maxLeft);
                    } else {
                        int maxRight = Math.min(
                                posA + 1 <= a.length - 1 ? a[posA + 1] : Integer.MAX_VALUE,
                                posB + 1 <= b.length - 1 ? b[posB + 1] : Integer.MAX_VALUE
                        );
                        return Double.valueOf((maxLeft + maxRight) / 2.0);
                    }
                }
            }
            return 0d;
        }
    }


    /**
     * https://leetcode.com/problems/range-sum-query-2d-immutable/
     * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
     * <p>
     * Range Sum Query 2D
     * The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.
     * <p>
     * Example:
     * Given matrix = [
     * [3, 0, 1, 4, 2],
     * [5, 6, 3, 2, 1],
     * [1, 2, 0, 1, 5],
     * [4, 1, 0, 1, 7],
     * [1, 0, 3, 0, 5]
     * ]
     * <p>
     * sumRegion(2, 1, 4, 3) -> 8
     * sumRegion(1, 1, 2, 2) -> 11
     * sumRegion(1, 2, 2, 4) -> 12
     * Note:
     * You may assume that the matrix does not change.
     * There are many calls to sumRegion function.
     * You may assume that row1 ≤ row2 and col1 ≤ col2.
     */
    static class RangeSumQuery2D {

        static class CacheSolution {
            private int[][] cache;

            public CacheSolution(int[][] matrix) {
                constructCache(matrix);
            }

            public void constructCache(int[][] matrix) {
                if (matrix.length == 0 || matrix[0].length == 0) return;
                cache = new int[matrix.length + 1][matrix[0].length + 1];
                for (int r = 0; r < matrix.length; r++) {
                    for (int c = 0; c < matrix[0].length; c++) {
                        cache[r + 1][c + 1] = cache[r + 1][c] + cache[r][c + 1] + matrix[r][c] - cache[r][c];
                    }
                }
            }

            public int sumRegion(int row1, int col1, int row2, int col2) {
                return cache[row2 + 1][col2 + 1] - cache[row1][col2 + 1] - cache[row2 + 1][col1] + cache[row1][col1];
            }
        }

        static class BITSolution {

            class BIT2D {
                int[][] tree;
                int[][] input;

                public BIT2D(int[][] input) {
                    if (input.length == 0) {
                        return;
                    }
                    tree = new int[input.length + 1][input[0].length + 1];
                    for (int i = 0; i < input.length; i++) {
                        for (int j = 0; j < input[0].length; j++) {
                            update(i + 1, j + 1, input[i][j]);
                        }
                    }
                }

                public void update(int x, int y, int val) {
                    for (int i = x; i < tree.length; i += (i & -i)) {
                        for (int j = y; j < tree[0].length; j += (j & -j)) {
                            tree[i][j] += val;
                        }
                    }
                }

                public int query(int x, int y) {
                    int sum = 0;
                    for (int i = x; i > 0; i -= (i & -i)) {
                        for (int j = y; j > 0; j -= (j & -j)) {
                            sum += tree[i][j];
                        }
                    }
                    return sum;
                }
            }

            BIT2D bit;
            int[][] grid;

            public BITSolution(int[][] matrix) {
                this.grid = matrix;
                bit = new BIT2D(matrix);
            }

            public int sumRegion(int row1, int col1, int row2, int col2) {
                if (grid.length == 0) {
                    return 0;
                }
                int r1 = row1 + 1, r2 = row2 + 1, c1 = col1 + 1, c2 = col2 + 1;
                return bit.query(r2, c2) - bit.query(r2, c1 - 1) - bit.query(r1 - 1, c2) + bit.query(r1 - 1, c1 - 1);
            }
        }
    }


    /**
     * https://leetcode.com/problems/sort-characters-by-frequency/
     * Given a string, sort it in decreasing order based on the frequency of characters.
     * <p>
     * Example 1:
     * <p>
     * Input:
     * "tree"
     * <p>
     * Output:
     * "eert"
     * <p>
     * Explanation:
     * 'e' appears twice while 'r' and 't' both appear once.
     * So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
     * Example 2:
     * <p>
     * Input:
     * "cccaaa"
     * <p>
     * Output:
     * "cccaaa"
     * <p>
     * Explanation:
     * Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
     * Note that "cacaca" is incorrect, as the same characters must be together.
     * Example 3:
     * <p>
     * Input:
     * "Aabb"
     * <p>
     * Output:
     * "bbAa"
     * <p>
     * Explanation:
     * "bbaA" is also a valid answer, but "Aabb" is incorrect.
     * Note that 'A' and 'a' are treated as two different characters.
     */
    class SortCharacterByFrequency {

        class Pair {
            char ch;
            int count;

            public Pair(char ch, int count) {
                this.ch = ch;
                this.count = count;
            }
        }

        public String frequencySort(String s) {
//        return arraySortSolution(s);
            return heapSolution(s);
        }

        public String arraySortSolution(String s) {
            Map<Character, Integer> charMap = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                int count = 0;
                if (charMap.containsKey(ch)) {
                    count = charMap.get(ch);
                }
                charMap.put(ch, count + 1);
            }

            Pair[] pairs = new Pair[charMap.size()];
            int len = 0;
            for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
                pairs[len++] = new Pair(entry.getKey(), entry.getValue());
            }

            Comparator<Pair> comparator = (a, b) -> b.count - a.count;
            Arrays.sort(pairs, comparator);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < pairs.length; i++) {
                for (int j = 0; j < pairs[i].count; j++) {
                    result.append(pairs[i].ch);
                }
            }
            return result.toString();
        }

        public String heapSolution(String s) {
            // Step-1: Create a map.
            Map<Character, Integer> charMap = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                int count = 0;
                if (charMap.containsKey(ch)) {
                    count = charMap.get(ch);
                }
                charMap.put(ch, count + 1);
            }

            // Step-2: Create a priority queue
            Comparator<Map.Entry<Character, Integer>> comparator = (a, b) -> b.getValue() - a.getValue();
            PriorityQueue<Map.Entry<Character, Integer>> heap = new PriorityQueue<>(comparator);
            heap.addAll(charMap.entrySet());

            // Step-3: Create result
            StringBuilder result = new StringBuilder();
            while (!heap.isEmpty()) {
                Map.Entry<Character, Integer> entry = heap.poll();
                for (int j = 0; j < entry.getValue(); j++) {
                    result.append(entry.getKey());
                }
            }
            return result.toString();
        }
    }


    /**
     * https://leetcode.com/problems/top-k-frequent-elements/
     * Given a non-empty array of integers, return the k most frequent elements.
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     * Example 2:
     * <p>
     * Input: nums = [1], k = 1
     * Output: [1]
     * Note:
     * <p>
     * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
     * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
     */
    class KFrequentElements {
        public List<Integer> topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> countMap = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int count = countMap.getOrDefault(nums[i], Integer.valueOf(0));
                countMap.put(nums[i], count + 1);
            }

            Comparator<Map.Entry<Integer, Integer>> comparator = (a, b) -> b.getValue() - a.getValue();
            PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue(comparator);
            // Instead of doing add all we can limit it to k elements.
            heap.addAll(countMap.entrySet());

            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < k && !heap.isEmpty(); i++) {
                result.add(heap.poll().getKey());
            }
            return result;
        }
    }


    /**
     * https://leetcode.com/problems/circular-array-loop/
     * <p>
     * You are given a circular array nums of positive and negative integers. If a number k at an index is positive,
     * then move forward k steps. Conversely, if it's negative (-k), move backward k steps. Since the array is circular,
     * you may assume that the last element's next element is the first element, and the first element's previous element is the last element.
     * <p>
     * Determine if there is a loop (or a cycle) in nums. A cycle must start and end at the same index and the cycle's length > 1.
     * Furthermore, movements in a cycle must all follow a single direction. In other words, a cycle must not consist of both forward and backward movements.
     * <p>
     * Example 1:
     * <p>
     * Input: [2,-1,1,2,2]
     * Output: true
     * Explanation: There is a cycle, from index 0 -> 2 -> 3 -> 0. The cycle's length is 3.
     * Example 2:
     * <p>
     * Input: [-1,2]
     * Output: false
     * Explanation: The movement from index 1 -> 1 -> 1 ... is not a cycle, because the cycle's length is 1. By definition the cycle's length must be greater than 1.
     * Example 3:
     * <p>
     * Input: [-2,1,-1,-2,-2]
     * Output: false
     * Explanation: The movement from index 1 -> 2 -> 1 -> ... is not a cycle, because movement from index 1 -> 2 is a forward movement, but movement from index 2 -> 1 is a backward movement. All movements in a cycle must follow a single direction.
     */
    class CircularArrayLoop {

        class DFSSolution {
            boolean visited[];
            int[] nums;
            int len;

            public boolean circularArrayLoop(int[] nums) {
                this.len = nums.length;
                this.visited = new boolean[len];
                this.nums = nums;

                int s = 0;
                int n = 0;
                Set<Integer> parent = new HashSet<>();
                while (true) {
                    s = nextStartIndex();
                    if (s == -1) {
                        return false;
                    }
                    n = nextJumpPosition(s);
                    visited[s] = true;
                    parent.clear();
                    parent.add(s);
                    if (s != n && dfs(s, n, nums[s] > 0, parent)) {
                        return true;
                    }
                }
            }

            private boolean dfs(int startIndex, int index, boolean isPositive, Set<Integer> parent) {
                if (parent.contains(index)) {
                    return true;
                }

                if ((isPositive && nums[index] < 0) || (!isPositive && nums[index] > 0)) {
                    return false;
                }

                if (parent.size() == visited.length) {
                    return false;
                }

                parent.add(index);
                visited[index] = true;
                int nextIndex = nextJumpPosition(index);

                // self loop.
                if (nextIndex == index) {
                    return false;
                }
                return dfs(startIndex, nextIndex, isPositive, parent);
            }

            private int nextStartIndex() {
                for (int i = 0; i < nums.length; i++) {
                    if (!visited[i]) {
                        return i;
                    }
                }
                return -1;
            }

            private int nextJumpPosition(int index) {
                int nextIndex = (index + nums[index]) % len; // check this for -ve numbers
                if (nextIndex < 0) {
                    return len + nextIndex;
                }
                return nextIndex;
            }
        }

        class NoExtraSpaceSolution {

            final int SENTINEL = 10000;
            int[] nums;
            int len;

            public boolean circularArrayLoop(int[] nums) {
                this.len = nums.length;
                this.nums = nums;

                for (int i = 0; i < nums.length; i++) {
                    if (nums[i] > SENTINEL) {
                        continue;
                    }

                    if (search(i, nums[i], SENTINEL + i + 1)) {
                        return true;
                    }
                }
                return false;
            }

            private boolean search(int index, int direction, int cycleCode) {
                if (nums[index] == cycleCode) {
                    return true;
                }

                if (nums[index] > SENTINEL || direction * nums[index] < 0) {
                    return false;
                }

                int nextIndex = nextJumpPosition(index);
                nums[index] = cycleCode;

                // self loop.
                if (nextIndex == index) {
                    return false;
                }
                return search(nextIndex, direction, cycleCode);
            }

            private int nextJumpPosition(int index) {
                int nextIndex = (index + nums[index]) % len;
                if (nextIndex < 0) {
                    return len + nextIndex;
                }
                return nextIndex;
            }
        }

        class FastAndSlowPointerSolution {
            public boolean circularArrayLoop(int[] nums) {
                if (nums == null || nums.length < 2) {
                    return false;
                }

                int slow, fast, N = nums.length;
                for (int i = 0; i < N; i++) {
                    slow = fast = i;
                    do {
                        slow = moveIndex(slow, nums[slow], N);
                        fast = moveIndex(fast, nums[fast], N);
                        fast = moveIndex(fast, nums[fast], N);
                    } while (slow != fast);

                    if (nums[slow] % N != 0 && uniDirectionLoop(slow, nums, N)) {
                        return true;
                    }
                }

                return false;
            }

            private boolean uniDirectionLoop(int slow, int[] nums, int N) {
                int current = slow;
                do {
                    current = moveIndex(current, nums[current], N);
                    if (nums[current] > 0 ^ nums[slow] > 0) {
                        return false;
                    }
                } while (current != slow);

                return true;
            }

            private int moveIndex(int index, int value, int N) {
                index += value;
                index %= N;

                if (index < 0) {
                    index += N;
                }
                return index;
            }
        }
    }


    /**
     * https://leetcode.com/problems/ipo/
     * Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital,
     * LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources,
     * it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize
     * its total capital after finishing at most k distinct projects.
     *
     * You are given several projects. For each project i, it has a pure profit Pi and a minimum capital of Ci is needed
     * to start the corresponding project. Initially, you have W capital. When you finish a project, you will obtain its
     * pure profit and the profit will be added to your total capital.
     *
     * To sum up, pick a list of at most k distinct projects from given projects to maximize your final capital,
     * and output your final maximized capital.
     *
     * Example 1:
     * Input: k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].
     *
     * Output: 4
     *
     * Explanation: Since your initial capital is 0, you can only start the project indexed 0.
     *              After finishing it you will obtain profit 1 and your capital becomes 1.
     *              With capital 1, you can either start the project indexed 1 or the project indexed 2.
     *              Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
     *              Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
     * Note:
     * You may assume all numbers in the input are non-negative integers.
     * The length of Profits array and Capital array will not exceed 50,000.
     * The answer is guaranteed to fit in a 32-bit signed integer.
     */
    static class IPO {

        static class Project {
            int capital;
            int profit;

            public Project(int c, int p) {
                this.capital = c;
                this.profit = p;
            }
        }

        static class PriorityQueueSolution {

            public int findMaximizedCapital(int k, int w, int[] profits, int[] capitals) {
                List<Project> projects = createProjectList(profits, capitals);
                projects = projects.stream().sorted(Comparator.comparingInt(p -> p.capital)).collect(Collectors.toList());

                PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a,b) -> b - a);
                int totalCapital = w;
                int index = addToQueue(projects, priorityQueue, 0, totalCapital);

                while (k > 0 && !priorityQueue.isEmpty()) {
                    totalCapital += priorityQueue.poll();
                    index = addToQueue(projects, priorityQueue, index, totalCapital);
                    k--;
                }
                return totalCapital;
            }

            private List<Project> createProjectList(int[] profits, int[] capitals) {
                List<Project> projectList = new ArrayList<>(profits.length);
                for (int i = 0; i < profits.length; i++) {
                    projectList.add(new Project(capitals[i], profits[i]));
                }
                return projectList;
            }

            private int addToQueue(List<Project> projects, PriorityQueue<Integer> priorityQueue, int index, int totalCapital) {
                while (index < projects.size() && projects.get(index).capital <= totalCapital) {
                    priorityQueue.add(projects.get(index).profit);
                    index++;
                }
                return index;
            }
        }

        static class BinarySearchSolution {
            public int findMaximizedCapital(int k, int w, int[] profits, int[] capitals) {

                List<Project> projects = createProjectList(profits, capitals);
                projects = projects.stream().sorted(Comparator.comparingInt(p -> p.capital)).collect(Collectors.toList());

                int totalCapital = w;
                while (k > 0) {
                    int p = findMaxProfitProject(projects, totalCapital);

                    if (p == -1) {
                        break;
                    }

                    // Update totalCapital and startCapital.
                    totalCapital += projects.get(p).profit;

                    // Move these elements to the end of array so they are not considered again.
                    projects.remove(p);

                    // We have selected a project.
                    k--;
                }
                return totalCapital;
            }

            private List<Project> createProjectList(int[] profits, int[] capitals) {
                List<Project> projectList = new ArrayList<>(profits.length);
                for (int i = 0; i < profits.length; i++) {
                    projectList.add(new Project(capitals[i], profits[i]));
                }
                return projectList;
            }

            private int findMaxProfitProject(List<Project> projects, int capital) {
                int index = binarySearch(projects, capital);
                if (index < 0) {
                    return -1;
                }
                int maxIndex = 0;
                for (int i = 0; i <= index; i++) {
                    if (projects.get(i).profit > projects.get(maxIndex).profit) {
                        maxIndex = i;
                    }
                }
                return maxIndex;
            }

            private int binarySearch(List<Project> projects, int capital) {

                if (projects.isEmpty() || projects.get(0).capital > capital) {
                    return -1;
                }

                int lo = 0, hi = projects.size() - 1, mid = 0;
                while (lo <= hi) {
                    mid = lo + (hi - lo) / 2;

                    if (projects.get(mid).capital <= capital) {
                        if (mid + 1 <= hi && projects.get(mid + 1).capital <= capital) {
                            lo = mid + 1;
                        } else {
                            return mid;
                        }
                    } else {
                        hi = mid - 1;
                    }
                }
                return -1;
            }
        }

        static class TreeSetSolution {
            class Project {
                int capital;
                int profit;

                public Project(int c, int p) {
                    this.capital = c;
                    this.profit = p;
                }
            }

            /**
             * TreeSet doesn't use equals method instead use the CompareTo method
             * for checking if the item exist. Hence, we have to maintain the equals
             * functionality in compareTo method itself.
             * https://stackoverflow.com/questions/16593406/treeset-add-returning-false
             */
            Comparator<Project> capComp = (a, b) -> {
                if (a.capital == b.capital && a.profit == b.profit) {
                    return a.hashCode() - b.hashCode();
                }

                if (a.capital == b.capital) {
                    return b.profit - a.profit;
                }
                return a.capital - b.capital;
            };
            Comparator<Project> profitComp = (a,b) -> b.profit - a.profit;

            public int findMaximizedCapital(int k, int cap, int[] profits, int[] capital) {

                // Initialization.
                TreeSet<Project> allProjects = new TreeSet<>(capComp);
                boolean added = false;
                for (int i=0; i<capital.length; i++) {
                    added = false;
                    added = allProjects.add(new Project(capital[i], profits[i]));
                }
                System.out.println(added);

                // Create a Priority queue and add all projects with capital less than equal to cap.
                PriorityQueue<Project> projects = new PriorityQueue<>(profitComp);
                addProjectsToQueue(projects, allProjects, cap);

                while (k > 0 && !projects.isEmpty()) {
                    cap += projects.poll().profit;
                    k--;
                    addProjectsToQueue(projects, allProjects, cap);
                }
                return cap;
            }

            private void addProjectsToQueue(PriorityQueue<Project> projects, TreeSet<Project> allProjects, int cap) {
                while (!allProjects.isEmpty() && allProjects.first().capital <= cap) {
                    projects.offer(allProjects.pollFirst());
                }
            }
        }
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
    static class CoinChange2 {

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
     *You are given coins of different denominations and a total amount of money amount.
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
    static class CoinChange {
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


    /**
     * https://leetcode.com/problems/array-nesting/
     * A zero-indexed array A of length N contains all integers from 0 to N-1.
     * Find and return the longest length of set S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... }
     * subjected to the rule below.
     *
     * Suppose the first element in S starts with the selection of element A[i] of index = i,
     * the next element in S should be A[A[i]], and then A[A[A[i]]]… By that analogy, we stop adding right before
     * a duplicate element occurs in S.
     *
     * Example 1:
     *
     * Input: A = [5,4,0,3,1,6,2]
     * Output: 4
     * Explanation:
     * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
     *
     * One of the longest S[K]:
     * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
     *
     *
     * Note:
     *
     * N is an integer within the range [1, 20,000].
     * The elements of A are all distinct.
     * Each element of A is an integer within the range [0, N-1].
     */
    static class ArrayNesting {
        public int arrayNesting(int[] nums) {
            int count;
            int maxCount = 0;
            for (int i=0; i<nums.length; i++) {
                // count =1, when the element is at right position. a[i] = i.
                count = 1;
                // Swap until you find a cycle.
                while (nums[i] != i) {
                    swap(nums, i, nums[i]);
                    count++;
                }
                maxCount = Math.max(maxCount, count);
            }
            return maxCount;
        }

        // We can make it faster by using bit operators for swapping and avoiding function call.
        private void swap(int[] arr, int x, int y) {
            int temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
        }
    }


    /**
     * Given an unsorted integer array, find the smallest missing positive integer.
     *
     * Example 1:
     *
     * Input: [1,2,0]
     * Output: 3
     * Example 2:
     *
     * Input: [3,4,-1,1]
     * Output: 2
     * Example 3:
     *
     * Input: [7,8,9,11,12]
     * Output: 1
     * Note:
     *
     * Your algorithm should run in O(n) time and uses constant extra space.
     */
    static class FirstMissingPositive {
        public int firstMissingPositive(int[] nums) {
            int sentinel = Integer.MIN_VALUE;
            int maxN = nums.length;
            int correctPos = 0;

            int i=0;
            while (i < nums.length) {
                if (nums[i]-1 == i) {
                    nums[i] = sentinel;
                    i++;
                } else if (nums[i] > maxN || nums[i] <= 0) {
                    // Not in consideration range
                    i++;
                } else {
                    correctPos = nums[i] -1;

                    if (nums[correctPos] != sentinel) {
                        nums[i] = nums[correctPos];
                        nums[correctPos] = sentinel;
                    } else {
                        i++;
                    }

                }
            }

            for (int j=0; j<maxN; j++) {
                if (nums[j] != sentinel) {
                    return j+1;
                }
            }
            return maxN+1;
        }
    }


    /**
     * https://leetcode.com/problems/largest-rectangle-in-histogram/submissions/
     * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1,
     * find the area of largest rectangle in the histogram.
     *
     * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
     *
     * The largest rectangle is shown in the shaded area, which has area = 10 unit.
     *
     *  Example:
     * Input: [2,1,5,6,2,3]
     * Output: 10
     */
    static class LargestRectangleInHistogram {

        class Pair<K,V> {
            K index;
            V height;
            Pair(K index, V value) { this.index = index; this.height = value; }
        }

        public int largestRectangleArea(int[] heights) {
            int maxArea = 0, width, height;
            Stack<Pair<Integer, Integer>> stack = new Stack<>();

            for (int i=0; i<heights.length; i++) {
                while (!stack.isEmpty() && stack.peek().height >= heights[i]) {
                    Pair<Integer, Integer> top = stack.pop();
                    width = stack.isEmpty() ? i : i - stack.peek().index - 1;
                    height = top.height;
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(new Pair<>(i, heights[i]));
            }

            while (!stack.isEmpty()) {
                Pair<Integer, Integer> top = stack.pop();
                height = top.height;
                width = stack.isEmpty() ?  heights.length : heights.length - stack.peek().index - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            return maxArea;
        }
    }


    /**
     * https://leetcode.com/problems/longest-valid-parentheses/
     * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
     *
     * Example 1:
     * Input: "(()"
     * Output: 2
     * Explanation: The longest valid parentheses substring is "()"
     *
     * Example 2:
     * Input: ")()())"
     * Output: 4
     *
     * Explanation: The longest valid parentheses substring is "()()"
     */
    static class LongestValidParenthesis {
        public int longestValidParentheses(String s) {
            int maxans = 0;
            Stack<Integer> stack = new Stack<>();
            stack.push(-1);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    stack.push(i);
                } else {
                    stack.pop();
                    if (stack.empty()) {
                        stack.push(i);
                    } else {
                        maxans = Math.max(maxans, i - stack.peek());
                    }
                }
            }
            return maxans;
        }
    }


    /**
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     *
     * Example:
     *
     * Input:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * Output: 1->1->2->3->4->4->5->6
     */
    static class MergeKSortedList {
        class ListNode {
            Integer val;
            ListNode next;
            ListNode(int x) { val = x; }
        }

        Comparator<ListNode> minComparator = (a,b) -> Integer.compare(a.val, b.val);

        public ListNode mergeKLists(ListNode[] lists) {
            PriorityQueue<ListNode> minHeap = createPriorityQueue(lists);
            ListNode head = null;
            ListNode tail = null;

            while (!minHeap.isEmpty()) {
                ListNode top = minHeap.poll();

                if (tail == null) {
                    head = top;
                } else {
                    tail.next = top;
                }


                if (top.next != null) {
                    minHeap.add(top.next);
                }
                tail = top;
                tail.next = null;
            }
            return head;
        }

        private PriorityQueue<ListNode> createPriorityQueue(ListNode[] lists) {
            if (lists.length == 0) {
                return new PriorityQueue<>();
            }
            List<ListNode> firstElement = new ArrayList<>(lists.length);
            for(ListNode node: lists) {
                if (node != null) {
                    firstElement.add(node);
                }
            }
            PriorityQueue<ListNode> minHeap = new PriorityQueue<>(lists.length, minComparator);
            minHeap.addAll(firstElement);
            return minHeap;
        }
    }


    /**
     * https://leetcode.com/problems/generate-parentheses/
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     *
     * For example, given n = 3, a solution set is:
     *
     * [
     *   "((()))",
     *   "(()())",
     *   "(())()",
     *   "()(())",
     *   "()()()"
     * ]
     */
    static class GenerateParenthesis {
        public List<String> generateParenthesis(int n) {
            List<String> result = new ArrayList<>();
            if (n == 0) {
                return result;
            }
            updateParen(result, "", n, n);
            return result;
        }

        public void updateParen(List<String> result, String pattern, int open, int close) {
            if (open < 0 || close <0) {
                return;
            }

            if (open > close) {
                return;
            }

            if (open == 0 && close == 0) {
                result.add(pattern);
                return;
            }

            updateParen(result, pattern + "(", open-1, close);
            updateParen(result, pattern + ")", open, close-1);
        }
    }


    /**
     * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
     * You are given a string, s, and a list of words, words, that are all of the same length.
     * Find all starting indices of substring(s) in s that is a concatenation of each word in words
     * exactly once and without any intervening characters.
     *
     * Example 1:
     *
     * Input:
     *   s = "barfoothefoobarman",
     *   words = ["foo","bar"]
     * Output: [0,9]
     * Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
     * The output order does not matter, returning [9,0] is fine too.
     * Example 2:
     *
     * Input:
     *   s = "wordgoodgoodgoodbestword",
     *   words = ["word","good","best","word"]
     * Output: []
     */
    static class SubstringWithConcatenationOfAllWords {
        public List<Integer> findSubstring(String s, String[] words) {
            List<Integer> result = new ArrayList<>();
            if (words.length == 0) {
                return result;
            }

            Map<String, Integer> wordMap = createWordMap(words);
            int wordLength = words[0].length();

            for (int i=0; i<= s.length() - wordLength; i++) {
                Map<String, Integer> occurrenceMap = new HashMap<>(wordMap);
                if (containsAllWords(occurrenceMap, s, i, wordLength)) {
                    result.add(i);
                }
            }
            return result;
        }

        private boolean containsAllWords(Map<String, Integer> occurrenceMap, String s, int startIndex, int wordLength) {
            while (startIndex + wordLength <= s.length() ) {
                String word = s.substring(startIndex, startIndex + wordLength);
                if (occurrenceMap.containsKey(word)) {
                    int freq = occurrenceMap.get(word);
                    if (freq > 1) {
                        occurrenceMap.put(word, freq-1);
                    } else {
                        occurrenceMap.remove(word);
                    }
                } else {
                    return false;
                }

                if (occurrenceMap.isEmpty()) {
                    return true;
                }

                startIndex += wordLength;
            }
            return false;
        }

        private Map<String, Integer> createWordMap(String[] words) {
            Map<String, Integer> wordMap = new HashMap<>();
            for (String word: words) {
                int freq = 1;
                if (wordMap.containsKey(word)) {
                    freq = wordMap.get(word) + 1;
                }
                wordMap.put(word, freq);
            }
            return wordMap;
        }
    }


    /**
     * https://leetcode.com/problems/reverse-nodes-in-k-group/
     * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
     * k is a positive integer and is less than or equal to the length of the linked list.
     * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
     *
     * Example:
     *
     * Given this linked list: 1->2->3->4->5
     *
     * For k = 2, you should return: 2->1->4->3->5
     *
     * For k = 3, you should return: 3->2->1->4->5
     *
     * Note:
     *
     * Only constant extra memory is allowed.
     * You may not alter the values in the list's nodes, only nodes itself may be changed.
     */
    static class ReverseKGroups {

        public static class ListNode {
            int val;
            ListNode next;
            ListNode(int x) { val = x; }
        }

        ListNode newHead = null;
        ListNode prevTail = null;
        ListNode currStart = null;
        ListNode currTail = null;
        ListNode start;
        ListNode newStart;

        public ListNode reverseKGroup(ListNode head, int k) {

//            if (head == null) {
//                return head;
//            }

            start = head;
            while (start != null) {
                if (!hasKNodes(start, k)) {
                    if (prevTail != null) {
                        prevTail.next = start;
                    }
                    break;
                }

                currTail = reverse(start, k-1);

                if (prevTail != null) {
                    prevTail.next = currStart;
                }

                prevTail = currTail;
                start = newStart;
                currTail.next = null;
            }

            return newHead == null ? head : newHead;
        }

        private boolean hasKNodes(ListNode node, int k) {
            int count = 0;
            for (; node != null && count < k; node = node.next) {
                count++;
            }
            return count == k;
        }

        private ListNode reverse(ListNode root, int k) {
            if (k == 0 || root.next == null) {
                if (newHead == null) {
                    newHead = root;
                }
                currStart = root;
                newStart = root.next;
                return root;
            }

            ListNode previous = reverse(root.next, k-1);
            previous.next = root;
            return root;
        }
    }


    /**
     * https://leetcode.com/problems/maximal-rectangle/
     * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
     *
     * Example:
     *
     * Input:
     * [
     *   ["1","0","1","0","0"],
     *   ["1","0","1","1","1"],
     *   ["1","1","1","1","1"],
     *   ["1","0","0","1","0"]
     * ]
     * Output: 6
     */
    static class MaximalRectangle {

        public int maximalRectangle(char[][] matrix) {
            if (matrix.length == 0) {
                return 0;
            }

            int[] dp = new int[matrix[0].length];
            int maxArea = 0;

            for (int i=0; i < matrix.length; i++) {
                for (int j=0; j<matrix[0].length; j++) {
                    if (matrix[i][j] == '0') {
                        dp[j] = 0;
                    } else {
                        dp[j] += 1;
                    }
                }
                maxArea = Math.max(maxArea, largestRectangleArea(dp));
            }
            return maxArea;
        }

        class Pair<K,V> {
            K index;
            V height;
            Pair(K index, V value) { this.index = index; this.height = value; }
        }

        public int largestRectangleArea(int[] heights) {
            int maxArea = 0, width, height;
            Stack<Pair<Integer, Integer>> stack = new Stack<>();

            for (int i=0; i<heights.length; i++) {
                while (!stack.isEmpty() && stack.peek().height >= heights[i]) {
                    Pair<Integer, Integer> top = stack.pop();
                    width = stack.isEmpty() ? i : i - stack.peek().index - 1;
                    height = top.height;
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(new Pair<>(i, heights[i]));
            }

            while (!stack.isEmpty()) {
                Pair<Integer, Integer> top = stack.pop();
                height = top.height;
                width = stack.isEmpty() ?  heights.length : heights.length - stack.peek().index - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            return maxArea;
        }
    }


    /**
     * https://leetcode.com/problems/recover-binary-search-tree/
     * Two elements of a binary search tree (BST) are swapped by mistake.
     *
     * Recover the tree without changing its structure.
     *
     * Example 1:
     *
     * Input: [1,3,null,null,2]
     *
     *    1
     *   /
     *  3
     *   \
     *    2
     *
     * Output: [3,1,null,null,2]
     *
     *    3
     *   /
     *  1
     *   \
     *    2
     * Example 2:
     *
     * Input: [3,1,4,null,null,2]
     *
     *   3
     *  / \
     * 1   4
     *    /
     *   2
     *
     * Output: [2,1,4,null,null,3]
     *
     *   2
     *  / \
     * 1   4
     *    /
     *   3
     * Follow up:
     *
     * A solution using O(n) space is pretty straight forward.
     * Could you devise a constant space solution?
     */
    static class RecoverBST {

        public static class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }

        public void recoverTree(TreeNode root) {
//            recoverTreeNoSpace(root);
            recoverTreeNoSpace(root);
        }

        public void recoverTreeSpace(TreeNode root) {
            List<TreeNode> sortedList = new ArrayList<>();
            inorder(root, sortedList);
            TreeNode first = null, second = null;

            for (int i=0; i<sortedList.size(); i++) {
                int previous = i == 0 ? Integer.MIN_VALUE : sortedList.get(i-1).val;
                int next = i == sortedList.size() -1 ? Integer.MAX_VALUE : sortedList.get(i+1).val;
                int current = sortedList.get(i).val;

                if (current < previous || current > next) {
                    if (first == null) {
                        first = sortedList.get(i);
                    } else {
                        second = sortedList.get(i);
                    }
                }

            }
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }

        private void inorder(TreeNode root, List<TreeNode> sortedList) {
            if (root == null) {
                return;
            }
            inorder(root.left, sortedList);
            sortedList.add(root);
            inorder(root.right, sortedList);
        }

        public void recoverTreeNoSpace(TreeNode root) {
            inorderTraversal(root);
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }


        TreeNode previous = null, first = null, second = null;
        private void inorderTraversal(TreeNode root) {
            if (root == null) {
                return;
            }

            inorderTraversal(root.left);

            if (previous != null && first == null && previous.val > root.val) {
                first = previous;
            }
            if (previous != null && first != null && previous.val > root.val) {
                second = root;
            }
            previous = root;

            inorderTraversal(root.right);
        }
    }


    /**
     * https://leetcode.com/problems/insert-interval/
     * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
     *
     * You may assume that the intervals were initially sorted according to their start times.
     *
     * Example 1:
     *
     * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
     * Output: [[1,5],[6,9]]
     * Example 2:
     *
     * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
     * Output: [[1,2],[3,10],[12,16]]
     * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
     */
    static class InsertInterval {
        final int START = 0;
        final int END = 1;
        // Comparator<int[]> comparator = (a, b) ->  a[START] - b[START] != 0 ? a[START] - b[START] : a[END] - b[END] ;

        public int[][] insert(int[][] intervals, int[] newInterval) {
            List<int[]> result = new ArrayList<>(intervals.length + 1);

            int i=0;
            // Add all smaller and non-overlapping intervals.
            while (i < intervals.length && !doOverlap(intervals[i], newInterval) && intervals[i][START] < newInterval[START]) {
                result.add(intervals[i++]);
            }

            // Merge all overlapping intervals and then add the merged interval.
            while (i < intervals.length && doOverlap(intervals[i], newInterval)) {
                newInterval = merge(intervals[i++], newInterval);
            }
            result.add(newInterval);

            // Add intervals with start greater than end of new interval if any.
            while (i < intervals.length) {
                result.add(intervals[i++]);
            }

            return convertToMatrix(result);
        }

        private boolean doOverlap(int[] a, int[] b) {
            if (b[END] < a[START] || b[START] > a[END]) {
                return false;
            }
            return true;
        }

        private int[] merge(int[] a, int[] b) {
            int[] result = new int[2];
            result[START] = Math.min(a[START], b[START]);
            result[END] = Math.max(a[END], b[END]);
            return result;
        }

        private int[][] convertToMatrix(List<int[]> result) {
            int[][] matrix = new int[result.size()][2];
            int i=0;
            for (int[] r: result) {
                matrix[i++] = r;
            }
            return matrix;
        }
    }


    /**
     * https://leetcode.com/problems/interleaving-string/
     * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
     *
     * Example 1:
     *
     * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
     * Output: true
     * Example 2:
     *
     * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
     * Output: false
     */
    static class InterleavingStrings {
        private String a;
        private String b;
        private String c;
        private int[][] cache;

        public boolean isInterleave(String s1, String s2, String s3) {
            a = s1; b = s2; c = s3;
            if (c.length() != a.length() + b.length()) {
                return false;
            }
            cache = new int[a.length()][b.length()];
            return isInterleaveMemoized(0,0,0);
        }

        private boolean isInterleaveRecursive(int i, int j, int k) {
            if (k == c.length()) {
                return true;
            }

            char chC = c.charAt(k);
            if (i < a.length() && chC == a.charAt(i) && isInterleaveRecursive(i+1, j, k+1)) {
                return true;
            }

            if (j < b.length() && chC == b.charAt(j) && isInterleaveRecursive(i, j+1, k+1)) {
                return true;
            }
            return false;
        }

        private boolean isInterleaveMemoized(int i, int j, int k) {

            if (i < a.length() && j < b.length() && cache[i][j] != 0) {
                return cache[i][j] == -1 ? false : true;
            }

            if (k == c.length()) {
                return true;
            }

            char chC = c.charAt(k);
            boolean ans = false;
            if (i < a.length() && chC == a.charAt(i) && isInterleaveMemoized(i+1, j, k+1)
            || (j < b.length() && chC == b.charAt(j) && isInterleaveMemoized(i, j+1, k+1))) {
                ans = true;
            }

            if (i < a.length() && j < b.length()) {
                cache[i][j] = ans ? 1 : -1;
            }
            return ans;
        }

        private boolean isInterleaveTabulated() {
            boolean [][] dp = new boolean[a.length()+1][b.length()+1];

            for (int i=0; i<dp.length; i++) {
                for (int j=0; j<dp[0].length; j++) {
                    if (i==0 && j ==0) {
                        dp[i][j] = true;
                    } else if (i==0) {
                        dp[i][j] = dp[i][j-1] && b.charAt(j-1) == c.charAt(i+j- 1);
                    } else if (j==0) {
                        dp[i][j] = dp[i-1][j] && a.charAt(i-1) == c.charAt(i+j-1);
                    } else {
                        dp[i][j] = dp[i-1][j] && a.charAt(i-1) == c.charAt(i+j-1) || dp[i][j-1] && b.charAt(j-1) == c.charAt(i+j-1);
                    }
                }
            }
            return dp[a.length()][b.length()];
        }
    }


    /**
     * https://leetcode.com/problems/binary-tree-maximum-path-sum/
     * Given a non-empty binary tree, find the maximum path sum.
     *
     * For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree
     * along the parent-child connections. The path must contain at least one node and does not need to go through the root.
     *
     * Example 1:
     *
     * Input: [1,2,3]
     *
     *        1
     *       / \
     *      2   3
     *
     * Output: 6
     * Example 2:
     *
     * Input: [-10,9,20,null,null,15,7]
     *
     *    -10
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * Output: 42
     */
    static class MaximumPathSumInBinaryTree {

        class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }

        public int maxPathSum(TreeNode root) {
            AtomicInteger globalMax = new AtomicInteger(Integer.MIN_VALUE);
            _maxPathSum(root, globalMax);
            return globalMax.get();
        }

        private int _maxPathSum(TreeNode root, AtomicInteger globalMax) {

            if (root == null) {
                return 0;
            }

            int left = _maxPathSum(root.left, globalMax);
            int right = _maxPathSum(root.right, globalMax);
            int candidate = left + right + root.val;

            if (globalMax.get() < candidate) {
                globalMax.set(candidate);
            }

            int path = root.val + Math.max(left, right);
            return path > 0 ? path : 0;
        }
    }


    /**
     * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
     * Serialization is the process of converting a data structure or object into a sequence of bits
     * so that it can be stored in a file or memory buffer, or transmitted across a network connection
     * link to be reconstructed later in the same or another computer environment.
     *
     * Design an algorithm to serialize and deserialize a binary tree.
     * There is no restriction on how your serialization/deserialization algorithm should work.
     * You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized
     * to the original tree structure.
     *
     * Example:
     *
     * You may serialize the following tree:
     *
     *     1
     *    / \
     *   2   3
     *      / \
     *     4   5
     *
     * as "[1,2,3,null,null,4,5]"
     * Clarification: The above format is the same as how LeetCode serializes a binary tree.
     * You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
     *
     * Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms
     * should be stateless.
     */
    static class CodecForBinaryTree {

        class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            public TreeNode(int v) { this.val = v;}
        }

        private final String NULL_NODE = "null";
        class SerializationContext {
            boolean isEmpty;
            int index;

            public SerializationContext() {
                isEmpty = true;
                index = 0;
            }
        }

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder builder = new StringBuilder();
            serialize(root, new SerializationContext(), builder);
            return builder.toString();
        }

        private void serialize(TreeNode root, SerializationContext context, StringBuilder builder) {
            append(builder, context, root);

            if (root == null) {
                return;
            }
            serialize(root.left, context, builder);
            serialize(root.right, context, builder);
        }

        private void append(StringBuilder builder, SerializationContext context, TreeNode root) {
            if (context.isEmpty) {
                context.isEmpty = false;
            } else {
                builder.append(",");
            }

            if (root == null) {
                builder.append(NULL_NODE);
            } else {
                builder.append(root.val);
            }
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }

            String[] nodes = data.split(",");
            if (nodes.length == 0) {
                return null;
            }
            return _construct(nodes, new SerializationContext());
        }

        private TreeNode _construct(String[] nodes, SerializationContext context) {
            if (context.index >= nodes.length) {
                return null;
            }

            TreeNode newNode = createNode(nodes[context.index++]);
            if (newNode == null) {
                return newNode;
            }
            newNode.left = _construct(nodes, context);
            newNode.right = _construct(nodes, context);
            return newNode;
        }

        private TreeNode createNode(String n) {
            if (NULL_NODE.equals(n)) {
                return null;
            }
            return new TreeNode(Integer.valueOf(n));
        }
    }


    /**
     * https://leetcode.com/problems/couples-holding-hands/
     * N couples sit in 2N seats arranged in a row and want to hold hands. We want to know the minimum number of swaps so that every couple is sitting side by side. A swap consists of choosing any two people, then they stand up and switch seats.
     *
     * The people and seats are represented by an integer from 0 to 2N-1, the couples are numbered in order, the first couple being (0, 1), the second couple being (2, 3), and so on with the last couple being (2N-2, 2N-1).
     *
     * The couples' initial seating is given by row[i] being the value of the person who is initially sitting in the i-th seat.
     *
     * Example 1:
     *
     * Input: row = [0, 2, 1, 3]
     * Output: 1
     * Explanation: We only need to swap the second (row[1]) and third (row[2]) person.
     * Example 2:
     *
     * Input: row = [3, 2, 0, 1]
     * Output: 0
     * Explanation: All couples are already seated side by side.
     * Note:
     *
     * len(row) is even and in the range of [4, 60].
     * row is guaranteed to be a permutation of 0...len(row)-1.
     */
    static class CouplesHoldingHands {
        public int minSwapsCouples(int[] row) {
            int [] position = new int[row.length];

            // Initialize hash.
            for (int i=0; i<row.length; i++) {
                position[row[i]] = i;
            }

            // Scan row from left to right
            int partner, positionOfPartner, swaps = 0;
            for (int i=1; i<row.length; i+=2) {
                partner = partnerOf(row[i-1]);
                positionOfPartner = position[partner];

                if (positionOfPartner != i) {
                    swap(row, i, positionOfPartner);
                    position[row[i]] = i;
                    position[row[positionOfPartner]] = positionOfPartner;
                    swaps++;
                }
            }
            return swaps;
        }

        int partnerOf(int n) {
            return n % 2 == 0 ? n +1 : n -1;
        }

        void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


    /**
     * https://leetcode.com/problems/counting-bits/
     * Given a non negative integer number num.
     * For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in their binary
     * representation and return them as an array.
     *
     * Example 1:
     *
     * Input: 2
     * Output: [0,1,1]
     * Example 2:
     *
     * Input: 5
     * Output: [0,1,1,2,1,2]
     */
    static class CountingBits {
        public int[] countBits(int num) {
            int [] dp = new int[num+1];
            int twoPower = 0;
            for (int i=1; i <= num; i++) {
                if ((i & i-1) == 0) {
                    twoPower = i;
                    dp[i] = 1;
                } else {
                    dp[i] = 1 + dp[i-twoPower];
                }
            }
            return dp;
        }
    }


    /**
     * https://leetcode.com/problems/split-array-largest-sum/
     * Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty
     * continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
     *
     * Note:
     * If n is the length of array, assume the following constraints are satisfied:
     *
     * 1 ≤ n ≤ 1000
     * 1 ≤ m ≤ min(50, n)
     * Examples:
     *
     * Input:
     * nums = [7,2,5,10,8]
     * m = 2
     *
     * Output:
     * 18
     *
     * Explanation:
     * There are four ways to split nums into two subarrays.
     * The best way is to split it into [7,2,5] and [10,8],
     * where the largest sum among the two subarrays is only 18.
     *
     * This is same as painter parition problem.
     */
    static class SplitArrayLargestSum {

        class DynamicProgrammingSolution {
            class BinaryIndexedTree {

                int[] input;
                int[] tree;

                public BinaryIndexedTree(int[] input) {
                    this.input = input;
                    tree = new int[input.length+1];
                    constructTree();
                }

                private void constructTree() {
                    this.tree = new int[input.length + 1];
                    for (int i = 0; i < input.length; i++) {
                        update(i+1, input[i]);
                    }
                }

                public int query(int itemIndex) {
                    int sum = 0;
                    for (int i=itemIndex; i> 0; i -= (i&-i)) {
                        sum += tree[i];
                    }
                    return sum;
                }

                public void update(int itemIndex, int value) {
                    for (int i=itemIndex; i < tree.length; i += (i&-i)) {
                        tree[i] += value;
                    }
                }

            }

            public int splitArray(int[] nums, int m) {
                BinaryIndexedTree tree = new BinaryIndexedTree(nums);
                int[][] dp = new int[m][nums.length];

                // When there's no partition.
                dp[0][0] = nums[0];
                for (int i=1; i<dp[0].length; i++) {
                    dp[0][i] = dp[0][i-1] + nums[i];
                }

                // When there's only one element in the array.
                for (int i=1; i<dp.length; i++) {
                    dp[i][0] = nums[0];
                }

                for (int i=1; i<dp.length; i++) {
                    for (int j=1; j<dp[0].length; j++) {
                        int max = Integer.MAX_VALUE;
                        for (int k=0; k<j; k++) {
                            max = Math.min(max, Math.max(dp[i-1][k], tree.query(j+1) - tree.query(k+1)));
                        }
                        dp[i][j] = max;
                    }
                }
                return dp[m-1][dp[0].length-1];
            }
        }

        class BinarySearchSolution {
            int[] nums;

            public int splitArray(int[] nums, int m) {
                this.nums = nums;
                long lo = 0, hi = 0;

                for (int i=0; i<nums.length; i++) {
                    hi += nums[i];
                    lo = Math.max(lo, nums[i]);
                }

                // hi = case when there's no partition.
                // lo = case when we pick the largest element in array as one partition.
                // we iterate between lo and hi and count the number of splits for
                while (lo < hi) {
                    long mid = lo + (hi - lo)/2;
                    int splits = countSplit(mid);

                    if (splits > m) {
                        lo = mid + 1;
                    } else {
                        hi = mid - 1;
                    }
                }
                return (int) lo;
            }

            private int countSplit(long limit) {
                int splits = 0;
                for (int i=0; i<nums.length; splits++) {
                    long sum = 0;
                    while (i < nums.length && sum + nums[i] <= limit) {
                        sum += nums[i];
                        i++;
                    }
                }
                return splits;
            }
        }
    }


    /**
     * https://leetcode.com/problems/all-oone-data-structure/
     * Implement a data structure supporting the following operations:
     *
     * Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1. Key is guaranteed to be a non-empty string.
     * Dec(Key) - If Key's value is 1, remove it from the data structure. Otherwise decrements an existing key by 1. If the key does not exist, this function does nothing. Key is guaranteed to be a non-empty string.
     * GetMaxKey() - Returns one of the keys with maximal value. If no element exists, return an empty string "".
     * GetMinKey() - Returns one of the keys with minimal value. If no element exists, return an empty string "".
     * Challenge: Perform all these in O(1) time complexity.
     */
    static class AllOne {

        private Map<String, Bucket> map;
        private Bucket start;
        private Bucket end;

        static class Bucket {
            int frequency;
            Set<String> keys;
            Bucket prev;
            Bucket next;

            public Bucket(int frequency, String key) {
                Set<String> keys = new HashSet<>();
                keys.add(key);
                this.frequency  = frequency; this.keys = keys;
            }

            public void addKey(String key) {
                this.keys.add(key);
            }

            public void removeKey(String key) {
                this.keys.remove(key);
            }

            public static void addAfter(Bucket currentBucket, Bucket newBucket) {
                Bucket nextBucket = currentBucket.next;
                currentBucket.next = newBucket;
                nextBucket.prev = newBucket;
                newBucket.prev = currentBucket;
                newBucket.next = nextBucket;
            }

            public static void delete(Bucket bucket) {
                Bucket prev = bucket.prev;
                Bucket next = bucket.next;
                prev.next = next;
                next.prev = prev;
            }
        }

        public AllOne() {
            map = new HashMap<>();
            start = new Bucket(Integer.MIN_VALUE, "");
            end = new Bucket(Integer.MAX_VALUE, "");
            start.next = end;
            end.prev = start;
        }

        /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
        public void inc(String key) {
            // When the key doesn't exist.
            if (!map.containsKey(key)) {
                Bucket newBucket;
                if (start.next.frequency == 1) {
                    start.next.addKey(key);
                    newBucket = start.next;
                } else {
                    newBucket = new Bucket(1, key);
                    Bucket.addAfter(start, newBucket);
                }
                map.put(key, newBucket);
                return;
            }

            // Key already exist.
            Bucket bucket = map.get(key);
            Bucket nextBucket = bucket.next;
            Bucket newBucket;
            if (nextBucket.frequency == bucket.frequency + 1) {
                nextBucket.addKey(key);
                newBucket = nextBucket;
            } else {
                newBucket = new Bucket(bucket.frequency + 1, key);
                Bucket.addAfter(bucket, newBucket);
            }
            map.put(key, newBucket);

            bucket.removeKey(key);
            if (bucket.keys.isEmpty()) {
                Bucket.delete(bucket);
            }
        }

        /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
        public void dec(String key) {
            if (!map.containsKey(key)) {
                return;
            }
            Bucket bucket = map.get(key);
            int freq = bucket.frequency;

            map.remove(key);
            if (freq > 1) {
                Bucket newBucket = null;
                if (bucket.prev.frequency == freq - 1) {
                    newBucket = bucket.prev;
                    bucket.prev.addKey(key);
                } else {
                    newBucket = new Bucket(freq-1, key);
                    Bucket.addAfter(bucket.prev, newBucket);
                }
                map.put(key, newBucket);
            }

            bucket.removeKey(key);
            if (bucket.keys.isEmpty()) {
                Bucket.delete(bucket);
            }
        }

        /** Returns one of the keys with maximal value. */
        public String getMaxKey() {
            return end.prev.keys.stream().findFirst().orElse("");
        }

        /** Returns one of the keys with Minimal value. */
        public String getMinKey() {
            return start.next.keys.stream().findFirst().orElse("");
        }
    }


    /**
     * https://leetcode.com/problems/k-th-smallest-in-lexicographical-order/
     * Given integers n and k, find the lexicographically k-th smallest integer in the range from 1 to n.
     *
     * Note: 1 ≤ k ≤ n ≤ 109.
     *
     * Example:
     *
     * Input:
     * n: 13   k: 2
     *
     * Output:
     * 10
     *
     * Explanation:
     * The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.
     */
    static class KthSmallestInLexicographicOrder {

        /**
         * consider it as prefix-tree.
         */
        class OptimalPreorderSearchBySkipping {

            public int findKthNumber(int n, int k) {
                int prefix = 1;
                k--;
                while (k > 0) {
                    long prefixCount = itemsWithPrefix(prefix, n);
                    if (prefixCount < k) {
                        prefix += 1;
                        k -= prefixCount;
                    } else if (prefixCount > k) {
                        k -= 1;
                        prefix *= 10;
                    } else {
                        return prefix+1;
                    }
                }
                return prefix;
            }

            /**
             * Traverses in leftmost and rightmost branches of a prefix and considers their difference i.e. number
             * of elements between them in a given level.
             */
            private long itemsWithPrefix(int p, int n) {
                long left = p, right = p, skipped = 0;
                while (left<=n) {
                    skipped += Math.min(right, n) - left + 1;
                    left *= 10;
                    right = right * 10 + 9;
                }
                return skipped;
            }
        }


        /**
         * Consider this as prefix tree and do an in-order traversal.
         */
        class PreorderTraversal {
            int count, n, k, ans;

            public int findKthNumber(int n, int k) {
                this.n = n;
                this.k = k;
                this.count = 0;
                this.ans = -1;

                for (int i=1; i <= 9 && ans == -1; i++) {
                    preOrderTraversal(i);
                }
                return ans;
            }

            void preOrderTraversal(int value) {
                if (value > n) {
                    return;
                }
                count++;
                if (count == k) {
                    this.ans = value;
                    return;
                }

                for (int i=0; i<=9 && ans == -1; i++) {
                    preOrderTraversal(value*10 + i);
                }
            }
        }
    }


    /**
     * https://leetcode.com/problems/zuma-game/
     * Think about Zuma Game. You have a row of balls on the table, colored red(R), yellow(Y), blue(B), green(G), and white(W).
     * You also have several balls in your hand.
     *
     * Each time, you may choose a ball in your hand, and insert it into the row (including the leftmost place and rightmost place).
     * Then, if there is a group of 3 or more balls in the same color touching, remove these balls.
     * Keep doing this until no more balls can be removed.
     *
     * Find the minimal balls you have to insert to remove all the balls on the table. If you cannot remove all the balls, output -1.
     *
     * Examples:
     *
     * Input: "WRRBBW", "RB"
     * Output: -1
     * Explanation: WRRBBW -> WRR[R]BBW -> WBBW -> WBB[B]W -> WW
     *
     * Input: "WWRRBBWW", "WRBRW"
     * Output: 2
     * Explanation: WWRRBBWW -> WWRR[R]BBWW -> WWBBWW -> WWBB[B]WW -> WWWW -> empty
     *
     * Input:"G", "GGGGG"
     * Output: 2
     * Explanation: G -> G[G] -> GG[G] -> empty
     *
     * Input: "RBYYBBRRB", "YRBGB"
     * Output: 3
     * Explanation: RBYYBBRRB -> RBYY[Y]BBRRB -> RBBBRRB -> RRRB -> B -> B[B] -> BB[B] -> empty
     *
     * Note:
     * You may assume that the initial row of balls on the table won’t have any 3 or more consecutive balls with the same color.
     * The number of balls on the table won't exceed 20, and the string represents these balls is called "board" in the input.
     * The number of balls in your hand won't exceed 5, and the string represents these balls is called "hand" in the input.
     * Both input strings will be non-empty and only contain characters 'R','Y','B','G','W'.
     */
    static class Zuma {

        private static int NO_RESULT = 1000;

        public int findMinStep(String board, String hand) {
            int[] hash = new int[128];
            for(char ch : hand.toCharArray()) {
                hash[ch]++;
            }
            int result = minSolve(board, hash);
            return result == NO_RESULT ? -1 : result;
        }

        // This is a simple brute force solution.
        int minSolve(String board, int[] hash) {
            if (board.isEmpty()) {
                return 0;
            }

            int result = NO_RESULT;
            for (int i=0, j; i<board.length(); i = j) {
                char ch = board.charAt(i);
                for (j=i+1; j < board.length() && ch == board.charAt(j); j++) { ; }

                int required = 3 - (j - i);
                // If a move is possible then make it.
                if (hash[ch] >= required) {
                    int usedFromHand = required < 0 ? 0 : required;
                    hash[ch] -= usedFromHand;

                    int interimResult = minSolve(board.substring(0, i) + board.substring(j), hash);
                    result = Math.min(result, interimResult + usedFromHand);

                    // Undo the move.
                    hash[ch] += usedFromHand;
                }
            }
            return result;
        }
    }


    /**
     * https://leetcode.com/problems/super-washing-machines/
     * You have n super washing machines on a line. Initially, each washing machine has some dresses or is empty.
     *
     * For each move, you could choose any m (1 ≤ m ≤ n) washing machines, and pass one dress of each washing machine
     * to one of its adjacent washing machines at the same time .
     *
     * Given an integer array representing the number of dresses in each washing machine from left to right on the line,
     * you should find the minimum number of moves to make all the washing machines have the same number of dresses.
     * If it is not possible to do it, return -1.
     *
     * Example1
     *
     * Input: [1,0,5]
     *
     * Output: 3
     *
     * Explanation:
     * 1st move:    1     0 <-- 5    =>    1     1     4
     * 2nd move:    1 <-- 1 <-- 4    =>    2     1     3
     * 3rd move:    2     1 <-- 3    =>    2     2     2
     * Example2
     *
     * Input: [0,3,0]
     *
     * Output: 2
     *
     * Explanation:
     * 1st move:    0 <-- 3     0    =>    1     2     0
     * 2nd move:    1     2 --> 0    =>    1     1     1
     * Example3
     *
     * Input: [0,2,0]
     *
     * Output: -1
     *
     * Explanation:
     * It's impossible to make all the three washing machines have the same number of dresses.
     * Note:
     * The range of n is [1, 10000].
     * The range of dresses number in a super washing machine is [0, 1e5].
     */
    static class SuperWashingMachines {

        // Check explanation: https://leetcode.com/problems/super-washing-machines/discuss/235584/Explanation-of-Java-O(n)-solution
        public int findMinMoves(int[] machines) {
            int sum = 0;
            for (int m: machines) {
                sum += m;
            }

            if(sum % machines.length != 0)  {
                return -1;
            }

            int avg = sum/machines.length;

            int diff, balance = 0, maxTo = 0, maxFrom = 0;
            for (int clothes: machines) {
                diff = clothes - avg;
                balance += diff;
                maxTo = Math.max(maxTo, Math.abs(balance));
                maxFrom = Math.max(maxFrom, diff);
            }
            return Math.max(maxTo, maxFrom);
        }
    }


    /**
     * https://leetcode.com/problems/non-negative-integers-without-consecutive-ones
     * Given a positive integer n, find the number of non-negative integers less than or equal to n, whose binary
     * representations do NOT contain consecutive ones.
     *
     * Example 1:
     * Input: 5
     * Output: 5
     * Explanation:
     * Here are the non-negative integers <= 5 with their corresponding binary representations:
     * 0 : 0
     * 1 : 1
     * 2 : 10
     * 3 : 11
     * 4 : 100
     * 5 : 101
     * Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule.
     * Note: 1 <= n <= 109
     */
    static class NonNegativeIntegersWithoutConsecutiveOnes {
        int count;
        public int findIntegers(int num) {
            count = 1;
            recursiveFind(1, num, true);
            return count;
        }

        private void recursiveFind(int val, int num, boolean previousWasOne) {
            if (val > num) {
                return;
            }
            count++;

            recursiveFind(val<<1, num, false);
            if (!previousWasOne) {
                recursiveFind(val<<1 | 1, num, true);
            }
        }
    }


    /**
     * https://leetcode.com/problems/snapshot-array/
     * Implement a SnapshotArray that supports the following interface:
     *
     * SnapshotArray(int length) initializes an array-like data structure with the given length.  Initially, each element equals 0.
     * void set(index, val) sets the element at the given index to be equal to val.
     * int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
     * int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
     *
     *
     * Example 1:
     *
     * Input: ["SnapshotArray","set","snap","set","get"]
     * [[3],[0,5],[],[0,6],[0,0]]
     * Output: [null,null,0,null,5]
     * Explanation:
     * SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
     * snapshotArr.set(0,5);  // Set array[0] = 5
     * snapshotArr.snap();  // Take a snapshot, return snap_id = 0
     * snapshotArr.set(0,6);
     * snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
     */
    static class SnapshotArray {

        List<Map<Integer, Integer>> snaps;
        Map<Integer, Integer> activeMap;
        int snapId = -1;

        public SnapshotArray(int length) {
            snaps = new ArrayList<>();
            activeMap = new HashMap<>();
        }

        public void set(int index, int val) {
            activeMap.put(index, val);
        }

        public int snap() {
            snapId++;
            snaps.add(activeMap);
            activeMap = new HashMap<>();
            return snapId;
        }

        public int get(int index, int snap_id) {
            for (int i = snap_id; i >=0; i--) {
                if (snaps.get(i).containsKey(index)) {
                    return snaps.get(i).get(index);
                }
            }
            return 0;
        }
    }


    /**
     * https://leetcode.com/problems/k-closest-points-to-origin/
     * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
     *
     * (Here, the distance between two points on a plane is the Euclidean distance.)
     *
     * You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
     *
     *
     *
     * Example 1:
     *
     * Input: points = [[1,3],[-2,2]], K = 1
     * Output: [[-2,2]]
     * Explanation:
     * The distance between (1, 3) and the origin is sqrt(10).
     * The distance between (-2, 2) and the origin is sqrt(8).
     * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
     * We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].
     * Example 2:
     *
     * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
     * Output: [[3,3],[-2,4]]
     * (The answer [[-2,4],[3,3]] would also be accepted.)
     */
    static class KClosestPointsToOrigin {

        Comparator<int[]> comparator = (a, b) -> {
            double da = Math.sqrt(a[0] * a[0] + a[1] * a[1]);
            double db = Math.sqrt(b[0] * b[0] + b[1] * b[1]);
            if (da < db) {
                return -1;
            } else if (da == db) {
                return 0;
            }
            return 1;
        };

        // Can also be done use Kth or median find algo that'll work in O(n).
        public int[][] kClosest(int[][] points, int K) {
            Arrays.sort(points, comparator);
            int[][] result = new int[K][2];
            for (int i=0; i<K; i++) {
                result[i][0] = points[i][0];
                result[i][1] = points[i][1];
            }
            return result;
        }
    }


    /**
     * https://leetcode.com/problems/odd-even-jump/
     * You are given an integer array A.  From some starting index, you can make a series of jumps.
     * The (1st, 3rd, 5th, ...) jumps in the series are called odd numbered jumps, and the (2nd, 4th, 6th, ...) jumps
     * in the series are called even numbered jumps.
     *
     * You may from index i jump forward to index j (with i < j) in the following way:
     *
     * During odd numbered jumps (ie. jumps 1, 3, 5, ...), you jump to the index j such that A[i] <= A[j] and A[j] is
     * the smallest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
     * During even numbered jumps (ie. jumps 2, 4, 6, ...), you jump to the index j such that A[i] >= A[j] and A[j]
     * is the largest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
     * (It may be the case that for some index i, there are no legal jumps.)
     * A starting index is good if, starting from that index, you can reach the end of the array (index A.length - 1) by
     * jumping some number of times (possibly 0 or more than once.)
     *
     * Return the number of good starting indexes.
     *
     *
     *
     * Example 1:
     *
     * Input: [10,13,12,14,15]
     * Output: 2
     * Explanation:
     * From starting index i = 0, we can jump to i = 2 (since A[2] is the smallest among A[1], A[2], A[3], A[4] that is greater or equal to A[0]), then we can't jump any more.
     * From starting index i = 1 and i = 2, we can jump to i = 3, then we can't jump any more.
     * From starting index i = 3, we can jump to i = 4, so we've reached the end.
     * From starting index i = 4, we've reached the end already.
     * In total, there are 2 different starting indexes (i = 3, i = 4) where we can reach the end with some number of jumps.
     * Example 2:
     *
     * Input: [2,3,1,1,4]
     * Output: 3
     * Explanation:
     * From starting index i = 0, we make jumps to i = 1, i = 2, i = 3:
     *
     * During our 1st jump (odd numbered), we first jump to i = 1 because A[1] is the smallest value in (A[1], A[2], A[3], A[4]) that is greater than or equal to A[0].
     *
     * During our 2nd jump (even numbered), we jump from i = 1 to i = 2 because A[2] is the largest value in (A[2], A[3], A[4]) that is less than or equal to A[1].  A[3] is also the largest value, but 2 is a smaller index, so we can only jump to i = 2 and not i = 3.
     *
     * During our 3rd jump (odd numbered), we jump from i = 2 to i = 3 because A[3] is the smallest value in (A[3], A[4]) that is greater than or equal to A[2].
     *
     * We can't jump from i = 3 to i = 4, so the starting index i = 0 is not good.
     *
     * In a similar manner, we can deduce that:
     * From starting index i = 1, we jump to i = 4, so we reach the end.
     * From starting index i = 2, we jump to i = 3, and then we can't jump anymore.
     * From starting index i = 3, we jump to i = 4, so we reach the end.
     * From starting index i = 4, we are already at the end.
     * In total, there are 3 different starting indexes (i = 1, i = 3, i = 4) where we can reach the end with some number of jumps.
     * Example 3:
     *
     * Input: [5,1,3,4,2]
     * Output: 3
     * Explanation:
     * We can reach the end from starting indexes 1, 2, and 4.
     */
    static class OddEvenJump {

        public int oddEvenJumps(int[] input) {
            boolean even[] = new boolean[input.length];
            boolean odd[] = new boolean[input.length];
            even[input.length-1] = true;
            odd[input.length-1] = true;

            // V. Imp property of tree map is it gives next lower and higher key.
            TreeMap<Integer, Integer> treeMap = new TreeMap<>();
            treeMap.put(input[input.length-1], input.length-1);

            for (int i=input.length-2; i>=0; i--) {
                int val = input[i];
                if (treeMap.containsKey(val)) {
                    odd[i] = even[treeMap.get(val)];
                    even[i] = odd[treeMap.get(val)];
                } else {
                    Integer justSmaller = treeMap.lowerKey(val);
                    Integer justGreater = treeMap.higherKey(val);

                    if (justSmaller != null) {
                        even[i] = odd[treeMap.get(justSmaller)];
                    }

                    if (justGreater != null) {
                        odd[i] = even[treeMap.get(justGreater)];
                    }
                }
                treeMap.put(val, i);
            }

            int count = 0;
            for (boolean oddStart: odd) {
                if (oddStart) {
                    count++;
                }
            }
            return count;
        }
    }


    /**
     * https://leetcode.com/problems/longest-consecutive-sequence/
     * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
     *
     * Your algorithm should run in O(n) complexity.
     *
     * Example:
     *
     * Input: [100, 4, 200, 1, 3, 2]
     * Output: 4
     * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
     */
    static class LongestConsecutiveSequence {

        public int longestConsecutive(int[] nums) {

            // Preprocessing step.
            Set<Integer> set = new HashSet<>();
            for (int i=0; i<nums.length; i++) {
                set.add(nums[i]);
            }

            int max = 0;
            for (int num: set) {
                if (!set.contains(num-1)) {
                    int count = 1;
                    int start = num;
                    while (set.contains(start+1)) {
                        count++;
                        start++;
                    }
                    max = Math.max(max, count);
                }
            }
            return max;
        }
    }


    /**
     * https://leetcode.com/problems/koko-eating-bananas/
     * Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.
     * The guards have gone and will come back in H hours.
     *
     * Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas, and
     * eats K bananas from that pile.  If the pile has less than K bananas, she eats all of them instead, and won't
     * eat any more bananas during this hour.
     *
     * Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.
     *
     * Return the minimum integer K such that she can eat all the bananas within H hours.
     *
     *
     *
     * Example 1:
     *
     * Input: piles = [3,6,7,11], H = 8
     * Output: 4
     * Example 2:
     *
     * Input: piles = [30,11,23,4,20], H = 5
     * Output: 30
     * Example 3:
     *
     * Input: piles = [30,11,23,4,20], H = 6
     * Output: 23
     *
     *
     * Note:
     *
     * 1 <= piles.length <= 10^4
     * piles.length <= H <= 10^9
     * 1 <= piles[i] <= 10^9
     */
    static class KokoEatingBanana {

        public int minEatingSpeed(int[] piles, int h) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int i=0; i<piles.length; i++) {
                max = Math.max(max, piles[i]);
            }

            int lo = 1, hi = max;
            while (lo < hi) {
                int mid = lo + (hi-lo)/2;
                if (canEatAll(piles, h, mid)) {
                    hi = mid;
                } else {
                    lo = mid+1;
                }
            }
            return lo;
        }

        private boolean canEatAll(int[] piles, int h, int k) {
            int count = 0;
            for (int i=0; i<piles.length; i++) {
                count += piles[i]/k;
                if (piles[i]%k != 0) {
                    count++;
                }
            }
            return count <= h;
        }
    }


    /**
     * https://leetcode.com/problems/word-ladder/
     * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation
     * sequence from beginWord to endWord, such that:
     *
     * Only one letter can be changed at a time.
     * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
     * Note:
     *
     * Return 0 if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     * You may assume no duplicates in the word list.
     * You may assume beginWord and endWord are non-empty and are not the same.
     * Example 1:
     *
     * Input:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     *
     * Output: 5
     *
     * Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
     * return its length 5.
     */
    static class WordLadder {

        class AdjacencyListGraph<V> {

            class Edge {
                private final double weight;
                private final V to;

                public Edge(V to, double weight) {
                    this.weight = weight;
                    this.to = to;
                }


                public Edge(V to) {
                    this.weight = 1;
                    this.to = to;
                }
            }

            private Map<V, Set<Edge>> vertexMap;
            private boolean isDirected = false;

            public AdjacencyListGraph(boolean isDirected) {
                vertexMap = new HashMap<>();
                this.isDirected = isDirected;
            }

            public AdjacencyListGraph() {
                vertexMap = new HashMap<>();
            }

            public void insert(V vertex) {
                if (isPresent(vertex)) {
                    throw new IllegalArgumentException("Tried to add duplicate node.");
                }
                vertexMap.put(vertex, new HashSet<>());
            }

            public Set<V> successors(V vertex) {
                if (isPresent(vertex)) {
                    return vertexMap.get(vertex).stream().map(e -> e.to).collect(Collectors.toSet());
                }
                throw new IllegalArgumentException("node is not present in graph");
            }

            public void connect(V v1, V v2, double weight) {
                if (!isPresent(v1) || !isPresent(v2)) {
                    throw new IllegalArgumentException("One or both node not present in graph.");
                }
                vertexMap.get(v1).add(new AdjacencyListGraph.Edge(v2, weight));

                if (!isDirected) {
                    vertexMap.get(v2).add(new AdjacencyListGraph.Edge(v1, weight));
                }
            }

            public boolean isPresent(V vertex) {
                return vertexMap.containsKey(vertex);
            }
        }

        public int ladderLength(String start, String end, List<String> dictionary) {
            dictionary.add(start);
            if (!dictionary.contains(end)) {
                return 0;
            }
            AdjacencyListGraph<String> ladder = new AdjacencyListGraph<>();
            Set<String> nodesInGraph = new HashSet<>();

            for (String word : dictionary) {
                if (!nodesInGraph.contains(word)) {
                    ladder.insert(word);
                    nodesInGraph.add(word);
                    updateGraph(ladder, nodesInGraph, word);
                }
            }
            return bfs(ladder, start, end);
        }

        class LevelNode {
            int level;
            String val;
            public LevelNode(String v, int l) {this.level = l; this.val = v;}
        }

        private int bfs(AdjacencyListGraph<String> graph, String source, String destination) {
            Queue<LevelNode> queue = new ArrayDeque<>();
            queue.offer(new LevelNode(source, 0));
            Set<String> explored = new HashSet<>();

            while (!queue.isEmpty()) {
                LevelNode front = queue.poll();

                if (front.val.equals(destination)) {
                    return front.level + 1;
                }

                explored.add(front.val);
                for (String succ: graph.successors(front.val)) {
                    if (!explored.contains(succ)) {
                        queue.add(new LevelNode(succ, front.level+1));
                    }
                }
            }
            return 0;
        }

        private void updateGraph(AdjacencyListGraph<String> ladder, Set<String> nodesInGraph, String word) {
            String similarWord;
            for (int i=0; i<word.length(); i++) {
                String prefix = i == 0 ? new StringBuilder(word.charAt(0)).toString() : word.substring(0, i);
                String suffix = i == word.length() - 1 ? "" : word.substring(i+1);
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    similarWord = prefix + ch + suffix;
                    if (nodesInGraph.contains(similarWord) && !similarWord.equals(word)) {
                        ladder.connect(word, similarWord, 1.0);
                    }
                }
            }
        }
    }


    /**
     * https://leetcode.com/problems/word-ladder-ii/
     * Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s)
     * from beginWord to endWord, such that:
     *
     * Only one letter can be changed at a time
     * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
     * Note:
     *
     * Return an empty list if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     * You may assume no duplicates in the word list.
     * You may assume beginWord and endWord are non-empty and are not the same.
     * Example 1:
     *
     * Input:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     *
     * Output:
     * [
     *   ["hit","hot","dot","dog","cog"],
     *   ["hit","hot","lot","log","cog"]
     * ]
     */
    static class WordLadderII {

        interface Graph<V> {

            void insert(V vertex);

            Set<V> vertices();

            Set<V> successors(V vertex);

            void connect(V v1, V v2);

            void connect(V v1, V v2, double weight);

            boolean isPresent(V vertex);

            double edgeWeight(V v1, V v2);


            class AdjacencyListGraph<V> implements Graph<V> {

                class Edge {
                    private final double weight;
                    private final V to;

                    public Edge(V to, double weight) {
                        this.weight = weight;
                        this.to = to;
                    }


                    public Edge(V to) {
                        this.weight = 1;
                        this.to = to;
                    }
                }

                private Map<V, Set<Edge>> vertexMap;
                private boolean isDirected = false;

                public AdjacencyListGraph(boolean isDirected) {
                    vertexMap = new HashMap<>();
                    this.isDirected = isDirected;
                }

                public AdjacencyListGraph() {
                    vertexMap = new HashMap<>();
                }

                @Override
                public void insert(V vertex) {
                    if (isPresent(vertex)) {
                        throw new IllegalArgumentException("Tried to add duplicate node.");
                    }
                    vertexMap.put(vertex, new HashSet<>());
                }

                @Override
                public Set<V> vertices() {
                    return vertexMap.keySet();
                }

                @Override
                public Set<V> successors(V vertex) {
                    if (isPresent(vertex)) {
                        return vertexMap.get(vertex).stream().map(e -> e.to).collect(Collectors.toSet());
                    }
                    throw new IllegalArgumentException("node is not present in graph");
                }

                @Override
                public void connect(V v1, V v2) {
                    if (!isPresent(v1) || !isPresent(v2)) {
                        throw new IllegalArgumentException("One or both node not present in graph.");
                    }
                    vertexMap.get(v1).add(new Edge(v2));

                    if (!isDirected) {
                        vertexMap.get(v2).add(new Edge(v1));
                    }
                }

                @Override
                public void connect(V v1, V v2, double weight) {
                    if (!isPresent(v1) || !isPresent(v2)) {
                        throw new IllegalArgumentException("One or both node not present in graph.");
                    }
                    vertexMap.get(v1).add(new Edge(v2, weight));

                    if (!isDirected) {
                        vertexMap.get(v2).add(new Edge(v1, weight));
                    }
                }

                @Override
                public boolean isPresent(V vertex) {
                    return vertexMap.containsKey(vertex);
                }

                @Override
                public double edgeWeight(V v1, V v2) {
                    Optional<Edge> edge = vertexMap.get(v1).stream().filter(e -> e.to == v2).findFirst();
                    if (edge.isPresent()) {
                        return edge.get().weight;
                    }
                    throw new IllegalArgumentException("Passed nodes are not connected.");
                }

                private V nextUnexploredNode(Set<V> explored) {
                    for (V v: vertices()) {
                        if (!explored.contains(v)) {
                            return v;
                        }
                    }
                    return null;
                }
            }
        }

        public List<List<String>> findLadders(String beginWord, String endWord, List<String> dictionary) {
            dictionary.add(beginWord);
            //dictionary.add(endWord);
            Graph<String> ladder = new Graph.AdjacencyListGraph<>();
            Set<String> nodesInGraph = new HashSet<>();

            for (String word : dictionary) {
                if (!nodesInGraph.contains(word)) {
                    ladder.insert(word);
                    nodesInGraph.add(word);
                    updateGraph(ladder, nodesInGraph, word);
                }
            }

            //dfs(ladder, beginWord, endWord, stack, result, new HashSet<>());
            return bfs(ladder, beginWord, endWord);
        }

        private void updateGraph(Graph<String> ladder, Set<String> nodesInGraph, String word) {
            String similarWord;
            for (int i=0; i<word.length(); i++) {
                String prefix = i == 0 ? new StringBuilder(word.charAt(0)).toString() : word.substring(0, i);
                String suffix = i == word.length() - 1 ? "" : word.substring(i+1);
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    similarWord = prefix + ch + suffix;
                    if (nodesInGraph.contains(similarWord) && !similarWord.equals(word)) {
                        ladder.connect(word, similarWord, 1.0);
                    }
                }
            }
        }


        private List<List<String>> bfs(Graph<String> graph, String begin, String end) {
            Queue<String> queue = new ArrayDeque<>();
            queue.add(begin);

            Map<String, List<List<String>>> trailMap = new HashMap<>();
            List<List<String>> startList = new ArrayList<>();
            startList.add(Collections.emptyList());
            trailMap.put(begin, startList); // Add empty list for begin node.

            Set<String> explored = new HashSet<>();

            while (!queue.isEmpty()) {
                String front = queue.poll();
                if (front.equals(end) || explored.contains(front)) {
                    continue;
                }

                explored.add(front);

                for (String succ: graph.successors(front)) {
                    List<List<String>> parentTrail = trailMap.get(front);
                    if (!explored.contains(succ)) {
                        queue.offer(succ);

                        List<List<String>> existingTrail = trailMap.get(succ);
                        if (existingTrail != null && parentTrail.get(0).size() + 1 == existingTrail.get(0).size()) {
                            List<List<String>> appendToExistingTrail = copyAndCreateNewListAndAddStringToEachList(parentTrail, front);
                            existingTrail.addAll(appendToExistingTrail);
                        }

                        if (existingTrail == null) {
                            trailMap.put(succ, copyAndCreateNewListAndAddStringToEachList(parentTrail, front));
                        }
                    }
                }
            }

            if (trailMap.containsKey(end)) {
                return copyAndCreateNewListAndAddStringToEachList(trailMap.get(end), end);
            }
            return Collections.emptyList();
        }

        private  List<List<String>> copyAndCreateNewListAndAddStringToEachList(List<List<String>> source, String front) {
            List<List<String>> newList = new ArrayList<>(source.size());
            for (List<String> list: source) {
                List<String> subList = new ArrayList<>(list.size());
                for (String s: list) {
                    subList.add(s);
                }
                subList.add(front);
                newList.add(subList);
            }
            return newList;
        }
    }


    /**
     * https://leetcode.com/problems/max-chunks-to-make-sorted/
     * Given an array arr that is a permutation of [0, 1, ..., arr.length - 1], we split the array into some number of
     * "chunks" (partitions), and individually sort each chunk.  After concatenating them, the result equals the sorted array.
     *
     * What is the most number of chunks we could have made?
     *
     * Example 1:
     *
     * Input: arr = [4,3,2,1,0]
     * Output: 1
     * Explanation:
     * Splitting into two or more chunks will not return the required result.
     * For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
     * Example 2:
     *
     * Input: arr = [1,0,2,3,4]
     * Output: 4
     * Explanation:
     * We can split into two chunks, such as [1, 0], [2, 3, 4].
     * However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
     */
    static class MaxChunksToMakeItSorted {
        public int maxChunksToSorted(int[] arr) {
            int end = 0, w=0;
            for (int i=0; i<arr.length; i++) {
                end = Math.max(end, arr[i]);
                if (i >= end) {
                    w++;
                }
            }
            return w;
        }
    }


    /**
     * https://leetcode.com/problems/max-chunks-to-make-sorted-ii/
     * Given an array arr of integers (not necessarily distinct), we split the array into some number of "chunks"
     * (partitions), and individually sort each chunk.  After concatenating them, the result equals the sorted array.
     *
     * What is the most number of chunks we could have made?
     *
     * Example 1:
     *
     * Input: arr = [5,4,3,2,1]
     * Output: 1
     * Explanation:
     * Splitting into two or more chunks will not return the required result.
     * For example, splitting into [5, 4], [3, 2, 1] will result in [4, 5, 1, 2, 3], which isn't sorted.
     * Example 2:
     *
     * Input: arr = [2,1,3,4,4]
     * Output: 4
     * Explanation:
     * We can split into two chunks, such as [2, 1], [3, 4, 4].
     * However, splitting into [2, 1], [3], [4], [4] is the highest number of chunks possible.
     */
    static class MaxChunksToMakeItSortedII {
        public int maxChunksToSorted(int[] arr) {
            Map<Integer, List<Integer>> positionMap = createPositionMap(arr);
            int end = Integer.MIN_VALUE, correctPos, w = 0;
            for (int i=0; i<arr.length; i++) {
                List<Integer> indexList = positionMap.get(arr[i]);
                correctPos = indexList.get(0);
                indexList.remove(0);
                end = Math.max(end, correctPos);
                if (i >= end) {
                    w++;
                    end = Integer.MIN_VALUE;
                }
            }
            return w;
        }

        private Map<Integer, List<Integer>> createPositionMap(int[] arr) {
            int[] sortedArr = Arrays.copyOf(arr, arr.length);
            Arrays.sort(sortedArr);

            Map<Integer, List<Integer>> positionMap = new HashMap<>();
            for (int i=0; i<sortedArr.length; i++) {
                List<Integer> indexes = positionMap.getOrDefault(sortedArr[i], new ArrayList<>());
                indexes.add(i);
                positionMap.put(sortedArr[i], indexes);
            }
            return positionMap;
        }
    }


    /**
     * https://leetcode.com/problems/task-scheduler/
     * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters
     * represent different tasks. Tasks could be done without original order. Each task could be done in one interval.
     * For each interval, CPU could finish one task or just be idle.
     *
     * However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n
     * intervals that CPU are doing different tasks or just be idle.
     *
     * You need to return the least number of intervals the CPU will take to finish all the given tasks.
     *
     * Example:
     * Input: tasks = ["A","A","A","B","B","B"], n = 2
     * Output: 8
     * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
     *
     *
     * Note:
     * The number of tasks is in the range [1, 10000].
     * The integer n is in the range [0, 100].
     */
    static class TaskScheduler {

        public int leastInterval(char[] tasks, int n) {
            Map<Character, Integer> map = new HashMap<>();
            for (char ch: tasks) {
                map.put(ch, map.getOrDefault(ch, 0)+1);
            }

            PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
            queue.addAll(map.values());

            int intervals = 0;
            List<Integer> removedPair = new ArrayList<>();
            while (!queue.isEmpty()) {
                removedPair.clear();

                int i=0;
                while (i<=n && !queue.isEmpty()) {
                    intervals++; i++;
                    Integer max = queue.poll();
                    if (max > 1) {
                        removedPair.add(max-1);
                    }
                }

                while (i<=n && !removedPair.isEmpty()) {
                    intervals++;
                    i++;
                }

                queue.addAll(removedPair);
            }
            return intervals;
        }
    }


    /**
     * https://leetcode.com/problems/distinct-subsequences/
     * Given a string S and a string T, count the number of distinct subsequences of S which equals T.
     *
     * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none)
     * of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence
     * of "ABCDE" while "AEC" is not).
     *
     * Example 1:
     *
     * Input: S = "rabbbit", T = "rabbit"
     * Output: 3
     * Explanation:
     *
     * As shown below, there are 3 ways you can generate "rabbit" from S.
     * (The caret symbol ^ means the chosen letters)
     *
     * rabbbit
     * ^^^^ ^^
     * rabbbit
     * ^^ ^^^^
     * rabbbit
     * ^^^ ^^^
     */
    static class DistinctSubsequences {
        public int numDistinct(String str, String text) {
            int[][] dp = new int[text.length()+1][str.length()+1];
            Arrays.fill(dp[0], 1);

            for (int t=1; t<dp.length; t++) {
                for (int s=1; s<dp[0].length; s++) {
                    char tCh = text.charAt(t-1);
                    char sCh = str.charAt(s-1);

                    dp[t][s] = dp[t][s-1];
                    dp[t][s] += tCh == sCh ? dp[t-1][s-1] : 0;
                }
            }
            return dp[dp.length-1][dp[0].length-1];
        }
    }


    /**
     * https://leetcode.com/problems/palindrome-partitioning-ii/
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     *
     * Return the minimum cuts needed for a palindrome partitioning of s.
     *
     * Example:
     *
     * Input: "aab"
     * Output: 1
     * Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
     */
    static class PalindromePartitioningII {
        public int minCut(String s) {
            int[] dp = new int[s.length()];
            dp[0] = 0;
            for (int i=1; i<dp.length; i++) {
                int min = dp[i-1] + 1;
                for (int j=i-1; j >=0; j--) {
                    if (isPalindrome(s, j, i)) {
                        int val = j == 0 ? 0 : dp[j-1] + 1;
                        min = Math.min(min, val);
                    }
                    dp[i] = min;
                }
            }
            return dp[dp.length-1];
        }

        private boolean isPalindrome(String str, int i, int j) {
            while (i<j) {
                if (str.charAt(i) != str.charAt(j)) {
                    return false;
                }
                i++; j--;
            }
            return true;
        }
    }


    /**
     * https://leetcode.com/problems/candy/
     * There are N children standing in a line. Each child is assigned a rating value.
     *
     * You are giving candies to these children subjected to the following requirements:
     *
     * Each child must have at least one candy.
     * Children with a higher rating get more candies than their neighbors.
     * What is the minimum candies you must give?
     *
     * Example 1:
     *
     * Input: [1,0,2]
     * Output: 5
     * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
     * Example 2:
     *
     * Input: [1,2,2]
     * Output: 4
     * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
     *              The third child gets 1 candy because it satisfies the above two conditions.
     */
    static class Candy {
        public int candy(int[] ratings) {
            int count[] = new int[ratings.length];
            Arrays.fill(count, 1);

            for (int i=1; i < ratings.length; i++) {
                if (ratings[i] > ratings[i-1]) {
                    count[i] = count[i -1] + 1;
                }
            }

            for (int i=ratings.length-2; i >= 0; i--) {
                if (ratings[i] > ratings[i+1]) {
                    count[i] = Math.max(count[i], count[i+1] + 1);
                }
            }

            int result = 0;
            for (int i=0; i<count.length; i++) {
                result += count[i];
            }
            return result;
        }
    }


    /**
     * https://leetcode.com/problems/word-search-ii/
     * Given a 2D board and a list of words from the dictionary, find all words in the board.
     *
     * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
     * horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
     *
     *
     *
     * Example:
     *
     * Input:
     * board = [
     *   ['o','a','a','n'],
     *   ['e','t','a','e'],
     *   ['i','h','k','r'],
     *   ['i','f','l','v']
     * ]
     * words = ["oath","pea","eat","rain"]
     *
     * Output: ["eat","oath"]
     *
     *
     * Note:
     *
     * All inputs are consist of lowercase letters a-z.
     * The values of words are distinct.
     */
    static class WordSearchII {

        static class Trie {

            static class Node {
                Node[] children;
                char ch;
                boolean word;

                public Node(char ch, boolean word) {
                    children = new Node[26];
                    this.ch = ch;
                    this.word = word;
                }
            }

            private Node root = new Node('#', false);

            public void add(String word) {
                Node ptr = root;
                for (char ch: word.toCharArray()) {
                    int index = ch - 'a';
                    if (ptr.children[index] == null) {
                        ptr.children[index] = new Node(ch, false);
                    }
                    ptr = ptr.children[index];
                }
                ptr.word = true;
            }

            public boolean isWord(String word) {
                Node ptr = find(word);
                if (ptr == null) {
                    return false;
                }
                return ptr.word;
            }

            public boolean containsPrefix(String prefix) {
                return find(prefix) != null;
            }

            private Node find(String word) {
                Node ptr = root;
                for (char ch: word.toCharArray()) {
                    int index = ch - 'a';
                    if (index < 0 || ptr.children[index] == null) {
                        return null;
                    }
                    ptr = ptr.children[index];
                }
                return ptr;
            }
        }

        private Trie trie;
        private int[][] moves = {{1,0}, {-1,0}, {0,1}, {0, -1}};


        public List<String> findWords(char[][] board, String[] words) {
            trie = new Trie();
            for (String w: words) {
                trie.add(w);
            }

            Set<String> result = new HashSet<>();
            for (int i=0; i<board.length; i++) {
                for (int j=0; j<board[0].length; j++) {
                    findWords(board, i, j, "" + board[i][j], result);
                }
            }
            return new ArrayList<>(result);
        }

        void findWords(char[][] board, int i, int j, String prefix, Set<String> result) {
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
                return;
            }

            if (trie.isWord(prefix)) {
                result.add(prefix);
            }

            for (int move=0; move <4; move++) {
                int newI = i + moves[move][0];
                int newJ = j + moves[move][1];
                if (canMove(board, prefix, newI, newJ)) {
                    char temp = board[i][j];
                    board[i][j] = '*';
                    findWords(board, newI, newJ, prefix + board[newI][newJ], result);
                    board[i][j] = temp;
                }
            }
        }

        boolean canMove(char[][] board, String prefix, int i, int j) {
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
                return false;
            }
            return trie.containsPrefix(prefix + board[i][j]);
        }

    }


    /**
     * https://leetcode.com/problems/concatenated-words/
     * Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.
     * A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.
     *
     * Example:
     * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
     *
     * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
     *
     * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     *  "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
     * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
     * Note:
     * The number of elements of the given array will not exceed 10,000
     * The length sum of elements in the given array will not exceed 600,000.
     * All the input string will only include lower case letters.
     * The returned elements order does not matter.
     */
    static class ConcatenatedWords {

        class Trie {
            class Node {
                Node[] children;
                char ch;
                boolean word;

                public Node(char ch, boolean word) {
                    children = new Node[26];
                    this.ch = ch;
                    this.word = word;
                }
            }

            private Node root = new Node('#', false);

            public void add(String word) {
                Node ptr = root;
                for (char ch: word.toCharArray()) {
                    int index = ch - 'a';
                    if (ptr.children[index] == null) {
                        ptr.children[index] = new Node(ch, false);
                    }
                    ptr = ptr.children[index];
                }
                ptr.word = true;
            }

            public boolean isWord(String word) {
                Node ptr = find(word);
                if (ptr == null) {
                    return false;
                }
                return ptr.word;
            }

            public boolean containsPrefix(String prefix) {
                return find(prefix) != null;
            }

            private Node find(String word) {
                Node ptr = root;
                for (char ch: word.toCharArray()) {
                    int index = ch - 'a';
                    if (index < 0 || ptr.children[index] == null) {
                        return null;
                    }
                    ptr = ptr.children[index];
                }
                return ptr;
            }
        }

        private Comparator<String> comp = (a, b) -> Integer.compare(a.length(), b.length());
        private Set<String> cache;
        private Trie trie;

        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            trie = new Trie();
            cache = new HashSet<>();

            Arrays.sort(words, comp);
            List<String> result = new ArrayList<>();

            for (String w: words) {
                if (search(w)) {
                    result.add(w);
                }
                trie.add(w);
            }
            return result;
        }

        public boolean search(String word) {
            if (trie.isWord(word) || cache.contains(word)) {
                return true;
            }

            for (int i=0; i<word.length(); i++) {
                String subWord = word.substring(0, i+1);
                if (!trie.containsPrefix(subWord)) {
                    return false;
                }

                if (trie.isWord(subWord) && search(word.substring(i+1))) {
                    cache.add(word.substring(i+1));
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * https://leetcode.com/problems/frog-jump/
     * A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone.
     * The frog can jump on a stone, but it must not jump into the water.
     *
     * Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross
     * the river by landing on the last stone. Initially, the frog is on the first stone and assume the first jump must be 1 unit.
     *
     * If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. Note that the
     * frog can only jump in the forward direction.
     *
     * Note:
     *
     * The number of stones is ≥ 2 and is < 1,100.
     * Each stone's position will be a non-negative integer < 231.
     * The first stone's position is always 0.
     * Example 1:
     *
     * [0,1,3,5,6,8,12,17]
     *
     * There are a total of 8 stones.
     * The first stone at the 0th unit, second stone at the 1st unit,
     * third stone at the 3rd unit, and so on...
     * The last stone at the 17th unit.
     *
     * Return true. The frog can jump to the last stone by jumping
     * 1 unit to the 2nd stone, then 2 units to the 3rd stone, then
     * 2 units to the 4th stone, then 3 units to the 6th stone,
     * 4 units to the 7th stone, and 5 units to the 8th stone.
     * Example 2:
     *
     * [0,1,2,3,4,8,9,11]
     *
     * Return false. There is no way to jump to the last stone as
     * the gap between the 5th and 6th stone is too large.
     */
    static class FrogJump {
        public boolean canCross(int[] stones) {
            if (stones.length == 0) {
                return true;
            }

            if (stones.length == 2) {
                return stones[1] == 1;
            }

            Set<Integer> numbers = new HashSet<>();
            for (int s: stones) {
                numbers.add(s);
            }

            return _canCross(stones, stones[stones.length-1], 1, 1, numbers, new HashSet<String>());
        }

        private boolean _canCross(int[] stones, int target, int val, int k, Set<Integer> numbers, Set<String> cache) {

            if (val == target) {
                return true;
            }

            if (!numbers.contains(val) || val > target || k == 0) {
                return false;
            }

            String cacheValue = val + " " + k;
            if (cache.contains(cacheValue)) {
                return false;
            }
            cache.add(cacheValue);

            return  _canCross(stones, target, val + k-1, k-1, numbers, cache) ||
                    _canCross(stones, target, val + k, k, numbers, cache) ||
                    _canCross(stones, target, val + k + 1, k + 1, numbers, cache);
        }
    }


    /**
     * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
     * You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number
     * from each of the k lists.
     *
     * We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.
     *
     *
     *
     * Example 1:
     *
     * Input: [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
     * Output: [20,24]
     * Explanation:
     * List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
     * List 2: [0, 9, 12, 20], 20 is in range [20,24].
     * List 3: [5, 18, 22, 30], 22 is in range [20,24].
     *
     *
     * Note:
     *
     * The given list may contain duplicates, so ascending order means >= here.
     * 1 <= k <= 3500
     * -105 <= value of elements <= 105.
     */
    static class SmallestRangeCoveringElementsFromKLists {

        class Node {
            int item;
            int listIndex;
            int itemIndex;

            public Node(int i, int lIndex, int iIndex) {
                this.item = i;
                this.listIndex = lIndex;
                this.itemIndex = iIndex;
            }
        }

        private Comparator<Node> comparator = (a, b) -> Integer.compare(a.item, b.item);

        public int[] smallestRange(List<List<Integer>> nums) {
            // Create Priority Queue
            PriorityQueue<Node> queue = new PriorityQueue<>(comparator);
            int max = Integer.MIN_VALUE;

            // Initialize Priority Queue
            for (int i=0; i < nums.size(); i++) {
                int val = nums.get(i).get(0);
                max = Math.max(max, val);
                queue.offer(new Node(val, i, 0));
            }

            // Find the smallest range with all elements.
            int min = Integer.MAX_VALUE, start = 0, end = 0;
            do  {
                Node minNode = queue.poll();
                if (max - minNode.item < min) {
                    min = max - minNode.item;
                    start = minNode.item;
                    end = max;
                }

                if (nums.get(minNode.listIndex).size() - 1 != minNode.itemIndex) {
                    minNode.itemIndex++;
                    minNode.item = nums.get(minNode.listIndex).get(minNode.itemIndex);
                    max = Math.max(max, minNode.item);
                    queue.offer(minNode);
                }
            } while (queue.size() >= nums.size());

            return new int[] {start, end};
        }
    }


    /**
     * https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
     * Design a data structure that supports all following operations in average O(1) time.
     *
     * Note: Duplicate elements are allowed.
     * insert(val): Inserts an item val to the collection.
     * remove(val): Removes an item val from the collection if present.
     * getRandom: Returns a random element from current collection of elements. The probability of each element being
     *   returned is linearly related to the number of same value the collection contains.
     */
    static class RandomizedCollection {

        private Map<Integer, Set<Integer>> map;
        private List<Integer> list;
        private Random random;

        /** Initialize your data structure here. */
        public RandomizedCollection() {
            map = new HashMap<>();
            list = new ArrayList<>();
            random = new Random();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            boolean flag = true;
            if (map.containsKey(val)) {
                flag = false;
            }

            int index = list.size();
            list.add(val);
            Set<Integer> elementIndexes = map.getOrDefault(val, new HashSet<>());
            elementIndexes.add(index);
            map.put(val, elementIndexes);
            return flag;
        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            // When the key is not present in the data structure.
            if (!map.containsKey(val)) {
                return false;
            }

            // Find index of deleted element
            Set<Integer> removeIndexesSet = map.get(val);
            int removeIndex = removeIndexesSet.stream().findFirst().get();
            removeIndexesSet.remove(removeIndex);

            // If the set of indexes become empty then remove the item from map
            if (removeIndexesSet.isEmpty()) {
                map.remove(val);
            }


            // Update position of last element.
            int lastItem = list.get(list.size()-1);
            list.set(removeIndex, lastItem);

            // Update indexes of last element in the map. If check is required for the cases
            // when the element deleted earlier was present at the last index previously, we cannot delete it twice.
            if (map.containsKey(lastItem)) {
                Set<Integer> lastItemIndexes = map.get(lastItem);
                lastItemIndexes.add(removeIndex);
                lastItemIndexes.remove(list.size()-1);
            }

            // Delete last index from the list.
            list.remove(list.size()-1);

            return true;
        }


        /** Get a random element from the collection. */
        public int getRandom() {
            return list.get(random.nextInt(list.size()));
        }

    }


    /**
     * https://leetcode.com/problems/strange-printer/
     * There is a strange printer with the following two special requirements:
     *
     * The printer can only print a sequence of the same character each time.
     * At each turn, the printer can print new characters starting from and ending at any places, and will cover the
     * original existing characters.
     * Given a string consists of lower English letters only, your job is to count the minimum number of turns the
     * printer needed in order to print it.
     *
     * Example 1:
     * Input: "aaabbb"
     * Output: 2
     * Explanation: Print "aaa" first and then print "bbb".
     * Example 2:
     * Input: "aba"
     * Output: 2
     * Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the existing character 'a'.
     */
    static class StrangePrinter {

        int[][] cache;

        public int strangePrinter(String s) {
            if (s.length() < 2) {
                return s.length();
            }

            cache = new int[s.length()][s.length()];

            // Compress the original string "aaaabbb" becomes "ab"
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<s.length(); ) {
                builder.append(s.charAt(i));
                int j = i+1;
                while (j < s.length() && s.charAt(j) == s.charAt(i)) {
                    j++;
                }
                i = j;
            }
            String compactString = builder.toString();
            // return minimumPrints(compactString, 0, compactString.length()-1);
            return bottomupMinimumPrints(compactString);
        }

        /**
         * For each character at the end of the string, lets consider 2 cases:
         *
         * 1. It was simply inserted with the cost of 1
         * 2. It was free from some previous step to the left that printed this character already (we can print extra
         * character all the way till the end)
         *
         * Consider string CABBA. Last character could be simply inserted after a string CABB with the cost of 1 or it
         * could be free since there is an A character to the left and we could simply print extra As all the way till
         * the end: CAAAA we just need to consider the cost of building string CA (same as CAAAA) and BB, in other words,
         * split remaining string into CA | BB | A
         */
        private int minimumPrints(String str, int i, int j) {
            if (i >= j) {
                return i > j ? 0 : 1;
            }

            if (cache[i][j] != 0) {
                return cache[i][j];
            }

            int cost = 1 + minimumPrints(str, i, j-1);
            for (int k=i; k < j; k++) {
                if (str.charAt(j) == str.charAt(k)) {
                    cost = Math.min(cost, minimumPrints(str, i, k) + minimumPrints(str, k+1, j-1));
                }
            }
            cache[i][j] = cost;
            return cost;
        }

        private int bottomupMinimumPrints(String str) {
            int[][] dp = new int[str.length()][str.length()];

            for (int i=0; i<dp.length; i++) {
                dp[i][i] = 1;
            }

            for (int len =1; len < dp.length; len++) {
                for (int i=0, j=i+len; j < dp.length; i++, j++) {
                    dp[i][j] = 1 + dp[i][j-1];
                    for (int k=i; k < j; k++) {
                        if (str.charAt(k) == str.charAt(j)) {
                            dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k+1][j-1]);
                        }
                    }
                }
            }
            return dp[0][dp.length-1];
        }
    }


    /**
     * https://leetcode.com/problems/cut-off-trees-for-golf-event/
     * You are asked to cut off trees in a forest for a golf event. The forest is represented as a non-negative 2D map, in this map:
     *
     *     0 represents the obstacle can't be reached.
     *     1 represents the ground can be walked through.
     *     The place with number bigger than 1 represents a tree can be walked through, and this positive number represents
     *     the tree's height.
     *
     *
     *
     * You are asked to cut off all the trees in this forest in the order of tree's height - always cut off the tree
     * with lowest height first. And after cutting, the original place has the tree will become a grass (value 1).
     *
     * You will start from the point (0, 0) and you should output the minimum steps you need to walk to cut off all the trees.
     * If you can't cut off all the trees, output -1 in that situation.
     *
     * You are guaranteed that no two trees have the same height and there is at least one tree needs to be cut off.
     *
     * Example 1:
     *
     * Input:
     * [
     *  [1,2,3],
     *  [0,0,4],
     *  [7,6,5]
     * ]
     * Output: 6
     *
     *
     *
     * Example 2:
     *
     * Input:
     * [
     *  [1,2,3],
     *  [0,0,0],
     *  [7,6,5]
     * ]
     * Output: -1
     *
     *
     *
     * Example 3:
     *
     * Input:
     * [
     *  [2,3,4],
     *  [0,0,5],
     *  [8,7,6]
     * ]
     * Output: 6
     * Explanation: You started from the point (0,0) and you can cut off the tree in (0,0) directly without walking.
     */
    static class CutOffTreesForGolfEvent {

        class Item {
            int val, xPos, yPos;
            public Item(int v, int x, int y) {val = v; xPos = x; yPos = y;}
        }

        int[][] moves = {{-1, 0}, {1,0}, {0,-1}, {0,1}};
        Comparator<Item> compartor = (a,b) -> Integer.compare(a.val, b.val);

        public int cutOffTree(List<List<Integer>> forest) {

            PriorityQueue<Item> queue = new PriorityQueue<>(compartor);

            // Create Matrix and initialize Priority Queue
            int[][] matrix = new int[forest.size()][forest.get(0).size()];
            for (int i=0; i<forest.size(); i++) {
                for (int j=0; j<forest.get(i).size(); j++) {
                    matrix[i][j] = forest.get(i).get(j);
                    if (matrix[i][j] > 1) {
                        queue.offer(new Item(matrix[i][j], i, j));
                    }
                }
            }

            int curX = 0, curY = 0, steps = 0;
            while (!queue.isEmpty()) {
                Item next = queue.poll();

                // int distance = dfs(matrix, curX, curY, next.xPos, next.yPos);
                int distance = bfs(matrix, curX, curY, next.xPos, next.yPos);
                if (distance == -1) {return -1;}

                steps += distance;
                curX = next.xPos;
                curY = next.yPos;
            }
            return steps;
        }

        private int dfs(int[][] matrix, int aX, int aY, int bX, int bY) {
            if (aX == bX && aY == bY) {
                return 0;
            }

            int nextX, nextY, minCost = Integer.MAX_VALUE;
            for (int[] move: moves) {

                nextX = aX + move[0];
                nextY = aY + move[1];

                if (isPossible(matrix, nextX, nextY)) {
                    int temp = matrix[aX][aY];
                    matrix[aX][aY] = 0;

                    int cost = bfs(matrix, nextX, nextY, bX, bY) + 1;
                    matrix[aX][aY] = temp;

                    if (cost > 0) {
                        minCost = Math.min(minCost, cost);
                    }

                }
            }
            return minCost == Integer.MAX_VALUE ? -1 : minCost;
        }

        private int bfs(int[][] matrix, int sx, int sy, int dx, int dy) {
            if (sx == dx && sy == dy) {
                return 0;
            }
            boolean visited[][] = new boolean[matrix.length][matrix[0].length];
            Queue<int[]> queue = new ArrayDeque<>();
            queue.offer(new int[]{sx, sy, 0});
            visited[sx][sy] = true;

            while (!queue.isEmpty()) {
                int[] current = queue.poll();

                if (current[0] == dx && current[1] == dy) {
                    return current[2];
                }

                for (int[] move: moves) {
                    int neighborX = current[0] + move[0];
                    int neighborY = current[1] + move[1];
                    int cost = current[2] + 1;

                    if (!isPossible(matrix, neighborX, neighborY) || visited[neighborX][neighborY]) {
                        continue;
                    }

                    queue.offer(new int[] {neighborX, neighborY, cost});
                    visited[neighborX][neighborY] = true;
                }
            }
            return -1;
        }

        private boolean isPossible(int[][] matrix, int i, int j) {
            if (i >= matrix.length || j >= matrix[0].length || i < 0 || j < 0 || matrix[i][j] == 0) {
                return false;
            }
            return true;
        }
    }


    /**
     * https://leetcode.com/problems/prefix-and-suffix-search/
     * Given many words, words[i] has weight i.
     *
     * Design a class WordFilter that supports one function, WordFilter.f(String prefix, String suffix). It will return
     * the word with given prefix and suffix with maximum weight. If no word exists, return -1.
     *
     * Examples:
     *
     * Input:
     * WordFilter(["apple"])
     * WordFilter.f("a", "e") // returns 0
     * WordFilter.f("b", "") // returns -1
     *
     *
     * Note:
     *
     * words has length in range [1, 15000].
     * For each test case, up to words.length queries WordFilter.f may be made.
     * words[i] has length in range [1, 10].
     * prefix, suffix have lengths in range [0, 10].
     * words[i] and prefix, suffix queries consist of lowercase letters only.
     */
    static class WordFilter {

        class Trie {
            private class Node {
                Node[] children;
                char ch;
                boolean word;
                int max;

                public Node(char ch, boolean word) {
                    children = new Node[27];
                    this.ch = ch;
                    this.word = word;
                    this.max = -1;
                }
            }

            private Node root = new Node('#', false);

            public void add(String word, int i) {
                Node ptr = root;
                for (char ch: word.toCharArray()) {
                    int index = ch - 'a';
                    if (ptr.children[index] == null) {
                        ptr.children[index] = new Node(ch, false);
                    }
                    ptr = ptr.children[index];
                }
                ptr.word = true;
                ptr.max = Math.max(ptr.max, i);
            }

            private int count(String prefix) {
                Node ptr = find(prefix);
                return ptr == null ? -1 : ptr.max;
            }

            private Node find(String word) {
                Node ptr = root;
                for (char ch: word.toCharArray()) {
                    int index = ch - 'a';
                    if (ptr.children[index] == null) {
                        return null;
                    }
                    ptr = ptr.children[index];
                }
                return ptr;
            }
        }

        // Using this as delimeter as it follows z in ascii code.
        private char DELIMETER = '{';
        private Trie trie;

        public WordFilter(String[] words) {
            trie = new Trie();
            for (int wIndex = 0; wIndex < words.length; wIndex++) {
                String w = words[wIndex];

                for (int i=0; i<= w.length(); i++) {
                    String prefix = w.substring(0, i);

                    for (int j=w.length(); j >=0 ; j--) {
                        String suffix = w.substring(j);
                        trie.add(prefix + DELIMETER + suffix, wIndex);
                    }
                }
            }
        }

        public int f(String prefix, String suffix) {
            return trie.count(prefix + DELIMETER + suffix);
        }
    }


    /**
     * https://leetcode.com/problems/car-fleet/
     * N cars are going to the same destination along a one lane road.  The destination is target miles away.
     *
     * Each car i has a constant speed speed[i] (in miles per hour), and initial position position[i] miles towards the
     * target along the road.
     *
     * A car can never pass another car ahead of it, but it can catch up to it, and drive bumper to bumper at the same speed.
     *
     * The distance between these two cars is ignored - they are assumed to have the same position.
     *
     * A car fleet is some non-empty set of cars driving at the same position and same speed.  Note that a single car is also a car fleet.
     *
     * If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.
     *
     *
     * How many car fleets will arrive at the destination?
     *
     *
     *
     * Example 1:
     *
     * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
     * Output: 3
     * Explanation:
     * The cars starting at 10 and 8 become a fleet, meeting each other at 12.
     * The car starting at 0 doesn't catch up to any other car, so it is a fleet by itself.
     * The cars starting at 5 and 3 become a fleet, meeting each other at 6.
     * Note that no other cars meet these fleets before the destination, so the answer is 3.
     */
    static class CarFleet {

        private int SPEED = 0;
        private int DIST = 1;

        private Comparator<int[]> comparator = (a,b) -> Integer.compare(a[DIST], b[DIST]);

        public int carFleet(int target, int[] position, int[] speed) {

            if (position.length == 0 || position.length == 1) {
                return position.length;
            }

            int[][] speedDistance = new int[position.length][2];
            for (int i=0; i<speedDistance.length; i++) {
                speedDistance[i][SPEED] = speed[i];
                speedDistance[i][DIST] = target - position[i];
            }

            Arrays.sort(speedDistance, comparator);
            double[] time = new double[position.length];
            for (int i=0; i<time.length; i++) {
                time[i] = (double) speedDistance[i][DIST] / speedDistance[i][SPEED];
            }

            double prev = time[0];
            int fleet = 0;
            for (int i=0; i<time.length; ) {
                int j = i+1;
                while (j < time.length && time[j] <= prev) {
                    j++;
                }
                fleet++;

                i = j;
                if (i < time.length) {
                    prev = time[i];
                }
            }
            return fleet;
        }
    }


    /**
     * https://leetcode.com/problems/swap-nodes-in-pairs/
     * Given a linked list, swap every two adjacent nodes and return its head.
     * You may not modify the values in the list's nodes, only nodes itself may be changed.
     *
     * Example:
     * Given 1->2->3->4, you should return the list as 2->1->4->3.
     */
    static class SwapNodesInPair {

        class ListNode {
            int val;
            ListNode next;
            public ListNode(int v) {this.val = v;}
        }

        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            ListNode p = null, q = head, r = null;
            head = q.next;

            while (q != null && q.next != null) {
                r = q.next;
                if (p != null) {
                    p.next = r;
                }
                q.next = r.next;
                r.next = q;
                p = q;
                q = q.next;
            }
            return head;
        }
    }


    /**
     * https://leetcode.com/problems/largest-divisible-subset/
     * Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this subset satisfies:
     *
     * Si % Sj = 0 or Sj % Si = 0.
     *
     * If there are multiple solutions, return any subset is fine.
     *
     * Example 1:
     *
     * Input: [1,2,3]
     * Output: [1,2] (of course, [1,3] will also be ok)
     * Example 2:
     *
     * Input: [1,2,4,8]
     * Output: [1,2,4,8]
     */
    static class LargestDivisibleSubset {
        public List<Integer> largestDivisibleSubset(int[] nums) {
            if (nums.length == 0) {
                return Collections.emptyList();
            }

            Arrays.sort(nums);
            int maxIndex = 0;
            List<Integer>[] dp = new List[nums.length];

            for (int i=0; i<nums.length; i++) {
                dp[i] = new ArrayList<>();
                int size =0, max = -1;
                for (int j=i-1; j>=0; j--) {
                    if (nums[i] % nums[j] == 0 && dp[j].size() > size) {
                        size = dp[j].size();
                        max = j;
                    }
                }

                if (max != -1) {
                    dp[i].addAll(dp[max]);
                }
                dp[i].add(nums[i]);
                maxIndex = dp[maxIndex].size() > dp[i].size() ? maxIndex : i;
            }
            return dp[maxIndex];
        }
    }


    /**
     * https://leetcode.com/problems/maximal-square/
     * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
     *
     * Example:
     *
     * Input:
     *
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     *
     * Output: 4
     */
    static class MaximalSquare {
        public int maximalSquare(char[][] matrix) {
            if (matrix.length == 0) {
                return 0;
            }

            int[][] dp = new int[matrix.length][matrix[0].length];
            int max = 0;
            for (int i=0; i<dp.length; i++) {
                for (int j=0; j<dp[0].length; j++) {
                    if (matrix[i][j] == '0') {
                        continue;
                    }
                    if (i==0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1;
                    }
                    max = Math.max(dp[i][j], max);
                }
            }
            return max * max;
        }

        private int min(int a, int b, int c) {
            int min = Math.min(a,b);
            return Math.min(min, c);
        }
    }


    /**
     * https://leetcode.com/problems/target-sum/
     * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -.
     * For each integer, you should choose one from + and - as its new symbol.
     *
     * Find out how many ways to assign symbols to make sum of integers equal to target S.
     *
     * Example 1:
     * Input: nums is [1, 1, 1, 1, 1], S is 3.
     * Output: 5
     * Explanation:
     *
     * -1+1+1+1+1 = 3
     * +1-1+1+1+1 = 3
     * +1+1-1+1+1 = 3
     * +1+1+1-1+1 = 3
     * +1+1+1+1-1 = 3
     *
     * There are 5 ways to assign symbols to make the sum of nums be target 3.
     * Note:
     * The length of the given array is positive and will not exceed 20.
     * The sum of elements in the given array will not exceed 1000.
     * Your output answer is guaranteed to be fitted in a 32-bit integer.
     */
    static class TargetSum {

        Map<Integer, Integer> cache = null;

        public int findTargetSumWays(int[] nums, int S) {
            cache = new HashMap<>();
            return memoizedSumWays(nums, 0, S, 0);
        }

        private int recursiveSumWays(int[] nums, int index, int target, int sum) {
            if (index == nums.length) {
                return sum == target ? 1 : 0;
            }

            int add = recursiveSumWays(nums, index+1, target, sum + nums[index]);
            int subtract = recursiveSumWays(nums, index+1, target, sum - nums[index]);
            return add + subtract;
        }

        private int memoizedSumWays(int[] nums, int index, int target, int sum) {
            if (cache.containsKey(sum)) {
                return cache.get(sum);
            }

            if (index == nums.length) {
                return sum == target ? 1 : 0;
            }

            int ways = 0;
            ways += recursiveSumWays(nums, index+1, target, sum + nums[index]);
            ways += recursiveSumWays(nums, index+1, target, sum - nums[index]);
            cache.put(sum, ways);
            return ways;
        }
    }


    /**
     * https://leetcode.com/problems/minimum-cost-for-tickets/
     * In a country popular for train travel, you have planned some train travelling one year in advance.
     * The days of the year that you will travel is given as an array days.  Each day is an integer from 1 to 365.
     *
     * Train tickets are sold in 3 different ways:
     *
     * a 1-day pass is sold for costs[0] dollars;
     * a 7-day pass is sold for costs[1] dollars;
     * a 30-day pass is sold for costs[2] dollars.
     * The passes allow that many days of consecutive travel.  For example, if we get a 7-day pass on day 2,
     * then we can travel for 7 days: day 2, 3, 4, 5, 6, 7, and 8.
     *
     * Return the minimum number of dollars you need to travel every day in the given list of days.
     *
     *
     *
     * Example 1:
     *
     * Input: days = [1,4,6,7,8,20], costs = [2,7,15]
     * Output: 11
     * Explanation:
     * For example, here is one way to buy passes that lets you travel your travel plan:
     * On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
     * On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
     * On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
     * In total you spent $11 and covered all the days of your travel.
     * Example 2:
     *
     * Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
     * Output: 17
     * Explanation:
     * For example, here is one way to buy passes that lets you travel your travel plan:
     * On day 1, you bought a 30-day pass for costs[2] = $15 which covered days 1, 2, ..., 30.
     * On day 31, you bought a 1-day pass for costs[0] = $2 which covered day 31.
     * In total you spent $17 and covered all the days of your travel.
     */
    static class MinimumCostForTickets {
        public int mincostTickets(int[] days, int[] costs) {

            if (days.length == 0) {
                return 0;
            }

            int[] dp = new int[days.length];
            dp[0] = minimum(costs[0], costs[1], costs[2]);
            for (int i=1; i<dp.length; i++) {
                int oneDayCost = costs[0] + dp[i-1];
                int sevenDayCost = costForDays(dp, days, i, costs[1], 7);
                int thirtyDayCost = costForDays(dp, days, i, costs[2], 30);
                dp[i] = minimum(oneDayCost, sevenDayCost, thirtyDayCost);
            }

            return dp[dp.length-1];
        }

        public int costForDays(int[] dp, int[] days, int i, int cost, int validity) {
            int j = i-1;
            while (j >=0 && days[i] - days[j] < validity) {
                j--;
            }
            return j < 0 ? cost : dp[j] + cost;
        }

        private int minimum(int a, int b, int c) {
            int min = Math.min(a,b);
            return Math.min(min, c);
        }
    }


    /**
     * https://leetcode.com/problems/combination-sum-iv/
     * Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that
     * add up to a positive integer target.
     *
     * Example:
     *
     * nums = [1, 2, 3]
     * target = 4
     *
     * The possible combination ways are:
     * (1, 1, 1, 1)
     * (1, 1, 2)
     * (1, 2, 1)
     * (1, 3)
     * (2, 1, 1)
     * (2, 2)
     * (3, 1)
     *
     * Note that different sequences are counted as different combinations.
     *
     * Therefore the output is 7.
     *
     *
     * Follow up:
     * What if negative numbers are allowed in the given array?
     * How does it change the problem?
     * What limitation we need to add to the question to allow negative numbers?
     */
    static class CombinationSum {
        public int combinationSum4(int[] nums, int target) {
            int[] dp = new int[target+1];
            // Arrays.fill(dp, -1);
            // return memoizationSolution(nums, dp, target, 0);
            return tabulatedSolution(nums, dp, target);
        }

        private int tabulatedSolution(int[] nums, int[] dp, int target) {
            dp[0] = 1;
            for (int i=1; i< dp.length; i++) {
                for (int j=0; j < nums.length; j++) {
                    if (i - nums[j] >= 0) {
                        dp[i] += dp[i-nums[j]];
                    }
                }
            }
            return dp[target];
        }

        private int memoizationSolution(int[] nums, int[] dp, int target, int sum) {
            if (sum > target) {
                return 0;
            }

            if (sum == target) {
                return 1;
            }

            if (dp[sum] != -1) {
                return dp[sum];
            }

            int count = 0;
            for (int n : nums) {
                count += memoizationSolution(nums, dp, target, sum + n);
            }
            dp[sum] = count;
            return count;
        }
    }


    /**
     * https://leetcode.com/problems/house-robber-ii/
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed.
     * All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one.
     * Meanwhile, adjacent houses have security system connected and it will automatically contact the police if two
     * adjacent houses were broken into on the same night.
     *
     * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount o
     * f money you can rob tonight without alerting the police.
     *
     * Example 1:
     *
     * Input: [2,3,2]
     * Output: 3
     * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
     *              because they are adjacent houses.
     * Example 2:
     *
     * Input: [1,2,3,1]
     * Output: 4
     * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
     *              Total amount you can rob = 1 + 3 = 4.
     */
    static class HouseRobberII {

        public int rob(int[] cost) {
            if (cost.length == 0) {
                return 0;
            } else if (cost.length == 1) {
                return cost[0];
            } else if (cost.length == 2) {
                return Math.max(cost[0], cost[1]);
            }
            return Math.max(maxSum(cost, 0, cost.length-1), maxSum(cost, 1, cost.length));
        }

        private int maxSum(int[] cost, int start, int end) {
            int[] dp = new int[cost.length];
            dp[start] = cost[start];
            dp[start+1] = Math.max(cost[start], cost[start+1]);
            for (int i=start+2; i < dp.length; i++) {
                dp[i] = Math.max(dp[i-1], dp[i-2] + cost[i]);
            }
            return dp[end-1];
        }
    }


    /**
     * https://leetcode.com/problems/subsets-ii/
     * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
     *
     * Note: The solution set must not contain duplicate subsets.
     *
     * Example:
     *
     * Input: [1,2,2]
     * Output:
     * [
     *   [2],
     *   [1],
     *   [1,2,2],
     *   [2,2],
     *   [1,2],
     *   []
     * ]
     */
    static class SubsetsII {
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            if (nums.length == 0) {
                return result;
            }
            Set<String> sets = new HashSet<>();
            recursiveSets(sets, "", nums, 0);
            for (String s: sets) {
                result.add(convertToList(s));
            }
            return result;
        }


        private void recursiveSets(Set<String> set, String str, int[] nums, int index) {
            set.add(sortedString(str));
            if (index >= nums.length) {
                return;
            }
            recursiveSets(set, str, nums, index+1);
            recursiveSets(set, str + "#" + nums[index], nums, index+1);
        }

        private List<Integer> convertToList(String s) {
            String[] numbers = s.split("#");
            List<Integer> output = new ArrayList<>();
            for (String n: numbers) {
                if (!n.isEmpty()) {
                    output.add(Integer.valueOf(n));
                }
            }
            return output;
        }

        private String sortedString(String str) {
            String[] strings = str.split("#");
            Arrays.sort(strings);
            StringBuilder builder = new StringBuilder();
            for (String s: strings) {
                builder.append(s);
            }
            return builder.toString();
        }
    }


    /**
     * https://leetcode.com/problems/majority-element-ii/
     * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
     *
     * Note: The algorithm should run in linear time and in O(1) space.
     *
     * Example 1:
     *
     * Input: [3,2,3]
     * Output: [3]
     * Example 2:
     *
     * Input: [1,1,1,3,3,2,2,2]
     * Output: [1,2]
     **/
    static class MajorityElementII {
        public List<Integer> majorityElement(int[] nums) {
            List<Integer> result = new ArrayList<>();
            if (nums.length == 0)  {
                return result;
            }

            int a = nums[0], na = 1, b = Integer.MIN_VALUE, nb = 0;
            for (int i=1; i<nums.length; i++) {
                int n = nums[i];
                if (a == n) {
                    na++;
                } else if (b == n) {
                    nb++;
                } else if (nb == 0) {
                    nb = 1;
                    b = n;
                } else if (na == 0) {
                    na = 1;
                    a = n;
                } else {
                    na--;
                    nb--;
                }
            }
            if (isMajorityElement(nums, a)) {
                result.add(a);
            }

            if (a != b && isMajorityElement(nums, b)) {
                result.add(b);
            }
            return result;
        }

        private boolean isMajorityElement(int[] nums, int a) {
            int count = 0;
            for (int n: nums) {
                if (a == n) {
                    count++;
                }
            }
            return count > nums.length/3;
        }
    }


    /**
     * https://leetcode.com/problems/non-overlapping-intervals/
     * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the
     * intervals non-overlapping.
     *
     * Example 1:
     *
     * Input: [[1,2],[2,3],[3,4],[1,3]]
     * Output: 1
     * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
     * Example 2:
     *
     * Input: [[1,2],[1,2],[1,2]]
     * Output: 2
     * Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
     * Example 3:
     *
     * Input: [[1,2],[2,3]]
     * Output: 0
     * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
     *
     *
     * Note:
     *
     * You may assume the interval's end point is always bigger than its start point.
     * Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
     */
    static class NonOverlappingIntervals {

        private Comparator<int[]> comp = (a,b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        };
        private int START = 0;
        private int END = 1;

        public int eraseOverlapIntervals(int[][] intervals) {
            if (intervals.length < 2) {
                return 0;
            }
            Arrays.sort(intervals, comp);
            int end = intervals[0][1];
            int count = 0;
            for (int i=1; i<intervals.length; i++) {
                if (intervals[i][START] < end) {
                    // In case of overlap always keep the interval with lower end.
                    count++;
                    end = Math.min(intervals[i][END], end);
                } else {
                    end = Math.max(intervals[i][END], end);
                }

            }
            return count;
        }
    }


    /**
     * https://leetcode.com/problems/increasing-subsequences/
     * Given an integer array, your task is to find all the different possible increasing subsequences of the given array,
     * and the length of an increasing subsequence should be at least 2.
     *
     * Example:
     * Input: [4, 6, 7, 7]
     * Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
     *
     * Note:
     * The length of the given array will not exceed 15.
     * The range of integer in the given array is [-100,100].
     * The given array may contain duplicates, and two equal integers should also be considered as a special case of
     * increasing sequence
     */
    static class IncreasingSubsequences {
        public List<List<Integer>> findSubsequences(int[] nums) {
            Set<List<Integer>> miniResult = new HashSet<>();
            recursiveSequences(nums, 0, new ArrayList<>(), miniResult);
            return new ArrayList<>(miniResult);
        }

        private void recursiveSequences(int[] nums, int index, List<Integer> active, Set<List<Integer>> result) {
            if (active.size() > 1) {
                result.add(new ArrayList<>(active));
            }

            for (int i=index; i<nums.length; i++) {
                if (!active.isEmpty() && nums[i] < active.get(active.size()-1)) {
                    continue;
                }
                active.add(nums[i]);
                recursiveSequences(nums, i+1, active, result);
                active.remove(active.size()-1);
            }
        }
    }


    /**
     * https://leetcode.com/problems/maximum-length-of-pair-chain/
     * You are given n pairs of numbers. In every pair, the first number is always smaller than the second number.
     *
     * Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can be formed in
     * this fashion.
     *
     * Given a set of pairs, find the length longest chain which can be formed. You needn't use up all the given pairs.
     * You can select pairs in any order.
     *
     * Example 1:
     * Input: [[1,2], [2,3], [3,4]]
     * Output: 2
     * Explanation: The longest chain is [1,2] -> [3,4]
     */
    static class MaximumLengthOfPairChain {

        private Comparator<int[]> minComp = (a,b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        };

        public int findLongestChain(int[][] pairs) {
            Arrays.sort(pairs, minComp);

            int end = pairs[0][1];
            int count = 1;
            for (int i=1; i<pairs.length; i++) {
                // do overlap
                if (end >= pairs[i][0]) {
                    end = Math.min(end, pairs[i][1]);
                } else {
                    count++;
                    end = pairs[i][1];
                }
            }
            return count;
        }
    }


    /**
     * https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/
     * Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting
     * some characters of the given string. If there are more than one possible results, return the longest word with
     * the smallest lexicographical order. If there is no possible result, return the empty string.
     *
     * Example 1:
     * Input:
     * s = "abpcplea", d = ["ale","apple","monkey","plea"]
     *
     * Output:
     * "apple"
     * Example 2:
     * Input:
     * s = "abpcplea", d = ["a","b","c"]
     *
     * Output:
     * "a"
     */
    static class LongestWordInDictionaryThroughDeleting {

        public String findLongestWord(String s, List<String> d) {
            int maxWordSize = 0;
            String result = "";
            for (String word: d) {
                if (isSubsequence(word, s)) {
                    if (word.length() > result.length()) {
                        result = word;
                    } else if (word.length() == result.length()) {
                        result = getLexSmallerString(word, result);
                    }
                }
            }
            return result;
        }

        private String getLexSmallerString(String a, String b) {
            for (int i=0; i<a.length(); i++) {
                if (a.charAt(i) == b.charAt(i)) {
                    continue;
                } else {
                    return a.charAt(i) < b.charAt(i) ? a : b;
                }
            }
            return a;
        }

        private boolean isSubsequence(String word, String str) {
            int j = 0;
            for (int i=0; i<str.length() && j <word.length(); i++) {
                if (word.charAt(j) == str.charAt(i)) {
                    j++;
                }
            }
            return j >= word.length();
        }
    }


    /**
sxxxxxxxxa     * Given a circular array (the next element of the last element is the first element of the array), print the
     * Next Greater Number for every element. The Next Greater Number of a number x is the first greater number to its
     * \traversing-order next in the array, which means you could search circularly to find its next greater number.
     * If it doesn't exist, output -1 for this number.
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: [2,-1,2]
     * Explanation: The first 1's next greater number is 2;
     * The number 2 can't find next greater number;
     * The second 1's next greater number needs to search circularly, which is also 2.
     */
    static class NextGreaterElementII {
        public int[] nextGreaterElements(int[] nums) {

            int[] result = new int[nums.length];
            Stack<Integer> stack = new Stack<>();

            // Go from right to left and find the maximum values
            for (int i=nums.length-1; i>-1; i--) {
                while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                    stack.pop();
                }
                result[i] = stack.isEmpty() ? Integer.MIN_VALUE : nums[stack.peek()];
                stack.push(i);
            }

            // We can reuse the stack from previous iteration.
            for (int i=nums.length-1; i>-1 ; i--) {
                if (result[i] != Integer.MIN_VALUE) {
                    continue;
                }
                while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                    stack.pop();
                }
                result[i] = stack.isEmpty() || i <= stack.peek() ? Integer.MIN_VALUE : nums[stack.peek()];
            }

            // Substitute all MIN_VALUE with -1.
            for (int i=0; i<result.length; i++) {
                if (result[i] == Integer.MIN_VALUE) {
                    result[i] = -1;
                }
            }
            return result;
        }

    }


    /**
     * https://leetcode.com/problems/maximum-sum-circular-subarray/
     * Given a circular array C of integers represented by A, find the maximum possible sum of a non-empty subarray of C.
     *
     * Here, a circular array means the end of the array connects to the beginning of the array.  (Formally, C[i] = A[i]
     * when 0 <= i < A.length, and C[i+A.length] = C[i] when i >= 0.)
     *
     * Also, a subarray may only include each element of the fixed buffer A at most once.  (Formally, for a subarray
     * C[i], C[i+1], ..., C[j], there does not exist i <= k1, k2 <= j with k1 % A.length = k2 % A.length.)
     *
     *
     *
     * Example 1:
     *
     * Input: [1,-2,3,-2]
     * Output: 3
     * Explanation: Subarray [3] has maximum sum 3
     * Example 2:
     *
     * Input: [5,-3,5]
     * Output: 10
     * Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10
     * Example 3:
     *
     * Input: [3,-1,2,-1]
     * Output: 4
     * Explanation: Subarray [2,-1,3] has maximum sum 2 + (-1) + 3 = 4
     * Example 4:
     *
     * Input: [3,-2,2,-3]
     * Output: 3
     * Explanation: Subarray [3] and [3,-2,2] both have maximum sum 3
     * Example 5:
     *
     * Input: [-2,-3,-1]
     * Output: -1
     * Explanation: Subarray [-1] has maximum sum -1
     */
    static class MaximumSumCircularSubArray {
        public int maxSubarraySumCircular(int[] arr) {
            int pSum = kadanesMaxSum(arr);
            int totalSum = 0;
            boolean isAllNonPositive = true;
            for (int i=0; i<arr.length; i++) {
                if (arr[i] > 0) {
                    isAllNonPositive = false;
                }
                totalSum += arr[i];
                arr[i] *= -1;
            }

            if (isAllNonPositive) {
                return pSum;
            }

            int nSum = kadanesMaxSum(arr);

            return pSum > totalSum + nSum ? pSum : totalSum + nSum;
        }

        private int kadanesMaxSum(int[] arr) {
            int sum = 0, maxSum = 0, maxVal = Integer.MIN_VALUE;
            for (int i=0; i<arr.length; i++) {
                if (sum + arr[i] < 0) {
                    sum = 0;
                } else {
                    sum += arr[i];
                }
                maxSum = Math.max(maxSum, sum);
                maxVal = Math.max(maxVal, arr[i]);
            }
            // If sum is 0 then either 0 is the max value in the array or array is completely -ve.
            // In either case pick the max value from the array
            return maxSum == 0 ? maxVal : maxSum;
        }
    }


    /**
     * https://leetcode.com/problems/fruit-into-baskets/
     * In a row of trees, the i-th tree produces fruit with type tree[i].
     *
     * You start at any tree of your choice, then repeatedly perform the following steps:
     *
     * Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
     * Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
     * Note that you do not have any choice after the initial choice of starting tree: you must perform step 1,
     * then step 2, then back to step 1, then step 2, and so on until you stop.
     *
     * You have two baskets, and each basket can carry any quantity of fruit, but you want each basket to only carry one
     * type of fruit each.
     *
     * What is the total amount of fruit you can collect with this procedure?
     *
     * Example 1:
     *
     * Input: [1,2,1]
     * Output: 3
     * Explanation: We can collect [1,2,1].
     * Example 2:
     *
     * Input: [0,1,2,2]
     * Output: 3
     * Explanation: We can collect [1,2,2].
     * If we started at the first tree, we would only collect [0, 1].
     * Example 3:
     *
     * Input: [1,2,3,2,2]
     * Output: 4
     * Explanation: We can collect [2,3,2,2].
     * If we started at the first tree, we would only collect [1, 2].
     * Example 4:
     *
     * Input: [3,3,3,1,2,1,1,2,3,3,4]
     * Output: 5
     * Explanation: We can collect [1,2,1,1,2].
     * If we started at the first tree or the eighth tree, we would only collect 4 fruits.
     */
    static class FruitIntoBaskets {
        public int totalFruit(int[] tree) {
            Map<Integer, Integer> countMap = new HashMap<>();
            int maxFruits = 0;
            for (int i=0, j=0; j < tree.length; j++) {
                countMap.put(tree[j], countMap.getOrDefault(tree[j], 0) + 1);
                while (countMap.size() > 2) {
                    int c = countMap.get(tree[i]);
                    if (c == 1) {
                        countMap.remove(tree[i]);
                    } else {
                        countMap.put(tree[i], c-1);
                    }
                    i++;
                }
                maxFruits = Math.max(maxFruits, j - i + 1);
            }
            return maxFruits;
        }
    }


    /**
     * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
     * There are a number of spherical balloons spread in two-dimensional space. For each balloon, provided input is the
     * start and end coordinates of the horizontal diameter. Since it's horizontal, y-coordinates don't matter and
     * hence the x-coordinates of start and end of the diameter suffice. Start is always smaller than end.
     * There will be at most 104 balloons.
     *
     * An arrow can be shot up exactly vertically from different points along the x-axis. A balloon with xstart and xend
     * bursts by an arrow shot at x if xstart ≤ x ≤ xend. There is no limit to the number of arrows that can be shot.
     * An arrow once shot keeps travelling up infinitely. The problem is to find the minimum number of arrows that
     * must be shot to burst all balloons.
     *
     * Example:
     *
     * Input:
     * [[10,16], [2,8], [1,6], [7,12]]
     *
     * Output:
     * 2
     *
     * Explanation:
     * One way is to shoot one arrow for example at x = 6 (bursting the balloons [2,8] and [1,6]) and another arrow at x = 11 (bursting the other two balloons).
     */
    static class MinimmumNumberOfArrowsToBurstBalloons {
        Comparator<int[]> comp = (a,b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        };
        public int findMinArrowShots(int[][] points) {
            if (points.length == 0) {
                return 0;
            }
            Arrays.sort(points, comp);
            int prevS = points[0][0];
            int prevE = points[0][1];
            int count = 1;

            for (int i=1; i<points.length; i++) {
                // If they overlap
                if (prevE >= points[i][0]) {
                    prevS = Math.max(prevS, points[i][0]);
                    prevE = Math.min(prevE, points[i][1]);
                } else {
                    prevS = points[i][0];
                    prevE = points[i][1];
                    count++;
                }
            }

            return count;
        }
    }

    /**
     * In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1.
     *
     * When a student enters the room, they must sit in the seat that maximizes the distance to the closest person.
     * If there are multiple such seats, they sit in the seat with the lowest number.  (Also, if no one is in the room,
     * then the student sits at seat number 0.)
     *
     * Return a class ExamRoom(int N) that exposes two functions: ExamRoom.seat() returning an int representing what
     * seat the student sat in, and ExamRoom.leave(int p) representing that the student in seat number p now leaves the room.
     * It is guaranteed that any calls to ExamRoom.leave(p) have a student sitting in seat p.
     *
     *
     *
     * Example 1:
     *
     * Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
     * Output: [null,0,9,4,2,null,5]
     * Explanation:
     * ExamRoom(10) -> null
     * seat() -> 0, no one is in the room, then the student sits at seat number 0.
     * seat() -> 9, the student sits at the last seat number 9.
     * seat() -> 4, the student sits at the last seat number 4.
     * seat() -> 2, the student sits at the last seat number 2.
     * leave(4) -> null
     * seat() -> 5, the student sits at the last seat number 5.
     */
    public static class ExamRoom {

        TreeSet<Integer> treeSet;
        int N;

        public ExamRoom(int N) {
            this.treeSet = new TreeSet<>();
            this.N = N;
        }

        public int seat() {
            if (treeSet.isEmpty()) {
                treeSet.add(0);
                return 0;
            }

            int prev = 0, maxDist = treeSet.first(), pos = 0;
            for (int s: treeSet) {
                int dist = (s - prev)/2;
                if (dist > maxDist) {
                    maxDist = dist;
                    pos = prev + (s - prev)/2;
                }
                prev = s;
            }

            if (N - 1 - treeSet.last() > maxDist) {
                pos = N - 1;
            }
            treeSet.add(pos);
            return pos;
        }

        public void leave(int p) {
            treeSet.remove(p);
        }
    }
}