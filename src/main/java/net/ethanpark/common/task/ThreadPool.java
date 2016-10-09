package net.ethanpark.common.task;

/**
 * Created by baiyp on 2016/10/8.
 */
public interface ThreadPool {

   /**
    * Return the number of threads in ThreadPool
    * 
    * @return
    */
   int size();

   void shutdown();

   /**
    * Submit a Runnable task to execute.
    * 
    * @param task
    */
   void submit(Runnable task);
}
