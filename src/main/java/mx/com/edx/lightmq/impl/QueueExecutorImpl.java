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
 * @version 1.0.2
 * @since 1.0.0
 */
public class QueueExecutorImpl implements QueueExecutor {

    private ExecutorService executor;
    private ConcurrentLinkedQueue<Callable> queue;
    private ConcurrentLinkedQueue<Future> queueFuture;
    private boolean flag;

    @Override
    public void setDispatchOnArrive(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean isDispatchOnArrive() {
        return this.flag;
    }
    
    @Override
    public void addToQueue(Callable message) {
        queue.add(message);
        if (isDispatchOnArrive()) {
            processAllMesages();
        }
    }

    @Override
    public void init(int threads) {
        queue = new ConcurrentLinkedQueue<>();
        queueFuture = new ConcurrentLinkedQueue<>();
        executor = Executors.newFixedThreadPool(threads);
        setDispatchOnArrive(true);
    }

    @Override
    public void processAllMesages() {
        while (!queue.isEmpty()) {
            queueFuture.add(executor.submit(queue.poll()));
        }
    }

    @Override
    public ConcurrentLinkedQueue<Future> getQueueProcesedMsg() {
        return queueFuture;
    }

}
