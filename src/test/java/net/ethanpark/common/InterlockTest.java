package net.ethanpark.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Interlock Tester.
 * 
 * @author EthanPark
 * 
 * @version 1.0
 */
public class InterlockTest {
   /**
    * 
    * Method: exchange(T needToBeAssigned, T value)
    * 
    */
   @Test
   public void testExchangeForNeedToBeAssignedValue() throws Exception {
      Map<String, String> map = new HashMap<>();
      map.put("5", "5");

      Map<String, String> needToBeAssigned = null;

      Assert.assertEquals(Interlock.exchange(needToBeAssigned, map).size(), 1);
   }

   /**
    * 
    * Method: exchange(Proxy<T> proxy, T value)
    * 
    */
   @Test
   public void testExchangeForProxyValue() throws Exception {
      //TODO: Test goes here...
      Map<String, String> map = new HashMap<>();
      map.put("5", "5");

      Map<String, String> needToBeAssigned = null;

      Interlock.Proxy<Map<String, String>> proxy =
            new Interlock.Proxy<>(needToBeAssigned);

      Interlock.exchange(proxy, map);

      Assert.assertEquals(proxy.getProxyObj().size(), 1);
   }

   /**
    * 
    * Method: getProxyObj()
    * 
    */
   @Test
   public void testGetProxyObj() throws Exception {
      //TODO: Test goes here... 
   }

   /**
    * 
    * Method: setProxyObj(T proxyObj)
    * 
    */
   @Test
   public void testSetProxyObj() throws Exception {
      //TODO: Test goes here... 
   }
}
