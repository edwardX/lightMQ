package mx.com.edx.lightmq.junit;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import mx.com.edx.lightmq.QueueExecutor;
import mx.com.edx.lightmq.impl.QueueExecutorImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edx
 */
public class LightMQJUnitTest {
    
    QueueExecutor lightmq= null;
    
    public LightMQJUnitTest() {
    }
    
    
    @Before
    public void setUp() {
        lightmq = new QueueExecutorImpl();
        lightmq.init(10);
        for (int i = 0; i < 10000;i++) {
            lightmq.addToQueue(new WordToUpperCaseCallable("Message No. "+i));
        }
           
    }
    
    @Test
    public void testLightMQ() {
       // lightmq.processAllMesages();
    }
    
    @After
    public void tearDown() throws InterruptedException, ExecutionException {
        ConcurrentLinkedQueue<Future> queueFuture = lightmq.getQueueProcesedMsg();
        while (!queueFuture.isEmpty()) {
            Future poll = queueFuture.poll();
            String getMsg = (String) poll.get();
            System.out.println("get poll Msg: " + getMsg);
        }
    }
    
    static class WordToUpperCaseCallable implements Callable {
    private String word;
    public WordToUpperCaseCallable(String word) {
      this.word = word;
    }
    
    @Override
    public String call() {
      return word.toUpperCase();
    }
  }
}
