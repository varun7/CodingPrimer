package org.code.runs.ds;


public interface SkipList<E extends Comparable<? super E>> {

    boolean contains(E item);

    void add(E item);

    void delete(E item);

    void printSkipList();

    class RandomizedSkipList<E extends Comparable<? super E>> implements SkipList<E> {

        private class SkipNode<E extends Comparable<? super E>> {
            E value;
            SkipNode<E>[] next;

            public SkipNode(E value, int size) {
                this.value = value;
                next = new SkipNode[size+1];
            }
        }

        private SkipNode<E> head;
        private SkipNode<E> sentinel;
        private int maxLevel;

        public RandomizedSkipList(int size) {
            this.maxLevel = (int) (Math.log(size)/Math.log(2));
            this.head = new SkipNode<>(null, maxLevel + 1);
            this.sentinel = new SkipNode<>(null, maxLevel + 1);

            for (int i=0; i <= maxLevel; i++) {
                head.next[i] = sentinel;
                sentinel.next[i] = null;
            }
        }

        @Override
        public boolean contains(E item) {
            SkipNode<E> node = this.head;
            for (int i=maxLevel; i>=0; i--) {
                while (node.next[i] != sentinel && node.next[i].value.compareTo(item) <= 0) {
                    if (node.next[i].value.compareTo(item) == 0) {
                        return true;
                    }
                    node = node.next[i];
                }
            }
            return false;
        }

        @Override
        public void add(E item) {
            int newNodeLevel = getRandomLevel();
            SkipNode<E> node = this.head;
            SkipNode<E> newNode = new SkipNode<>(item, newNodeLevel);
            for (int i=maxLevel; i >= 0; i--) {
                while (node.next[i] != sentinel && node.next[i].value.compareTo(item) < 0) {
                    node = node.next[i];
                }
                if (i <= newNodeLevel) {
                    SkipNode<E> temp = node.next[i];
                    node.next[i] = newNode;
                    newNode.next[i] = temp;
                }
            }
        }

        @Override
        public void printSkipList() {
            System.out.println("\n\nMaximum level in skipList = " + maxLevel);
            for (int i=maxLevel-1; i >=0; i--) {
                System.out.print("Level " + i + ": ");
                for (SkipNode<E> node = this.head; node != sentinel; node = node.next[i]) {
                    System.out.print( node.value + " , ");
                }
                System.out.println();
            }
        }

        @Override
        public void delete(E item) {
            if (!contains(item)) {
                System.out.println("Cannot delete as element is not present in the list.");
            }

            SkipNode<E> node = this.head;
            for (int i=maxLevel; i>=0; i--) {
                while (node.next[i] != sentinel && node.next[i].value.compareTo(item) <= 0) {
                    if (node.next[i].value.compareTo(item) == 0) {
                        node.next[i] = node.next[i].next[i];
                    }
                    node = node.next[i];
                }
            }
            System.out.println("Deleted node " + item);
        }

        private int getRandomLevel() {
            int level = 0;
            while (level < maxLevel && Math.random() > 0.5) {
                level++;
            }
            return level;
        }
    }
}
