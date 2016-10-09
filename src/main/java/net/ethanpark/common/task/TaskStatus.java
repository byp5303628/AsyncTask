package net.ethanpark.common.task;

/**
 * Created by baiyp on 2016/10/9.
 */
public enum TaskStatus {
   /**
    * Task is created by yet started.
    */
   START,

   /**
    * Task is submitted to executorService.
    */
   SCHEDULED,

   /**
    * Task is running.
    */
   RUNNING,

   /**
    * Task is finished.
    */
   FINISHED

}
