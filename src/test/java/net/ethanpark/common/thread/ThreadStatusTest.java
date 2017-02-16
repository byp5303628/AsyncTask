package net.ethanpark.common.thread;


/**
 * @author EthanPark <br/>
 * @version 1.0
 */
public class ThreadStatusTest {
   public static void main(String[] args) throws InterruptedException {
      Object lock = new Object();

      Thread t1 = new Thread(new Runnable() {
         @Override
         public void run() {
            System.out.println("Thread t1 executed.");
         }
      });

      System.out.println(t1.getState());

      t1.start();
      System.out.println(t1.getState());

      Thread.sleep(1000);

      System.out.println(t1.getState());

      Thread t2 = new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(300);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            System.out.println("Thread t2 executed.");
            synchronized (lock) {
               System.out.println(lock);
            }
         }
      });

      Thread t3 = new Thread(new Runnable() {
         @Override
         public void run() {
            synchronized (lock){
               try {
                  System.out.println("Thread t2: " + t2.getState());
                  Thread.sleep(1000);
                  System.out.println("Thread t3 executed.");
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
      });

      t3.start();
      t2.start();
   }
}
