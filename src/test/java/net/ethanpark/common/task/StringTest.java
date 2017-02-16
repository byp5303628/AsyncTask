package net.ethanpark.common.task;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author EthanPark <br/>
 * @version 1.0
 */
public class StringTest {
   @Test
   public void testString(){

      String s1 = null;

      Assert.assertEquals(s1 + "C", "nullC");

      Assert.assertEquals("" + "C", "C");
      String s = "[{\"DPort\":\"HGH\",\"Aport\":\"KWL\",\"CanSale\":0},{\"DPort\":\"HGH\",\"Aport\":\"CAN\",\"CanSale\":0},{\"DPort\":\"CTU\",\"Aport\":\"DLC\",\"CanSale\":0}]";
   }
}
