package org.code.runs.ds;

import java.util.ArrayList;
import java.util.List;

public class IntervalSearchTree{

    public static class IntervalNode {
        public int low;
        public int high;
        public int max;
        public int value;
        public IntervalNode left;
        public IntervalNode right;

        public IntervalNode(int low, int high, int max, int value) {
            this.low = low;
            this.high = high;
            this.max = max;
            this.value = value;
        }
    }

    IntervalNode root = null;

    public IntervalSearchTree() {
    }

    public void put(int lo, int hi, int value) {
        IntervalNode newNode = new IntervalNode(lo, hi, hi, value);
        if (root == null) {
            root = newNode;
            return;
        }
        _put(root, newNode);
    }

    private IntervalNode _put(IntervalNode root, IntervalNode newNode) {
        if (root == null) {
            return newNode;
        }

        if (newNode.low < root.low) {
            root.left = _put(root.left, newNode);
        } else {
            // Move left if newNode.left < root.low
            root.right = _put(root.right, newNode);
        }
        root.max = Math.max(root.max, newNode.max);
        return root;
    }

    public List<IntervalNode> overlappingIntervals(int lo, int hi) {
        List<IntervalNode> intervals = new ArrayList<>();
        _overlappingIntervals(this.root, lo, hi, intervals);
        return intervals;
    }

    private void _overlappingIntervals(IntervalNode root, int lo, int hi, List<IntervalNode> intervals) {
        if (root == null) {
            return;
        }

        // No overlap.
        if (lo > root.max) {
            return;
        }

        if (doOverlap(lo, hi, root)) {
            intervals.add(root);
        }

        _overlappingIntervals(root.left, lo, hi, intervals);
        _overlappingIntervals(root.right, lo, hi, intervals);
    }

    private boolean doOverlap(int lo, int hi, IntervalNode root) {
        if (lo <= root.max && hi >= root.low) {
            return true;
        }
        return false;
    }

}
