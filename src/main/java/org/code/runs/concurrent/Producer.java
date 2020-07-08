package org.code.runs.concurrent;

import java.util.Random;

public interface Producer<V> {

    V produce();

    class ThreadProducer implements Producer<Integer>, Runnable {

        private Broker<Integer> broker;

        public ThreadProducer(Broker<Integer> broker) {
            this.broker = broker;
        }

        @Override
        public Integer produce() {
            Random random = new Random(System.currentTimeMillis());
            int rand = random.nextInt();
            System.out.println("[Producer] Generated number = " + rand);
            return rand;
        }

        @Override
        public void run() {
            for (int i=0; i<10; i++) {
                broker.put(produce());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("[Producer] Recieved interrupt, breaking.");
                    break;
                }
            }
            System.out.println("[Producer] Done producing, existing after this one.");
            broker.put(-1);
        }
    }

}
