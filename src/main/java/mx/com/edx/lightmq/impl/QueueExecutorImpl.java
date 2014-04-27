package mx.com.edx.lightmq.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import mx.com.edx.lightmq.QueueExecutor;

/**
 *
 * @author edx
 * @version 1.0.1
 * @since 1.0.0
 */
public class QueueExecutorImpl implements QueueExecutor {

    ExecutorService executor;
    ConcurrentLinkedQueue<Callable> queue;
    ConcurrentLinkedQueue<Future> queueFuture;

    @Override
    public void addToQueue(Callable message) {
        queue.add(message);
    }

    @Override
    public void init(int threads) {
        queue = new ConcurrentLinkedQueue<>();
        executor = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void processAllMesages() {
        while (!queue.isEmpty()) {
            queueFuture.add(executor.submit(queue.poll()));
        }
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public ConcurrentLinkedQueue<Callable> getQueue() {
        return queue;
    }

    public ConcurrentLinkedQueue<Future> getQueueFuture() {
        return queueFuture;
    }

}
