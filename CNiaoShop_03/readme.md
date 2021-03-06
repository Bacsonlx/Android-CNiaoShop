# **SliderLayout实现轮播图**

博客地址：https://blog.csdn.net/weixin_43750332/article/details/114456718

(注：代码采用的是第一章的模板，关于轮播图的代码只需要看HomeFragment以及相关的布局文件即可 )

效果图：

![3-2](C:\Users\11609\Pictures\Temp\3-2.png)

## 一、**AndroidImageSlider简介**

​    AndroidImageSlider是GitHub上的一个非常火的开源项目，由“代码家”出品，他的网址是https://github.com/daimajia/AndroidImageSlider。下面的用法介绍很多都是参考他的GitHub主页的常规用法，因此建议大家学习使用开源项目的时候直接参考GitHub主页的用法介绍。

​    下图是AndroidImageSlider的架构，最核心的类是SliderLayout，他继承自相对布局，包含了可以左右滑动切换的SliderView，以及页面指示器PagerIndicator，也就是上图中的4个小圆点。这两个都可以自定义，常规的用法是：使用TextSliderView+自定义PagerIndicator，下面对常规用法就行介绍。

![3-1](C:\Users\11609\Pictures\Temp\3-1.png)



## **二、AndroidImageSlider实现广告轮播条**

###  1、添加依赖性和权限

参考GithHub中AndroidImageSlider主页的介绍，首先将需要的三个依赖项目引入进来，他们分别是：

```xml
dependencies {
    	implementation "com.android.support:support-v4:+"
    	implementation 'com.squareup.picasso:picasso:2.3.2'
    	implementation 'com.nineoldandroids:library:2.4.0'
    	implementation 'com.daimajia.slider:library:1.1.5@aar'
}
```

​     建议全部的依赖项目统一使用最新的版本，因为依赖的项目之间可能也会存在依赖，如果不想出现版本冲突问题，最后全部使用最新版。

添加权限：

```xml
    <!-- if you want to load images from the internet -->
    <uses-permission android:name="android.permission.INTERNET" />

    <!-- if you want to load images from a file OR from the internet -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

### 2、布局文件

在布局文件中放置SliderLayout以及PagerIndicator。注意布局中是通过相对布局把PagerIndicator压在SliderLayout之上的。代码都可以在GitHub上找到参考。

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
	<!-- 定义SliderLayout -->
    <com.daimajia.slider.library.SliderLayout
        android:id="@+id/slider"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        />
    <!-- 自定义Indicator  -->
    <com.daimajia.slider.library.Indicators.PagerIndicator
        android:id="@+id/custom_indicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        custom:selected_color="#0095BF"
        custom:unselected_color="#55333333"
        custom:shape="rect"
        custom:selected_padding_left="2dp"
        custom:selected_padding_right="2dp"
        custom:unselected_padding_left="2dp"
        custom:unselected_padding_right="2dp"
        custom:selected_width="6dp"
        custom:selected_height="6dp"
        custom:unselected_width="6dp"
        custom:unselected_height="6dp"
        android:layout_gravity="center"
        />

</LinearLayout>
```

### 3、代码实现

**主要步骤是：**

- 准备好要显示的数据，包括图片和图片描述等。
- 新建若干个TextSliderView并且设置好数据以及相应的监听，最后添加到SliderLayout里面。
- 对SliderLayout进行一些个性化的设置，比如动画，自定义PagerIndicator，每一个广告的延时时间等。
- 最后别忘了在布局摧毁的时候，调用sliderLayout.stopAutoCycle()方法停止广告的轮播，以释放资源。

```java
public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;
    private PagerIndicator mIndicator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);

        initSlider();
        return  view;
    }

    // 初始化Slider
    private void initSlider() {
        // 添加图片和描述
        TextSliderView textSliderView = new TextSliderView(this.getActivity());
        textSliderView
                .description("11111111")
                .image("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3880478576,1199725881&fm=26&gp=0.jpg");
        // 为图片添加点击事件
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(), "11111111111", Toast.LENGTH_SHORT).show();
            }
        });

        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
        textSliderView2
                .description("22222222")
                .image("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=189934895,680861583&fm=26&gp=0.jpg");
        // 为图片添加点击事件
        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(), "222222222", Toast.LENGTH_SHORT).show();
            }
        });

        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
        textSliderView3
                .description("33333333")
                .image("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/241f95cad1c8a7863cb5bacd6709c93d71cf5052.jpg");
        // 为图片添加点击事件
        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(), "333333333", Toast.LENGTH_SHORT).show();
            }
        });


        // 在sliderLayout里设置图片
        sliderLayout.addSlider(textSliderView);
        sliderLayout.addSlider(textSliderView2);
        sliderLayout.addSlider(textSliderView3);

        // 添加指示器 参数为Indicators的位置 （这是默认的Indicator效果 区别自定义Indicator）
//        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // 添加自定义Indicator
        sliderLayout.setCustomIndicator(mIndicator);
        // 添加自定义动画
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        // 转场效果
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.CubeIn);
        // 转场时间
        sliderLayout.setDuration(2000);

        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("HomeFragment", "onPageScrolled: ");

            }

            @Override
            public void onPageSelected(int position) {
//                Log.d("HomeFragment", "onPageSelected: ");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d("HomeFragment", "onPageScrollStateChanged: ");

            }
        });
    }
}
```

