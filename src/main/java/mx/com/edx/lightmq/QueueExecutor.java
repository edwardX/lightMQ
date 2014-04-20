package mx.com.edx.lightmq;

import java.util.concurrent.Callable;

/**
 *
 * @author edx
 * @version 1.0.0
 * @since 1.0.0
 */
public interface QueueExecutor {

    void init(int threads);

    void addToQueue(Callable message);

    void processAllMesages();

}
