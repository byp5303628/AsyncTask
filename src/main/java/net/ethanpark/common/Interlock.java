package net.ethanpark.common;

/**
 * @author EthanPark <br/>
 * @version 1.0
 */
public class Interlock {

   private static final Object lock = new Object();

   /**
    * 针对指定类型进行数据交换，使用时务必将需要赋值的缓存进行赋值操作
    * 
    * @param needToBeAssigned
    * @param value
    * @param <T>
    * @return
    */
   public static <T> T exchange(T needToBeAssigned, T value) {
      if (needToBeAssigned == null) {
         synchronized (lock) {
            needToBeAssigned = value;
         }
      } else {
         synchronized (needToBeAssigned) {
            needToBeAssigned = value;
         }
      }
      return needToBeAssigned;
   }

   public static <T> void exchange(Proxy<T> proxy, T value) {
      proxy.setProxyObj(exchange(proxy.getProxyObj(), value));
   }

   public static class Proxy<T> {
      private T proxyObj;

      public Proxy(T t) {
         this.proxyObj = t;
      }

      public T getProxyObj() {
         return proxyObj;
      }

      public void setProxyObj(T proxyObj) {
         this.proxyObj = proxyObj;
      }
   }
}
