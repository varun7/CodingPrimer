package org.code.runs.ds;

import org.junit.Test;

import java.util.List;

public class IntervalSearchTreeTest {

    @Test
    public void testOverlappingIntervals() {
        IntervalSearchTree tree = new IntervalSearchTree();
        tree.put(2,10, 4);
        tree.put(3,5, 5);
        tree.put(0,2, 4);
        tree.put(7,12, 4);

        int p = 0, q=15;
        List<IntervalSearchTree.IntervalNode> nodes = tree.overlappingIntervals(p,q);

        System.out.println("\n\nPrinting intervals overlapping with (" + p + " , " + q + ") is/are: ");
        printIntervals(nodes);
    }

    private static void printIntervals(List<IntervalSearchTree.IntervalNode> nodes) {
        nodes.forEach(n -> {
            System.out.print(" (" + n.low + " , " + n.high + ")  ");
        });
    }
}
