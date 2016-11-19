package net.ethanpark.common.lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author EthanPark <br/>
 *         Date: 2016/11/19 <br/>
 * @version 1.0
 */
public class LockTest {
   private final Lock lockObj = new ReentrantLock();

   public void lock() {
      System.out
            .println(Thread.currentThread().getName() + " start to execute.");
      try {
         lockObj.lock();
         System.out.println(new Date(System.currentTimeMillis()));
         Thread.sleep(1000);
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         System.out.println(Thread.currentThread().getName() + " release");

         lockObj.unlock();
      }
   }

   public void tryLockWithTimeout() {
      System.out
            .println(Thread.currentThread().getName() + " start to execute.");
      boolean acquire = false;
      try {
         acquire = lockObj.tryLock(500, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
         e.printStackTrace();
         acquire = false;
      }
      if (!acquire)
         return;

      handleInternal();
   }

   public void tryLock(){
      System.out
            .println(Thread.currentThread().getName() + " start to execute.");
      boolean acquire = lockObj.tryLock();
      if (!acquire)
         return;
      handleInternal();
   }

   private void handleInternal(){
      try {
         System.out.println(new Date(System.currentTimeMillis()));
         Thread.sleep(1000);
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         System.out.println(Thread.currentThread().getName() + " release");

         lockObj.unlock();
      }
   }

   public static void main(String[] args) {
      LockTest lockTest = new LockTest();
      Thread t1 = new Thread(new TestRunnable(lockTest), "t1");
      Thread t2 = new Thread(new TestRunnable(lockTest), "t2");
      t1.start();
      t2.start();
   }

   public static class TestRunnable implements Runnable {
      private LockTest lockTest;

      public TestRunnable(LockTest lockTest) {
         this.lockTest = lockTest;
      }

      @Override
      public void run() {
         lockTest.lock();
         try {
            Thread.sleep(2000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         lockTest.tryLock();

         try {
            Thread.sleep(2000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         lockTest.tryLockWithTimeout();
      }
   }
}
