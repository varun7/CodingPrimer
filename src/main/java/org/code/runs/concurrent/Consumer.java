package org.code.runs.concurrent;

import java.util.Random;

public interface Consumer<V> {

    void consume(V data);

    public static class ThreadConsumer implements Consumer<Integer>, Runnable {

        private Broker<Integer> broker;

        public ThreadConsumer(Broker<Integer> broker) {
            this.broker = broker;
        }

        @Override
        public void consume(Integer data) {
            System.out.println("[Consumer] Consumed int = " + data);
        }

        @Override
        public void run() {
            Random random = new Random();
            for (int i=broker.take(); i != -1; i = broker.take()) {
                consume(i);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    System.out.println("[Consumer] Received interrupt, breaking.");
                    break;
                }
            }
        }
    }
}
