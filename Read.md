viewModel:数据处理框架，可感知生命周期,数据绑定了Activity/Fragment生命周期，当Activity正常关闭的时候，都会清除ViewModel中的数据.
LiveData:可被观察的数据持有类它可以感知 Activity、Fragment或Service 等组件的生命周期，
LiveData 是一个抽象类，它的实现子类有 MutableLiveData ，MediatorLiveData。在实际使用中，用得比较多的是 MutableLiveData

1.可以做到组件处于激活状态的时候才会回调相应的方法，从而刷新UI

2.感知生命周期，不用担心内存泄露

3.当Config 导致Activity重新创建的时候，不需要手动去处理数据的存储和恢复。已经包含

4.当Activity不是处于激活状态的时候，如果想LiveData setValue 之后立即回调obsever的onChange方法。而不是等
Activity处于激活的时候才回调，你可以使用observeForver方法，但是你必须再onDestroy的时候removeOb；

LiveData与MutableLiveData的其实在概念上是一模一样的.唯一几个的区别如下:

1.MutableLiveData的父类是LiveData

2.LiveData在实体类里可以通知指定某个字段的数据更新.

3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段


dataBinding 数据绑定

绑定自定义方法：

  android:onClick="@{()->methord.onClick()}"
  绑定值：
   android:text="@{userBean.passWord}"
   
   双向绑定：
   xml:绑定值
    android:text="@={userBean.passWord}"
    get方法添加注解： @Bindable
    
     @Bindable
        public String getName() {
            return name;
        }
    set方法：调用 notifyPropertyChanged(BR.name);
    
        public void setName(String name) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }
