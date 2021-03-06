# 自定义ToolBar的使用



博客地址：https://blog.csdn.net/weixin_43750332/article/details/114450353

代码中：**MainActivity演示的是自定义ToolBar的使用，TestActivity演示的是原生ToolBar的使用。**

## 一、原生ToolBar

### 1. 什么是ToolBar

Toolbar 是 android 5.0 引入的一个新控件，Toolbar出现之前，我们很多时候都是使用ActionBar以及ActionActivity实现顶部导航栏的，因此Toolbar可以理解为是ActionBar的升级版。Toolbar大大扩展了ActionBar，使用更灵活，不像ActionBar那么固定，Toolbar更像是一般的View元素，可以被放置在view树体系的任意位置，可以应用动画，可以跟着scrollView滚动，可以与布局中的其他view交互。



### 2. **使用ToolBar**

在我们需要顶部导航栏的布局文件当中添加Toolbar，并且配置一些常用的属性（使用自定义属性的时候需要注意把命名空间“app”添加到根节点）

![image-20210305213340288](C:\Users\11609\Pictures\Temp\2-0.png)

![image-20210305213541686](C:\Users\11609\Pictures\Temp\2-2.png)

这里只列出一些常用的属性，比如最小高度，返回按钮的图标，背景等等。这里需要注意的是，**属性值中的“?”表示对Android系统的主题样式进行重用**。意思是如果我们改变了主题样式中的**colorPrimary**属性的话，Toolbar的背景颜色也会随之改变，因此提醒我们去主题样式中进行一些配置。

```xml
xmlns:app="http://schemas.android.com/apk/res-auto"

<android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?attr/colorPrimary"
        android:minHeight="?actionBarSize"
        app:navigationIcon="@mipmap/arrow_left"
        app:title="标题"/>

```

**拓展：**Android主题颜色的详解

- **colorPrimaryDark**是我们手机最顶端的状态栏的背景颜色（改变它需要Android5.0以及以上的手机支持才行）。
- **colorPrimary**是指导航栏的颜色。
- **colorAccent**是指我们常用控件比如Button等的颜色。
- **textColorPrimary**是指我们导航栏中标题的颜色。
- **windowBackground**是指我们窗体的默认颜色。
- **navigationBarColor**是指Android手机中虚拟按键的背景颜色。





### 3.  **去除ActionBar：**

在**styles.xml**文件中进行一些常用的配置。由于我们使用的是AppCompatActivity，因此必须使用AppCompat的相关主题，笔者这里使用亮色调的没有ActionBar的主题，注意需要在清单文件当中去使用自己定义的主题。为了完全去掉ActionBar，需要把**windowActionBar**、**windowNoTitle**以及加上**android**声明的也写上，确保把系统自带的以及第三方兼容包的ActionBar都彻底去掉。

```xml
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat">
        <!-- Customize your theme here. -->

        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="android:textColorPrimary">@color/white</item>
        <!-- 窗口的背景颜色 -->
        <item name="android:windowBackground">@android:color/white</item>
        <!--隐藏ActionBar-->
        <item name="android:windowActionBar">false</item>
        <item name="android:windowNoTitle">true</item>

        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>

    </style>
```

------

## 二、 自定义ToolBar

通过下面的对比可以知道，原生的Toolbar画面太美不忍直视，一般来说要在项目当中使用Toolbar我们都应该去自定义Toolbar。下面开始讨论如何去自定义Toolbar。

![2-1](C:\Users\11609\Pictures\Temp\2-1.png)




### **核心的要点：**

- 自定义布局，添加到Toolbar当中
- 有必要的时候自定义一些属性
- 自定义Class继承Toolbar，读取自定义属性，对Toolbar的布局显示，内容进行设置，最后需要对外公开一些函数用于设置标题、监听等。下面通过步骤来详细说明。

