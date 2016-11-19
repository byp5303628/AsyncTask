package net.ethanpark.common.lock;

import java.sql.Timestamp;
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
         System.out.println(new Timestamp(System.currentTimeMillis()));
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
         System.out.println(new Timestamp(System.currentTimeMillis()));
         Thread.sleep(1000);
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         System.out.println(Thread.currentThread().getName() + " release");

         lockObj.unlock();
      }
   }

   public static void main(String[] args) throws InterruptedException {
      LockTest lockTest = new LockTest();
      Thread t1 = new Thread(new Runnable() {
         @Override
         public void run() {
            lockTest.lock();
         }
      }, "t1");
      Thread t2 = new Thread(new Runnable() {
         @Override
         public void run() {
            lockTest.lock();
         }
      }, "t2");
      t1.start();
      t2.start();

      Thread.sleep(3000);
      System.out.println("------------");

      t1 = new Thread(new Runnable() {
         @Override
         public void run() {
            lockTest.tryLock();
         }
      }, "t1");

      t2 = new Thread(new Runnable() {
         @Override
         public void run() {
            lockTest.tryLock();
         }
      }, "t2");

      t1.start();
      t2.start();

      Thread.sleep(3000);
      System.out.println("------------");

      t1 = new Thread(new Runnable() {
         @Override
         public void run() {
            lockTest.tryLockWithTimeout();
         }
      }, "t1");

      t2 = new Thread(new Runnable() {
         @Override
         public void run() {
            lockTest.tryLockWithTimeout();
         }
      }, "t2");

      t1.start();
      t2.start();
   }
}
