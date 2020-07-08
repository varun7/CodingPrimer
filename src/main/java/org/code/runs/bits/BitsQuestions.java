package org.code.runs.bits;

public class BitsQuestions {

    public static int add(int a, int b) {
        int aAndB = (a & b) << 1;
        int aXorB = a ^ b;
        int temp;

        while (aAndB != 0 ) {
            temp = aXorB ^ aAndB;
            aAndB = aXorB & aAndB;
            aXorB = temp;
            aAndB <<= 1;
        }
        return aXorB;
    }

    public static int max(int a, int b) {
        int diff = a - b;
        int msb = diff >> 31 & 1;
        return a - msb * diff;
    }
}
