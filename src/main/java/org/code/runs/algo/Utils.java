package org.code.runs.algo;

import java.util.Random;

public final class Utils {

    public static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static void printArray(int [] array) {
        for (int i: array) {
            System.out.print(i + " ");
        }
    }


    public static void printMatrix(int [][] matrix) {
        for (int i=0; i < matrix.length; i++) {
            System.out.println();
            for (int j=0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
        }
    }

    public static void printMatrix(long [][] matrix) {
        for (int i=0; i < matrix.length; i++) {
            System.out.println();
            for (int j=0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
        }
    }

    public static String reverseString(String str) {
        StringBuilder build = new StringBuilder();
        for (int i=str.length()-1; i>=0; i--) {
            build.append(str.charAt(i));
        }
        return build.toString();
    }

    private static <T> void _printMatrix(T[][] matrix) {
        for (int i=0; i < matrix.length; i++) {
            System.out.println();
            for (int j=0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
        }
    }

    public static int[] createRandomIntArray(int size) {
        Random random = new Random(System.currentTimeMillis());
        int [] array = new int[size];
        for (int i=0; i < size; i++) {
            array[i] = random.nextInt() % 103;
        }
        return array;
    }

    public static int minimum(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        }
        if (b <=c) {
            return b;
        }
        return c;
    }
}
