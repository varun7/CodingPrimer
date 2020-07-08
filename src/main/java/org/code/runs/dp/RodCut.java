package org.code.runs.dp;

public class RodCut {

    public int maxValue(int price[], int length) {
        int [] dp = new int[length+1];
        dp [0] = 0;

        int max;
        for (int i=1; i <= length; i++) {
            max = Integer.MIN_VALUE;
            for (int j = i-1; j >=0; j--) {
                max = Math.max(max, dp [j] + dp [i-j]);
            }
            dp [i] = Math.max(max, price[i-1]);
        }
        return dp [length];
    }
}
