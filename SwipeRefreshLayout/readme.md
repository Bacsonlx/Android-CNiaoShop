# SwipeRefreshLayout实现下拉刷新

**博客地址**：https://blog.csdn.net/weixin_43750332/article/details/114650657

**运行效果：**

<img src="C:\Users\11609\Pictures\Temp\SwipeRefreshLayout.gif" alt="SwipeRefreshLayout" style="zoom: 50%;" />

## 一、SwipeRefreshLayout的简介

 

   以前我们都是使用XListView等流行框架实现下拉刷新以及上拉加载更多的，今天介绍一个新的控件SwipeRefreshLayout，它是谷歌官方提供的一个新控件，同样可以实现下拉刷新。

​    SwipeRefreshLayout是V4支持包中提供的一个新的控件，它可以实现下拉刷新的功能。但是缺点是没有实现上拉加载更多。但是网上有很多开发者对它进行加强，使得它可以实现上拉加载。

​    SwipeRefreshLayout可以与ListView，RecycleView，GridView等列表控件配合使用，而且高度解耦，使用方便灵活，下拉刷新效果比较炫酷。

​    这是SwipeRefreshLayout的官方文档，注：这是国内的API镜像。

http://doc.cniao5.com/android/reference/android/support/v4/widget/SwipeRefreshLayout.html

 

## 二、SwipeRefreshLayout的基本使用

 

1、在Gradle中引入SwipeRefreshLayout的依赖库。

```xml
implementation 'com.android.support:appcompat-v7:23.0.1'
```

 

2、在布局文件当中放置我们的SwipeRefreshLayout。SwipeRefreshLayout里面也可以放置任意列表控件，通过这种方式可以实现SwipeRefreshLayout和列表之间的解耦。

activity_main.xml 布局文件：

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.swiperefreshlayout.widget.SwipeRefreshLayout

        android:id="@+id/refreshlayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />

    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>


</RelativeLayout>

```

3.基本使用

```java
refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

//设置进度条的颜色，不定长参数可以设置多种颜色
//对于RefreshLayout，网上有人说最多4种颜色，不要使用android.R.color.，否则会卡死
refreshLayout.setColorSchemeColors(
        Color.RED,
        Color.YELLOW,
        Color.GREEN);

//设置进度条的背景颜色
refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);

//设置大小
refreshLayout.setSize(SwipeRefreshLayout.LARGE);

//设置手指划过多少像素开始触发刷新
refreshLayout.setDistanceToTriggerSync(100);

//设置刷新的时候监听，三秒钟之后添加数据完毕
refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        //模拟网络请求数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    //添加数据
                    myAdapter.addData(i, "new data " + i);
                    myAdapter.notifyItemRangeChanged(0, 30);
                }

                //recyclerView回到最上面
                recyclerView.scrollToPosition(0);
                //判断是否在刷新
//                      refreshLayout.isRefreshing()
                //刷新完毕，关闭下拉刷新的组件
                refreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
});

```

