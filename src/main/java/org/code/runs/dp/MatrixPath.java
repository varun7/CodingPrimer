package org.code.runs.dp;

public class MatrixPath {

    /**
     * Given a cost matrix Cost[][] where Cost[i][j] denotes the Cost of visiting cell with coordinates (i,j),
     * find a min-cost path to reach a cell (x,y) from cell (0,0) under the condition that you can only travel
     * one step right or one step down. (We assume that all costs are positive integers)
     */
    public static int cheapestPath(int endx, int endy, int[][]cost) {
        int rows = cost.length, columns = cost[0].length;
        int dp[][] = new int[rows][columns];
        System.out.println("Rows = " + rows + "columns = " + columns);

        // Initialization
        dp [0][0] = cost[0][0];
        for (int i=1; i < rows; i++) {
            dp[i][0] = dp[i-1][0] + cost[i][0];
        }

        for (int j=1; j<columns; j++) {
            dp[0][j] = dp[0][j-1] + cost[0][j];
        }

        // Computation
        for (int i=1; i<rows; i++) {
            for (int j=1; j<columns; j++) {
                dp[i][j] = Integer.min(dp[i][j-1], dp[i-1][j]) + cost[i][j];
            }
        }
        return dp[endx][endy];
    }

    /**
     * Given a 2-D matrix with M rows and N columns, find the number of ways to reach
     * cell with coordinates (i,j) from starting cell (0,0) under the condition that you can
     * only travel one step right or one step down.
     */
    public static int numberOfPaths(int endx, int endy) {
        int [][] ways = new int[endx+1][endy+1];

        // Compute
        for (int i=0; i<endx+1; i++) {
            for (int j=0; j<endy+1; j++) {
                // Only one way to reach any row in column-0
                // Only one way to reach any column in row-0
                if (i==0 || j ==0) {
                    ways[i][j] = 1;
                    continue;
                }

                ways[i][j] = ways[i-1][j] + ways[i][j-1];
            }
        }
        return ways[endx][endy];
    }

    /**
     * A robot is designed to move on a rectangular grid of M rows and N columns.
     * The robot is initially positioned at (1, 1), i.e., the top-left cell.
     * The robot has to reach the (M, N) grid cell. In a single step, robot can move only to the cells
     * to its immediate east and south directions. That means if the robot is currently at (i, j),
     * it can move to either (i + 1, j) or (i, j + 1) cell, provided the robot does not leave the grid.
     * Now somebody has placed several obstacles in random positions on the grid,
     * through which the robot cannot pass.
     * Given the positions of the blocked cells, your task is to count the number of
     * paths that the robot can take to move from (1, 1) to (M, N).
     */
    public static int numberOfPathsWithObstacles(int endx, int endy, int matrix[][]) {
        // Initializations.
        int [][] ways = new int[endx+1][endy+1];
        ways[0][0] = 1;
        for (int i=1; i<=endx; i++) {
            if (matrix[i][0] == 0) {
                ways[i][0] = 0;
                continue;
            }
            ways[i][0] = ways[i-1][0];
        }

        for (int j=1; j<=endy; j++) {
            if (matrix[0][j] == 0) {
                ways[0][j] = 0;
                continue;
            }
            ways[0][j] = ways[0][j-1];
        }

        // Compute
        for (int i=1; i<endx+1; i++) {
            for (int j=1; j<endy+1; j++) {
                if (matrix[i][j] == 0) {
                    ways[i][j] = 0;
                    continue;
                }
                ways[i][j] = ways[i-1][j] + ways[i][j-1];
            }
        }

        return ways[endx][endy];
    }
}
