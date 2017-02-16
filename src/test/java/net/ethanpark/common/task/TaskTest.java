package net.ethanpark.common.task;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Task Tester.
 * 
 * @author EthanPark
 * 
 * @version 1.0
 */
public class TaskTest {

   @Before
   public void before() throws Exception {
   }

   @After
   public void after() throws Exception {
   }


   @Test
   public void testConstructor1() {
      long now = System.currentTimeMillis();
      Task task = new Task(new Runnable() {
         @Override
         public void run() {
            try {
               TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
               throw new RuntimeException(e);
            }
            System.out.println("Async Task after 1 second");
         }
      });

      task.start();
      task.getResult();
      long after = System.currentTimeMillis();

      Assert.assertTrue(after - now > 1000L);
   }

   @Test
   public void testConstructor2() throws IOException {
      Task task = new Task(new Runnable() {
         @Override
         public void run() {
            try {
               TimeUnit.SECONDS.sleep(1);
               throw new RuntimeException("Test Exception");
            } catch (InterruptedException e) {
               throw new RuntimeException(e);
            }
         }
      });

      task.start();
      task.getResult();

      Assert.assertNotNull(task.getThrowable());

      ExecutorService es = Executors.newFixedThreadPool(100);

      ServerSocket serverSocket = new ServerSocket(83);

      es.submit(new Runnable() {
         @Override
         public void run() {
            try {
               Socket socket = serverSocket.accept();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      });
   }

   @Test
   public void testGetResult1() {
      Task<Integer> task = new Task<>(new Callable<Integer>() {
         @Override
         public Integer call() throws Exception {
            TimeUnit.MILLISECONDS.sleep(1000);
            return 1;
         }
      });
      task.start();

      Assert.assertTrue(task.getResult() == 1);

      System.out.println(task.getRunningMilliseconds());
      Assert.assertTrue(task.getRunningMilliseconds() >= 1000L);
   }

   @Test
   public void testGetResult2() {
      Task<Integer> task = new Task<>(new Callable<Integer>() {
         @Override
         public Integer call() throws Exception {
            TimeUnit.MILLISECONDS.sleep(1000);
            return 1;
         }
      });
      task.start();

      Assert.assertTrue(task.getResult() == 1);

      Assert.assertTrue(task.getRunningMilliseconds() >= 1000L);
   }

   @Test
   public void testAwaitToFinish1() {
      Task<Integer> task = new Task<>(new Callable<Integer>() {
         @Override
         public Integer call() throws Exception {
            TimeUnit.SECONDS.sleep(1);
            return 1;
         }
      });
      task.start();

      Assert.assertFalse(task.awaitToFinish(500));
   }

   @Test
   public void testAwaitToFinish2() {
      Task<Integer> task = new Task<>(new Callable<Integer>() {
         @Override
         public Integer call() throws Exception {
            TimeUnit.MILLISECONDS.sleep(1000);
            return 1;
         }
      });
      task.start();

      try {
         TimeUnit.MILLISECONDS.sleep(1000);
         Assert.assertTrue(task.awaitToFinish(500));
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
}
