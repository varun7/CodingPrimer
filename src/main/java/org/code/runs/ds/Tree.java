package org.code.runs.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public interface Tree {

    class Node {
        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    class BinarySearchTree {

        public Node root = null;

        public boolean isEmpty() {
            return root == null;
        }

        public void insert(int data) {
            if (isEmpty()) {
                root = new Node(data);
                return;
            }

            Node currentNode = root;
            while (true) {
                if (currentNode.data >= data) {
                    // move to left of tree.
                    if (currentNode.left != null) {
                        currentNode = currentNode.left;
                    } else {
                        currentNode.left = new Node(data);
                        return;
                    }
                } else {
                    if (currentNode.right != null) {
                        currentNode = currentNode.right;
                    } else {
                        currentNode.right = new Node(data);
                        return;
                    }
                }
            }
        }

        public boolean search(Node node, int data) {
            if (node == null) {
                return false;
            }

            if (data == node.data) {
                return true;
            }
            if (data < node.data) {
                return search(node.left, data);
            } else {
                return search(node.right, data);
            }
        }

        public void delete(int data) {

        }

        public void inorderTraversal() {
            System.out.println("\n\n Inorder Traversal : ");
            _inorderTraversal(root);
        }

        private void _inorderTraversal(Node root) {
            if (root == null) {
                return;
            }

            _inorderTraversal(root.left);
            System.out.print(" " + root.data);
            _inorderTraversal(root.right);
        }

        public void preorderTraversal() {
            System.out.println("\n\n Preorder Traversal : ");
            _preorderTraversal(root);
        }

        private void _preorderTraversal(Node root) {
            if (root == null) {
                return;
            }

            System.out.print(" " + root.data);
            _preorderTraversal(root.left);
            _preorderTraversal(root.right);
        }

        public void postOrderTraversal() {
            System.out.println("\n\n Postorder Traversal : ");
            _postOrderTraversal(root);
        }

        public void _postOrderTraversal(Node root) {
            if (root == null) {
                return;
            }
            _postOrderTraversal(root.left);
            _postOrderTraversal(root.right);
            System.out.print(" " + root.data);
        }

        public void iterativePreorderTraversal(Node root) {
            Node node = root;
            Stack<Node> nodeStack = new Stack<>();
            while (node != null) {
                // process root
                System.out.print(" " + node.data);

                if (node.right != null) {
                    nodeStack.push(node.right);
                }

                if (node.left != null) {
                    node = node.left;
                } else {
                    node = null;
                    if (!nodeStack.isEmpty()) {
                        node = nodeStack.pop();
                    }
                }
            }
        }

        public void iterativeInorderTraversal(Node root) {
            Stack<Node> stack = new Stack<>();
            Node node = root;
            while(true) {
                if (node != null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    if (stack.isEmpty()) {
                        return;
                    }

                    node = stack.pop();
                    // Process node.
                    System.out.print(" " + node.data);
                    node = node.right;
                }
            }
        }

        public void iterativePostorderTraversal(Node root) {
            Stack<Node> stack = new Stack<>();
            Node node = root, prev = null;
            while (true) {
                if (node!=null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    if (stack.isEmpty()) {
                        return;
                    }
                    node = stack.peek();
                    if (node.right != null && node.right != prev) {
                        node = node.right;
                    } else  {
                        // If there is no right child or right child is already processed.
                        // then process this node.
                        stack.pop();
                        prev = node;
                        System.out.print(" " + node.data);
                        node = null;
                    }
                }
            }
        }

        int pIndex = 0;
        public void constructBSTFromInAndPreOrder(int[] inorder, int[] preorder, int n) {
            pIndex = 0;
            root = _constructBSTFromInAndPreOrder(inorder, preorder, n, 0, n-1);
        }

        private Node _constructBSTFromInAndPreOrder(int [] inorder, int [] preorder, int n, int iStart, int iEnd) {

            if (iStart > iEnd || pIndex >= n) {
                return null;
            }

            Node newNode = new Node(preorder[pIndex++]);
            int iIndex = searchArray(inorder, iStart, iEnd, newNode.data);
            newNode.left = _constructBSTFromInAndPreOrder(inorder, preorder, n, iStart, iIndex-1);
            newNode.right = _constructBSTFromInAndPreOrder(inorder, preorder, n, iIndex+1, iEnd);
            return newNode;
        }

        int preorderIndex = 0;
        public void constructBSTFromPreorder(int []preorder) {
            this.root = new Node(preorder[0]);
            preorderIndex = 1;
            _constructBSTFromPreorder(root, Integer.MIN_VALUE, Integer.MAX_VALUE, preorder);
        }

        private void _constructBSTFromPreorder(Node root, int lowerBound, int upperBound, int[] preorder) {
            if (preorderIndex >= preorder.length) {
                return;
            }

            if (preorder[preorderIndex] < lowerBound || preorder[preorderIndex] > upperBound) {
                // Not a right place to insert this node.
                return;
            }

            Node newNode = new Node(preorder[preorderIndex++]);
            if (newNode.data <= root.data) {
                root.left = newNode;
            } else {
                root.right = newNode;
            }

            _constructBSTFromPreorder(newNode, lowerBound, newNode.data, preorder);
            _constructBSTFromPreorder(newNode, newNode.data, upperBound, preorder);
        }

        private static int searchArray(int[] inorder, int start, int end, int element) {
            for (int i=start; i <= end; i++) {
                if (inorder[i] == element) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Print encoding for an array.
         * Rules: consider BST made from given array. Let say number x is present in the BST and to reach x
         * if you go right print 1, if left then 0. Now you are given an index i in the array A (so x= A[i])
         * and print the encoding without constructing BST to reach x and without space with leas time complexity.
         */
        public void bstEncoding(int[] array, int n , int element) {
            int lowerBound = Integer.MIN_VALUE;
            int upperBound = Integer.MAX_VALUE;
            String encoding = "";

            for (int i=0; i<n; i++) {
                if (element == array[i]) {
                    System.out.print("\n\nBST encoding for element " + element + " is " + encoding);
                    return;
                }

                if (array[i] > element && array[i] <= upperBound) {
                    upperBound = array[i];
                    encoding += "0";
                }

                if (array[i] < element && array[i] >= lowerBound) {
                    lowerBound = array[i];
                    encoding += "1";
                }
            }
        }

        int maxPathLength;
        public int lengthOfLongestLeafToLeafPath() {
            maxPathLength = 0;
            longestPath(root);
            System.out.println("\n\nMaximum leaf to leaf path length = "  + maxPathLength);
            return maxPathLength;
        }

        private int longestPath(Node root) {
            if (root == null) {
                return 0;
            }
            int leftHeight = longestPath(root.left);
            int rightHeight = longestPath(root.right);
            int candidateMax = leftHeight + rightHeight + 1;
            if (candidateMax > maxPathLength) {
                maxPathLength = candidateMax;
            }
            return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
        }

        public void rootToLeafPath(int sum) {
            System.out.println("\n\n Is rhere a path from root to leaf with sum " + sum + " " + rootToLeafPath(root,sum) );
        }

        public void lowestCommonAncestor(int data1, int data2) {
            Node node = _lowestCommonAncestor(root, data1, data2);
            if (node == null) {
                System.out.println("\n\n Lowest common ancestor: either one or both elements are missing in tree");
            }
            System.out.println("\n\n Lowest common ancestor of " + data1 + " and " + data2 + " is " + node.data);
        }

        private Node _lowestCommonAncestor(Node root, int data1, int data2) {
            if (root == null) {
                return null;
            }

            if (root.data == data1 || root.data == data2) {
                return root;
            }

            Node left = _lowestCommonAncestor(root.left, data1, data2);
            Node right = _lowestCommonAncestor(root.right, data1, data2);

            if (left != null && right != null) {
                return root;
            }

            return left != null? left : right;
        }

        public boolean isValidBST(Node root) {
            return isBst(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        private boolean isBst(Node root, long start, long end) {
            if (root == null) {
                return true;
            }

            if (root.data <= start || root.data >= end) {
                return false;
            }

            return isBst(root.left, start, root.data) && isBst(root.right, root.data, end);
        }

        private boolean rootToLeafPath(Node root, int sum) {
            if (root == null) {
                return false;
            }

            if (root.left == null && root.right == null && sum - root.data == 0) {
                return true;
            }

            if (rootToLeafPath(root.left, sum - root.data)) {
                return true;
            }

            if (rootToLeafPath(root.right, sum-root.data)) {
                return true;
            }
            return false;
        }

        public List<Integer> rightViewOfTree() {
            List<Integer> result = new ArrayList<>();
            if (root == null) {
                return result;
            }

            _rightViewOfTree(root, result, 0, new Integer(-1));
            return result;
        }

        private void _rightViewOfTree(Node root, List<Integer> levels, int level, Integer maxLevel) {
            if (root == null) {
                return;
            }

            if (maxLevel.intValue() < level) {
                levels.add(root.data);
                maxLevel = level;
            }

            _rightViewOfTree(root.right, levels, level+1, maxLevel);
            _rightViewOfTree(root.left, levels, level+1, maxLevel);
        }

        public Node kthSmallest(int k) {
            return _kthSmallest(root, k, new AtomicInteger(0));
        }

        private Node _kthSmallest(Node root, int k, AtomicInteger pk) {
            if (root == null) {
                return null;
            }

            Node node = _kthSmallest(root.left, k, pk);

            if (node != null) {
                return node;
            }
            pk.addAndGet(1);
            if (pk.get() == k) {
                return root;
            }

            node = _kthSmallest(root.right, k, pk);
            return node;
        }

        public int maximumSumPathInBinaryTree() {
            AtomicInteger globalMax = new AtomicInteger(Integer.MIN_VALUE);
            _maxPathSum(root, globalMax);
            return globalMax.get();
        }

        private int _maxPathSum(Node root, AtomicInteger globalMax) {
            if (root == null) {
                return 0;
            }

            int left = _maxPathSum(root.left, globalMax);
            int right = _maxPathSum(root.right, globalMax);
            int candidate = left + right + root.data;

            if (globalMax.get() < candidate) {
                globalMax.set(candidate);
            }

            int path = root.data + Math.max(left, right);
            return path > 0 ? path : 0;
        }

        private final String NULL_NODE = "null";
        private class SerializationContext {
            boolean isEmpty;
            int index;

            public SerializationContext() {
                isEmpty = true;
                index = 0;
            }
        }

        // Encodes a tree to a single string.
        public String serializeBinaryTree(Node root) {
            StringBuilder builder = new StringBuilder();
            serialize(root, new SerializationContext(), builder);
            return builder.toString();
        }

        private void serialize(Node root, SerializationContext context, StringBuilder builder) {
            append(builder, context, root);

            if (root == null) {
                return;
            }
            serialize(root.left, context, builder);
            serialize(root.right, context, builder);
        }

        private void append(StringBuilder builder, SerializationContext context, Node root) {
            if (context.isEmpty) {
                context.isEmpty = false;
            } else {
                builder.append(",");
            }

            if (root == null) {
                builder.append(NULL_NODE);
            } else {
                builder.append(root.data);
            }
        }

        // Decodes your encoded data to tree.
        public Node deserializeBinaryTree(String data) {
            if (data == null) {
                return null;
            }

            String[] nodes = data.split(",");
            if (nodes.length == 0) {
                return null;
            }
            return _construct(nodes, new SerializationContext());
        }

        private Node _construct(String[] nodes, SerializationContext context) {
            if (context.index >= nodes.length) {
                return null;
            }

            Node newNode = createNode(nodes[context.index++]);
            if (newNode == null) {
                return newNode;
            }
            newNode.left = _construct(nodes, context);
            newNode.right = _construct(nodes, context);
            return newNode;
        }

        private Node createNode(String n) {
            if (NULL_NODE.equals(n)) {
                return null;
            }
            return new Node(Integer.valueOf(n));
        }
    }
}
