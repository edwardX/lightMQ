package mx.com.edx.lightmq.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import mx.com.edx.lightmq.QueueExecutor;

/**
 *
 * @author edx
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueueExecutorImpl implements QueueExecutor {

    ThreadPoolExecutor executor;
    ConcurrentLinkedQueue<Callable> queue;
    ConcurrentLinkedQueue<Future> queueFuture;

    @Override
    public void addToQueue(Callable message) {
        queue.add(message);
    }

    @Override
    public void init(int threads) {
        queue = new ConcurrentLinkedQueue<>();
        executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.DAYS, null);
        executor.setMaximumPoolSize(threads);
    }

    @Override
    public void processAllMesages() {
        while (!queue.isEmpty()) {
            queueFuture.add(executor.submit(queue.poll()));
        }
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public ConcurrentLinkedQueue<Callable> getQueue() {
        return queue;
    }

    public ConcurrentLinkedQueue<Future> getQueueFuture() {
        return queueFuture;
    }

}
