package net.ethanpark.common.task;

/**
 * Author: EthanPark <br/>
 * Date: 2016/11/27<br/>
 */
public class SyncTest {
   public static void main(String[] args) {
      Sync sync = new Sync();

      Thread t1 = new Thread(new Runnable() {
         @Override
         public void run() {
            sync.syncMethod();
         }
      });

      Thread t2 = new Thread(new Runnable() {
         @Override
         public void run() {
            sync.syncThis();
         }
      });

      Thread t3 = new Thread(new Runnable() {
         @Override
         public void run() {
            sync.notSyncMethod();
         }
      });

      Thread t4 = new Thread(new Runnable() {
         @Override
         public void run() {
            Sync.staticSyncMethod();
         }
      });

      Thread t5 = new Thread(new Runnable() {
         @Override
         public void run() {
            Sync.staticTypeMethod();
         }
      });

      t1.start();
      t2.start();
      t3.start();
      t4.start();
      t5.start();
   }
}

class Sync {
   public synchronized void syncMethod() {
      System.out.println("Sync Method Start To Executed");
      try {
         Thread.sleep(5000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      System.out.println("Sync Method Executed");
   }

   public void syncThis() {
      synchronized (this) {
         System.out.println("Sync This Executed");
      }
   }

   public void notSyncMethod(){
      System.out.println("Not Sync Method Executed");
   }

   public synchronized static void staticSyncMethod(){
      System.out.println("Static Sync Method Start To Executed");
      try {
         Thread.sleep(2000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      System.out.println("Static Sync Method Executed");
   }

   public static void staticTypeMethod(){
      synchronized (Sync.class){
         System.out.println("Static Sync Type Executed");
      }
   }
}
