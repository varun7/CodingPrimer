package org.code.runs.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentProducerConsumer {

    public static class UniDataBroker implements Broker<Integer> {

        private Integer local;

        @Override
        public synchronized void put(Integer data) {
            while (local != null) {
                // Producer should wait until the existing data is consumed.
                try {
                    wait(); // This will release the lock
                } catch (InterruptedException e) {
                }
            }

            // Add new data and notify
            local = data;
            notifyAll();
        }

        @Override
        public synchronized Integer take() {
            while (local == null) {
                try {
                    wait();
                } catch (InterruptedException e) { }
            }

            int tempCopy = local;
            local = null;
            notifyAll();
            return tempCopy;
        }
    }

    public static class QueueBroker implements Broker<Integer> {

        private List<Integer> queue;
        private int capacity;

        public QueueBroker(int size) {
            this.queue = new ArrayList<>(size);
            this.capacity = size;
        }

        @Override
        public synchronized void put(Integer data) {
            while (queue.size() == capacity) {
                try {
                    System.out.println("[QueueBroker] Queue is full, blocking producer.");
                    wait();
                } catch (InterruptedException e) { }
            }
            notifyAll();
            queue.add(data);
        }

        @Override
        public synchronized Integer take() {
            while (queue.isEmpty()) {
                try {
                    System.out.println("[QueueBroker] Queue is empty, blocking consumer.");
                    wait();
                } catch (InterruptedException e) { }
            }
            notifyAll();
            return queue.remove(0);
        }
    }

    public static class LockBasedBroker implements Broker<Integer> {
        private Integer local;
        private Lock lock = new ReentrantLock();

        private Condition notEmpty = lock.newCondition();
        private Condition notFull = lock.newCondition();

        @Override
        public void put(Integer data) {
            lock.lock();
            while (local != null) {
                try {
                    System.out.println("[LockBroker] Not enough space, blocking producer till we don't recieve singal for taken.");
                    notFull.await();
                } catch (InterruptedException e) {
                }
            }
            this.local = data;
            notEmpty.signalAll();
            lock.unlock();
        }

        @Override
        public Integer take() {
            lock.lock();
            while(local == null) {
                try {
                    System.out.println("[LockBroker] Nothing to take, blocking consumer till producer doesn't signal added.");
                    notEmpty.await();
                } catch (InterruptedException e) {
                }
            }
            int temp = local;
            local = null;
            notFull.signalAll();
            lock.unlock();
            return temp;
        }
    }

}
