package org.code.runs.judges;

import org.junit.Test;

public class LeetCodeProblemsTest {

    @Test
    public void burstBalloons() {
        LeetCodeProblems.BurstBalloons balloons = new LeetCodeProblems.BurstBalloons();
        int[] input = {3, 1, 5, 8};
        System.out.println("Max coin top down = " + balloons.maxCoins(input));
    }

    @Test
    public void ipoTest() {
        int k = 10;
        int w = 0;
        int[] profits = {1, 2, 3};
        int[] capitals = {0, 1, 2};
        LeetCodeProblems.IPO.PriorityQueueSolution solution = new LeetCodeProblems.IPO.PriorityQueueSolution();
        solution.findMaximizedCapital(k, w, profits, capitals);
    }
}