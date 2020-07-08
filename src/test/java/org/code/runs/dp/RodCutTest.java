package org.code.runs.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class RodCutTest {

    @Test
    public void maxValue() {
        int [] price = {3, 5, 8, 9, 10, 17, 17, 20};
        int length = 8;
        RodCut rodCut = new RodCut();
        System.out.println(rodCut.maxValue(price, length));
    }
}