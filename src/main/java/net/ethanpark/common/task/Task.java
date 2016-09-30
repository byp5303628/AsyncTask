package net.ethanpark.common.task;

import java.util.concurrent.*;

/**
 * Created by baiyp on 2016/9/30.
 */
public class Task<T> {
   private static ExecutorService threadPool;

   private Future<?> runnableFuture;

   private T result;

   private Throwable throwable;

   /**
    * Get the async procedure's exception, if it exists.
    * 
    * @return
    */
   public Throwable getThrowable() {
      return throwable;
   }

   public void setThrowable(Throwable throwable) {
      this.throwable = throwable;
   }

   public Task() {
      threadPool = Executors.newWorkStealingPool();
   }

   public Task(Runnable runnable) {
      this();
      runnableFuture = threadPool.submit(() -> {
         try {
            runnable.run();
         } catch (Exception ex) {
            throwable = ex;
         }
      });
   }

   public Task(Callable<T> callable) {
      this();
      runnableFuture = threadPool.submit(() -> {
         try {
            return callable.call();
         } catch (Exception ex) {
            throwable = ex;
            return null;
         }
      });
   }

   /**
    * Start the task
    */
   public void start() {
      try {
         result = (T) runnableFuture.get();
      } catch (InterruptedException e) {
         e.printStackTrace();
      } catch (ExecutionException e) {
         e.printStackTrace();
      }
   }

   /**
    * Get the actual result which computed from callable.
    * 
    * @return
    */
   public T getResult() {
      if (runnableFuture.isDone())
         return result;

      return null;
   }
}
