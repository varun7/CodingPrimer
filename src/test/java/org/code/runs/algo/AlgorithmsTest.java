package org.code.runs.algo;

import org.junit.Test;

import java.util.Random;

public class AlgorithmsTest {

    @Test
    public void testArrayShuffling() {
        int[] input = {2,  8,  6,  7,  9,  4,  5,  3,  1};
        Algorithms.shuffleArray(input);
        print(input);
    }

    public void print(int[] arr) {
        System.out.println();
        for (int n: arr) {
            System.out.print("  " + n);
        }
    }
}
