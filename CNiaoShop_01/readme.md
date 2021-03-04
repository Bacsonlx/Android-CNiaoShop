# **底部导航栏的实现方式**

## **一、常见的实现方式**

- TabHost+Activity：资源开销比较大，官方已经不推荐使用。
- RadioButton（RadioGroup）+Fragment：实现起来比较麻烦。
- **FragmentTabHost+Fragment：实现简单，资源开销小，推荐使用。**

## 二、FragmentTabHost介绍

​		如下图所示，**整一个底部导航栏是一个FragmentTabHost**，里面包含的**每一个“小按钮”我们称之为TabSpec**，也就是每一个分页。TabSpec里面需要有**指示器Indicator**，用来指示用户选中了哪一个，里面一般包含一张图片和文字描述。

![img](file:///D:/Temp/msohtmlclip1/01/clip_image002.jpg)



## **三、FragmentTabHost实现步骤以及注意点**

1. 所用的Activity必须要**继承FragmentActivity**，不然项目就会崩溃。
2. 调用FragmentTabHost的**setup()方法**，**设置FragmentManager**，以及指定用于装载Fragment的布局容器。
3. 调用FragmentTabHost的**addTab()方法添加分页**。



## 四、Selector实现点击分页改变颜色

​		在Android开发过程中，经常对View的背景在不同的 状态 (正常、按下，选中… )下设置不同的 背景 ，增强用户体验。

### Selector的状态

**1.android:state_pressed="true/false"**

true:表示按下状态下使用，false:表示非按下状态下使用。

**2.android:state_focused="true/false"**

ture:表示聚焦状态使用（例如使用滚动球/D-pad聚焦Button），false:表示非聚集状态下使用。

**3.android:state_selected="true/false"**

true:表示被选中状态下使用，false:表示非选中下使用

**4.android:state_active="true/false"**

true:表示可勾选状态时使用，false:表示不可勾选状态下使用

**5. android:state_checkable="true/false"**

true:表示勾选状态下使用，false:表示非勾选状态使用

**6.android:state_checked="true/false"**

true:表示勾选状态下使用，false:表示非勾选状态使用

**7. android:state_enabled="true/false"**

true:表示可用状态使用（能接收触摸/点击事件），false:表示不可用状态使用

**8. android:state_window_focused="true/false"**

true:表示应用程序窗口有焦点时使用（应用程序在前台），false:表示无焦点时使用



#### **图标示例：**

定义选择器：

<img src="C:\Users\11609\AppData\Roaming\Typora\typora-user-images\image-20210304224543865.png" alt="image-20210304224543865" style="zoom:80%;" />

在主活动中设置图标的更改样式：

![image-20210304225434828](C:\Users\11609\AppData\Roaming\Typora\typora-user-images\image-20210304225434828.png)

#### **文字示例：**

定义选择器：

![image-20210304224715597](C:\Users\11609\AppData\Roaming\Typora\typora-user-images\image-20210304224715597.png)

在indicator的布局文件中修改TextView的textColor属性

![image-20210304224926257](C:\Users\11609\AppData\Roaming\Typora\typora-user-images\image-20210304224926257.png)





最需要注意的是我们的TextView的文字颜色是通过selector进行状态选择的。需要注意的是，这并不是图片，只是颜色，**不能放在drawable目录下，而应该放在color目录下**。

