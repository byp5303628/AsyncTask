package net.ethanpark.common.task;

/**
 * Created by baiyp on 2016/10/8.
 */
public class ThreadPoolImpl implements ThreadPool {
   private Thread[] threads;

   private static final int DEFAULT_POOL_SIZE = 4;

   public ThreadPoolImpl() {
      threads = new Thread[DEFAULT_POOL_SIZE];
   }

   public ThreadPoolImpl(int size) {
      if (size <= 0)
         throw new IllegalArgumentException(
               "Thread Pool Size Cannot Be Less Than 1");

      threads = new Thread[size];
   }

   @Override
   public int size() {
      return threads.length;
   }

   @Override
   public void shutdown() {

   }

   @Override
   public void submit(Runnable task) {

   }
}
