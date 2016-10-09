package net.ethanpark.common.task;

import java.util.concurrent.*;

/**
 * Created by baiyp on 2016/9/30.
 */
public class Task<T> {
   private static ExecutorService threadPool;

   private Object lock = new Object();

   /**
    * Task Status, which is the status to track the life cycle of the task.
    */
   private TaskStatus status = TaskStatus.START;

   private Future<?> runnableFuture;

   private T result;

   private Throwable throwable;

   private long startTime;

   private Callable<T> currentCallable;

   /**
    * Get the async procedure's exception, if it exists.
    * 
    * @return
    */
   public Throwable getThrowable() {
      return throwable;
   }

   public Task() {
      if (threadPool == null) {
         synchronized (lock) {
            if (threadPool == null)
               threadPool = Executors.newWorkStealingPool();
         }
      }
   }

   public Task(Runnable runnable) {
      this();
      currentCallable = new Callable<T>() {
         @Override
         public T call() throws Exception {
            setStatus(TaskStatus.RUNNING);
            runnable.run();
            return null;
         }
      };
   }

   public Task(Callable<T> callable) {
      this();
      currentCallable = new Callable<T>() {
         @Override
         public T call() throws Exception {
            setStatus(TaskStatus.RUNNING);
            return callable.call();
         }
      };
   }

   /**
    * Start the task
    */
   public void start() {
      startTime = System.currentTimeMillis();
      setStatus(TaskStatus.SCHEDULED);
      runnableFuture = threadPool.submit(currentCallable);
   }

   /**
    * Get the actual result which computed from callable.
    * 
    * @return
    */
   public T getResult() {
      try {
         result = (T) runnableFuture.get();
      } catch (Exception e) {
         throwable = e;
      }

      if (runnableFuture.isDone())
         return result;

      return null;
   }

   public boolean awaitToFinish() {
      try {
         result = (T) runnableFuture.get();
         return true;
      } catch (Exception e) {
         throwable = e;
      }
      return false;
   }

   public boolean awaitToFinish(long milliseconds) {
      try {
         result = (T) runnableFuture.get(milliseconds, TimeUnit.MILLISECONDS);
         return true;
      } catch (Exception e) {
         throwable = e;
      }
      return false;
   }

   public long getRunningMilliseconds() {
      return System.currentTimeMillis() - startTime;
   }

   public TaskStatus getStatus() {
      synchronized (lock) {
         return status;
      }
   }

   private void setStatus(TaskStatus status) {
      synchronized (lock) {
         this.status = status;
      }
   }
}
