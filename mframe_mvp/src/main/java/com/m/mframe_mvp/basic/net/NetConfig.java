package com.m.mframe_mvp.basic.net;

public interface NetConfig {
   /**
    * baseUrl
    *
    * @return
    */
   String mBaseUrl();

   /**
    * 连接超时时间
    *
    * @return
    */
   long configConnectTimeoutMills();

   /**
    * 读取超时时间
    *
    * @return
    */
   long configReadTimeoutMills();

   /**
    * 写入超时时间
    *
    * @return
    */
   long configWriteTimeoutMills();

   /**
    * 是否调试模式
    *
    * @return
    */
   boolean configLogEnable();
}
