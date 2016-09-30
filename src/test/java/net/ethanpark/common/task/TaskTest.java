package net.ethanpark.common.task;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
      Date now = new Date();
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

      Date after = new Date();

      Assert.assertTrue(after.getTime() - now.getTime() > 1000);
   }

   @Test
   public void testConstructor2() {
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

      Assert.assertNotNull(task.getThrowable());
   }
}
