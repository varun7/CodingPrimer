package org.code.runs.ds;

import org.code.runs.algo.Utils;

public interface Heap {

    boolean isFull();

    boolean isEmpty();

    void delete();

    int peek();

    void insert(int element);

    void print();

    class ArrayHeap implements Heap  {

        private final int [] array;
        private int size = 0;
        private final int capacity;

        public ArrayHeap(int size) {
            array = new int[size];
            capacity = size;
        }

        @Override
        public boolean isFull() {
            return size == capacity;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public void delete() {
            if (isEmpty()) {
                System.out.println("Empty heap, nothing to delete. Terminating delete operation");
                return;
            }

            array[0] = array[size-1];
            size--;
            heapify(0);
        }

        @Override
        public int peek() {
            if (isEmpty()) {
                System.out.println("Empty heap returning -100");
                return -100;
            }
            return array[0];
        }

        @Override
        public void insert(int element) {
            if (isFull()) {
                System.out.println("Cannot insert heap is full. Terminating insert.");
                return;
            }

            array[size++] = element;
            for (int i = size -1; i > 0 && array[(i-1)/2] > array[i]; i = (i-1)/2) {
                Utils.swap(array, i, (i-1)/2);
            }
        }

        @Override
        public void print() {
            System.out.println("\n Printing heap : ");
            for (int i=0; i<size; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println();
        }

        private void heapify(int index) {
            int lChildIndex = 2 * index + 1;
            int rChildIndex = 2 * index + 2;
            int smallerIndex = index;

            if (lChildIndex < size && array[lChildIndex] < array[smallerIndex]) {
                smallerIndex = lChildIndex;
            }

            if (rChildIndex < size && array[rChildIndex] < array[smallerIndex]) {
                smallerIndex = rChildIndex;
            }

            if (smallerIndex != index) {
                Utils.swap(array, index, smallerIndex);
                heapify(smallerIndex);
            }
        }
    }
}
