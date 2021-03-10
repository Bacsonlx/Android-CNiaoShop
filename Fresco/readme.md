# Fresco的使用

**博客地址**：https://blog.csdn.net/weixin_43750332/article/details/114645336

## 一、Fresco的简介

**Fresco的GitHub地址**： https://github.com/facebook/fresco 

**Fresco的中文使用文档介绍**： http://fresco-cn.org/docs/index.html

​    Fresco是目前最强大的图片加载组件。以前们常用的比较火的是：

[Android图片异步加载框架Android-Universal-Image-Loader](http://blog.csdn.net/hantangsongming/article/details/41961749)。是FaceBook出品的，项目中使用了MVC模式。

​    Fresco中设计有一个叫做image pipeline的模块。它负责从网络，从本地文件系统，本地资源加载图片。 为了最大限度节省空间和CPU时间，它含有3级缓存设计（2级内存，1级文件）。

​    Fresco中设计有一个叫做Drawees模块， 方便地显示loading图，当图片不再显示在屏幕上时，及时地释放内存和空间占用。



## 二、Fresco的重要特点

### 特点1-内存管理

- ​    一个没有未压缩的图片，即Android中的Bitmap，占用大量的内存。大的内存占用势必引发更加**频繁的GC**。 在5.0以下，GC将会显著地引发界面卡顿。

- 在5.0以下系统，Fresco将图片放到一个特别的内存区域。当然，在图片不显示的时候，占用的内存会自动被释放。 这会使得APP更加流畅，减少因图片内存占用而引发的OOM。

- 给图片分配内存的方式：

- Fresco：Ashmem(系统匿名共享内存)

- 其他：Java Heap


### 特点2-渐进式呈现图片

-  **渐进式的JPEG图片**格式已经流行数年了，渐进式图片格式先呈现大致的图片轮廓，然后随着图片下载的继续， 呈现逐渐清晰的图片，这对于移动设备，尤其是慢网络有极大的利好，可带来更好的用户体验。

### 特点3-Gif图和WebP格式

- ​    支持加载**Gif图**，支持**WebP**格式。

### 特点4-图像的呈现

- **自定义居中焦点**(对人脸等图片显示非常有帮助)。
- 圆角图，当然圆圈也行。
- 下载失败之后，点击重现下载。
- 自定义占位图，自定义overlay, 或者进度条。
- 指定用户按压时的overlay。

### 特点5-图像的加载

- 为同一个图片指定不同的远程路径，或者使用已经存在本地缓存中的图片。
- 先显示一个低解析度的图片，等高清图下载完之后再显示高清图。
- 加载完成回调通知。
- 对于本地图，如有EXIF缩略图，在大图加载完成之前，可先显示缩略图。
- 缩放或者旋转图片。
- 处理已下载的图片。
- WebP支持。



## 三、Fresco的基本使用

1. 在Gradle中引入Fresco的依赖库

   ```xml
   implementation 'com.facebook.fresco:fresco:0.9.0'
   ```

2. 配置自己的Application，并且在onCreate的时候调用

   ```java
   Fresco.initialize(this);
   ```

   在清单文件中添加网络权限：

   ```xml
   <uses-permission android:name="android.permission.INTERNET"/>
   ```

   配置Application不要忘了

   ```xml
   android:name=".MyApplication" <-自定义Application的名字
   ```

   

3. 在布局文件当中放置我们的Fresco的常用控件。以SimpleDraweeView为例，用于放置图片。**activity_main.xml 布局文件**

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:fresco="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin"
    tools:context=".MainActivity">


    <com.facebook.drawee.view.SimpleDraweeView
        android:id="@+id/image_view"
        android:layout_width="500dp"
        android:layout_height="300dp"
        fresco:placeholderImage="@drawable/default_loading"
        />
    
</RelativeLayout>


```

其中fresco:placeholderImage是自定义的属性，需要在布局文件头上加入:

- xmlns:fresco="http://schemas.android.com/apk/res-auto"

该属性是表示在下载图片前所用的默认图片



**下面对常用属性进行说明：**

- android:layout_width="20dp"  // 不支持wrap_content， 如果要设置宽高比, 需要在Java代码中指定setAspectRatio(float ratio);

- android:layout_height="20dp"  // 不支持wrap_content 

- fresco:placeholderImage="@color/wait_color" // 下载成功之前显示的图片

- fresco:placeholderImageScaleType="fitCenter" // 设置图片缩放. 通常使用focusCrop,该属性值会通过算法把人头像放在中间

-  fresco:failureImage="@drawable/error" // 加载失败的时候显示的图片

- fresco:failureImageScaleType=“centerInside" // 设置图片缩放

- fresco:retryImage="@drawable/retrying" // 加载失败,提示用户点击重新加载的图片(会覆盖failureImage的图片)

- fresco:retryImageScaleType="centerCrop" // 是不是设置圆形方式显示图片 

- fresco:roundAsCircle="false" // 圆角设置

    fresco:roundedCornerRadius="1dp"

    fresco:roundTopLeft="true"

    fresco:roundTopRight="false"

    fresco:roundBottomLeft="false"

    fresco:roundBottomRight="true"

    fresco:roundWithOverlayColor="@color/corner_color"

    fresco:roundingBorderWidth="2dp"

    fresco:roundingBorderColor="@color/border_color"

4.   代码中，只要配置一个图片对应的Uri就可以了

   ```java
   Uri uri = Uri.parse(img_url);
   draweeView.setImageURI(uri);
   ```

   > ​    剩下的，Fresco会替你完成:
   >
   > ​	**显示占位图直到加载完成；**
   >
   > ​	**下载图片；**
   >
   > ​	**缓存图片；**
   >
   > ​	**图片不再显示时，从内存中移除；**

## 四、渐进式显示网络图片

- Fresco 支持渐进式的网络JPEG图。在开始加载之后，图会从模糊到清晰渐渐呈现。
- 你可以设置一个清晰度标准，在未达到这个清晰度之前，会一直显示占位图。
- 渐进式JPEG图仅仅支持网络图 
- 核心的代码就是给draweeView配置一个可以显示渐进式JPEG的控制器。由于网络太快的原因，效果不会很明显，不过我们的目的已经达到了。

```java
private void requestImage(){

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(img_url2))
                .setProgressiveRenderingEnabled(true)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request).build();
    
        draweeView.setController(controller);
}
```

## 五、多图请求

- 多图请求就是先显示模糊的图片，等到大图加载成功之后再显示大图。
- 核心代码也是通过控制器实现

```java
DraweeController controller = Fresco.newDraweeControllerBuilder()
        .setLowResImageRequest(ImageRequest.fromUri("低分辨率"))
        .setImageRequest(ImageRequest.fromUri("高分辨率"))
        .build();
draweeView.setController(controller);

```

## 六、事件监听

```java
ControllerListener<ImageInfo> listener = new ControllerListener<ImageInfo>() {
    @Override
    public void onSubmit(String id, Object callerContext) {

    }

	//图片加载成功的时候
    @Override
    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {

    }

    @Override
    public void onIntermediateImageSet(String id, ImageInfo imageInfo) 	{

    }

    @Override
    public void onIntermediateImageFailed(String id, Throwable throwable) 	{

    }

	//图片加载失败
    @Override
    public void onFailure(String id, Throwable throwable) {

    }

    @Override
    public void onRelease(String id) {

    }
};

```

```java
// controller设置控制器 setControllerListener(listener) 就可以启用事件监听了。

DraweeController controller = Fresco.newDraweeControllerBuilder()
        .setLowResImageRequest(ImageRequest.fromUri("低分辨率"))
        .setImageRequest(ImageRequest.fromUri("高分辨率"))
		.setControllerListener(listener)
        .build();
draweeView.setController(controller);


```

