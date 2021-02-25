# LiveDate
# MVVM
# MVP

## A activity----B activity 跳转的生命周期 ##

1. 当B launchModel为standard 
A onPause--B onCreate-B onStart--B onResume--A onStop

2. 当B launchModel 为SingleTop  切B Activity 已经位于栈顶，比如点击通知栏，连点 此时只有B 页面生命周期发生变化
B onPause--B onNewIntent--B onResume

3. 当B launchModel 为singleInstance singleTask

A onPause---B onNewIntent--B onRestart--B onStart--onResume--A onStop


## Activity 的几种LaunchModel ##

1. standard 标准模式，每次都会创建一个新的Activity 实例
2. singleTop 栈顶模式，要启动的Activity如果位于栈顶则复用该Activity 如果不在栈顶则像Standard一样创建新的
Activity实例
3. SingleTask 栈内复用模式 不管启动的Activity 是位于栈顶还是栈顶都会复用该Activity实例，并销毁上边的所有Activity实例
4. SingleInstance 全局唯一模式 主要是指该Activity位于一个单独栈中


## Activity 生命周期： ##
onCreate(窗口正在创建，比如加载layout 布局文件，可做一些初始化操作)

-onStart（此时Activity正在启动，已经可见，还没出现在前台，无法交互）

-onResume（此时已经可见，并可进行交互）

-onPause（窗口停止，可以做一些数据存储，停止动画等 可见，不可交互）

-onStop（不可见，不可交互）

-onDestroy（销毁，释放资源）
![Image text](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8a03ac58e9c049e98a0a671dfc8b54f9~tplv-k3u1fbpfcp-zoom-1.image)

##Context 
context 上下文对象，对当前运行环境的具体描述，一个抽象类，Activity,service,Application 都是具体实现类，
为系统组件的正常运行提供必要的环境和资源

可用于 获取系统资源
启动系统组件
获取服务
Context个数 = Service个数 + Activity个数 + Application个数 + ContextImpl个数。
dialog不能使用getApplicationContext(),会报错为 Unable to add window  token null is no valid

##事件冲突
事件 ACTION_DOWN
     ACTION_UP
     ACTION_MOVE
     ACTION_CANCEL

时间分发消费
  dispatchTouchEvent 
  onInterceptTouchEvent
  onTouchEvent,
 dispatchTouchEvent返回值的意义跟onTouch返回值的区别，两者返回true的时候ACTION都会传递到ACTION_DOWN，其中onTouch返回true的时候由于
 不会执行到onTouchEvent所以不会执行到onClick，dispatchTouchEvent返回值为true对会不会执行onClick没有影响；onTouch返回false的时候执行onTouchEvent，
 如果此时该控件是可点击的就发执行onClick,而dispatchTouchEvent返回false就停止ACTION传递
dispatchTouchEvent返回true代表自己处理，交由自己的onTouchEvent处理；返回false表示子View不处理，会返回给父viewgroup的onTouchEvent处理
##Handler
handler 接收和处理消息
loop 消息泵 轮询，通过messageQueue.next() 来取出消息message，
messageQueue 单列表结构存储message 的消息队列
message 消息 存储常用的obj 和what

问：一个线程有几个handler？
一个线程有多个handler 只有一个looper,
问：怎么保证一个looper
threadLocal  <key,value> key 是threadLocal value 是looper
looper.java 中 prepare方法中：

private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }
    
问：内存泄漏的原因？ 持有Activity
直接new Handler（）相当于匿名内部类
匿名内部类 内部类会持有外部类的对象
问：handler 内存泄漏，为什么其他内部类没有这个问题？

Handler内存泄漏的原因
MessageQueue持有Message，Message持有activity
delay多久，message就会持有activity多久
方法：静态内部类、弱引用

messageQueue-> Message-》包含handler-》持有Activity
 delay
 messageQueue位于内存，释放持有Activity 内存泄漏

Looper存在整个应用程序的生命周期.
Handler在主线程创建时会关联到Looper的Message Queue,Message添加到消息队列中的时候Message(排队的Message)会持有当前Handler引用，
当Looper处理到当前消息的时候，会调用Handler#handleMessage(Message).就是说在Looper处理这个Message之前，
会有一条链MessageQueue -> Message -> Handler -> Activity，由于它的引用导致你的Activity被持有引用而无法被回收`

避免内存泄漏：
如果Handler中执行的是耗时的操作，在关闭Activity的时候停掉你的后台线程。线程停掉了，就相当于切断了Handler和外部连接的线，Activity自然会在合适的时候被回收。
如果Handler是被delay的Message持有了引用，那么在Activity的onDestroy()方法要调用Handler的remove*方法，把消息对象从消息队列移除就行了。

问：为什么可以在主线程newHandler（）就可以用。
launcher-zygote-application(art)-main(在ActivityThread)

ActivityThread.java

       public static void main(String[] args) {
      .................................

   Looper.prepareMainLooper();

      ..................................

        // End of event ActivityThreadMain.
        Trace.traceEnd(Trace.TRACE_TAG_ACTIVITY_MANAGER);
   Looper.loop();

        throw new RuntimeException("Main thread loop unexpectedly exited");
    }

问：在子线程newHandler需要准备什么？

        looper.prepare（）
        looper.loop()
        
                                                         
问：子线程中维护的looper消息队列无消息的时候
Message msg = queue.next(); // might block
当无消息的时候:

MessageQueue.java

    Message next() {
        // Return here if the message loop has already quit and been disposed.
        // This can happen if the application tries to restart a looper after quit
        // which is not supported.
        final long ptr = mPtr;
        if (ptr == 0) {
            return null;
        }

    int pendingIdleHandlerCount = -1; // -1 only during first iteration
        int nextPollTimeoutMillis = 0;
        for (;;) {
            if (nextPollTimeoutMillis != 0) {
                Binder.flushPendingCommands();
            }
结束上次循环后，再次循环此时nextPollTimeoutMillis=-1
  nativePollOnce(ptr, nextPollTimeoutMillis);

            synchronized (this) {
                // Try to retrieve the next message.  Return if found.
                final long now = SystemClock.uptimeMillis();
                Message prevMsg = null;
                Message msg = mMessages;
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null && !msg.isAsynchronous());
                }
                if (msg != null) {
                    if (now < msg.when) {
                        // Next message is not ready.  Set a timeout to wake up when it is ready.
                        nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                    } else {
                        // Got a message.
                        mBlocked = false;
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        if (DEBUG) Log.v(TAG, "Returning message: " + msg);
                        msg.markInUse();
                        return msg;
                    }
                } else {
                    // No more messages.
   nextPollTimeoutMillis = -1; 无消息时此时为-1
                }

                // Process the quit message now that all pending messages have been handled.
                if (mQuitting) {
                    dispose();
                    return null;
                }

                // If first time idle, then get the number of idlers to run.
                // Idle handles only run if the queue is empty or if the first message
                // in the queue (possibly a barrier) is due to be handled in the future.
                if (pendingIdleHandlerCount < 0
                        && (mMessages == null || now < mMessages.when)) {
                    pendingIdleHandlerCount = mIdleHandlers.size();
                }
                if (pendingIdleHandlerCount <= 0) {
                    // No idle handlers to run.  Loop and wait some more.
                    mBlocked = true;
   continue; 跳出本次循环
   
                    }
                    
   如果值为-1 为无限等待               
  private native void nativePollOnce(long ptr, int timeoutMillis); /*non-static for callbacks*/
  
  
 handler的睡眠与唤醒
 
 主线程handler能释放吗？为什么
 不能释放会报Main thread not allowed to quit
 
 MessageQueue.java
 
    void quit(boolean safe) {
         if (!mQuitAllowed) {
             throw new IllegalStateException("Main thread not allowed to quit.");
         }
 
         synchronized (this) {
             if (mQuitting) {
                 return;
             }
             mQuitting = true;
 
             if (safe) {
                 removeAllFutureMessagesLocked();
             } else {
                 removeAllMessagesLocked();
             }
 
             // We can assume mPtr != 0 because mQuitting was previously false.
             nativeWake(mPtr);
         }
     }
 问：既然一个looper可以存在多个handler往MessageQueue中添加数据，发消息时可能Handler 处于不同的线程，那么他内部如何确保线程安全的？  
 一个线程-->一个looper->一个messageQueue
 synchronized(this) 可用于函数 静态函数 代码块
   使用锁机制来保证线程安全
  
  问：我们使用Message 的时候如何创建他？
  享元设计模式 
  主要用于减少创建对象的数量，以减少内存占用和提高性能，这种类型的设计模式属于结构型模式
  他提供了减少对象数量从而改善应用所需的对象结构的方式，
  
  避免重复创建对象，节省内存空间。根据内部状态把对象存储在共享池，需要时去共享池取就行
  
  主要解决：在有大量对象时，有可能会造成内存溢出，我们把其中共同的部分抽象出来，如果有相同的业务请求，直接返回在内存中已有的对象，避免重新创建。
  
  何时使用： 1、系统中有大量对象。 2、这些对象消耗大量内存。 3、这些对象的状态大部分可以外部化。 4、这些对象可以按照内蕴状态分为很多组，当把外蕴对象从对象中剔除出来时，每一组对象都可以用一个对象来代替。 5、系统不依赖于这些对象身份，这些对象是不可分辨的。
  
  如何解决：用唯一标识码判断，如果在内存中有，则返回这个唯一标识码所标识的对象。
  
  关键代码：用 HashMap 存储这些对象。
  
  应用实例： 1、JAVA 中的 String，如果有则返回，如果没有则创建一个字符串保存在字符串缓存池里面。 2、数据库的数据池。
  
  优点：大大减少对象的创建，降低系统的内存，使效率提高。
  
  缺点：提高了系统的复杂度，需要分离出外部状态和内部状态，而且外部状态具有固有化的性质，不应该随着内部状态的变化而变化，否则会造成系统的混乱。
  
  使用场景： 1、系统有大量相似对象。 2、需要缓冲池的场景。
  
  注意事项： 1、注意划分外部状态和内部状态，否则可能会引起线程安全问题。 2、这些类必须有一个工厂对象加以控制。
  
  问：looper 死循环为甚不会卡死？
  ANR: 五秒内没有响应输入事件，广播接收器十秒内没有执行完毕，消息没有及时处理，Message
  消息队列中无消息怎么处理 block
  nativePollOnce值为-1表示无限等待，让出cpu时间片给其线程，本线程等待
  0表示无须等待直接返回
  nativePollOnce -> epoll(linux) ->linux层的messagequeue
  
## ThreadLocal
ThreadLocal是一种无同步的线程安全实现，体现了Thread-Specific Storage模式：即使只有一个入口，内部也会为每个线程分配特有的存储空间。
由于线程间没有共享资源，因此可以实现无锁线程安全；
总结：线程并发：在多线程开发的场景下
      传递数据：我们通过ThreadLocal 在同一线程，不同组件中传递公共变量
      线程隔离：每个线程的变量都是独立的，不会互相影响
      
  synchronized 同步机制采用时间换空间的方式，只提供了一份变量，让不同的线程排队访问
  ThreadLocal 采用以空间换时间的方式，为每一个线程提供了一份变量副本，从而实现同时访问互不干扰
 ##Retrofit
 注解+动态代理+反射+okHttp
 
 
 
 
 adb 安装
 
 adb install ---
 
获取SHA1
keytool -list -v -keystore jks 文件



描述一下android应用的启动过程！

1，点击桌面应用图标，Launcher 进程将启动 Activity（MainActivity）的请求以 Binder 的方式发送给了 AMS。

2，AMS 接收到启动请求后，交付 ActivityStarter 处理 Intent 和 Flag 等信息，然后再交给 ActivityStackSupervisior/ActivityStack

3，处理 Activity 进栈相关流程。同时以 Socket 方式请求 Zygote 进程 fork 新进程。

4，Zygote 接收到新进程创建请求后 fork 出新进程。

5，在新进程里创建 ActivityThread 对象，新创建的进程就是应用的主线程，在主线程里开启 Looper 消息循环，开始处理创建 Activity。

6，ActivityThread 利用 ClassLoader 去加载 Activity、创建 Activity 实例，并回调 Activity 的 onCreate()方法。这样便完成了 Activity 的启动。

非系统应用如何在应用退到后台切换下一首歌曲？
通过service 来实现后台音乐播放


retrofit：

Retrofit就是一个网络请求框架的封装，底层的网络请求默认使用的Okhttp，
本身只是简化了用户网络请求的参数配置等，还能与Rxjava相结合，使用起来更加简洁方便。

用到的设计模式：
外观模式，构建者模式，工厂模式，代理模式，适配器模式，策略模式，观察者模式
