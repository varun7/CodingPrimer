package org.code.runs.concurrent;

public interface Broker<V> {

    void put(V data);

    V take();

}
