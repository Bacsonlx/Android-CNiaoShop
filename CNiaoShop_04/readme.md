# 使用okHttp加载首页商品数据 

**博客地址：**https://blog.csdn.net/weixin_43750332/article/details/114609012

**演示效果：**

<img src="C:\Users\11609\Pictures\Temp\5-1.jpg" alt="5-1" style="zoom: 25%;" />

## 一、Android中网络请求的历史

Android中网络请求的进化图：

<img src="C:\Users\11609\Pictures\Temp\5-2.png" alt="5-2" style="zoom: 80%;" />

## 二、OKHttp的简介

 首先，给出oKHttp的项目地址：https://github.com/square/okhttp

​    Android为我们提供了两种HTTP交互的方式： **HttpURLConnection** 和 **Apache HTTP Client**，虽然两者都支持HTTPS流的上传和下载，配置超时，IPv6和连接池，已足够满足我们各种HTTP请求的需求。但更高效的使用HTTP可以让您的应用运行更快、更节省流量。而OkHttp库就是为此而生。

### OkHttp是一个高效的HTTP库:

1. 支持 SPDY ，共享同一个Socket来处理同一个服务器的所有请求
2. 如果SPDY不可用，则通过连接池来减少请求延时
3. 无缝的支持GZIP来减少数据流量
4. 缓存响应数据来减少重复的网络请求

### OkHttp支持：

- 一般的get请求
- 一般的post请求
- 基于Http的文件上传
- 文件下载
- 加载图片
- 支持请求回调，直接返回对象、对象集合
- 支持session的保持

​    OkHttp会从很多常用的连接问题中自动恢复。如果您的服务器配置了多个IP地址，当第一个IP连接失败的时候，会自动尝试下一个IP。OkHttp还处理了代理服务器问题和SSL握手失败问题。（并发）

​    使用 OkHttp 无需重写您程序中的网络代码。OkHttp实现了几乎和java.net.HttpURLConnection一样的API。如果您用了 Apache HttpClient，则OkHttp也提供了一个对应的okhttp-apache 模块。

​    从上面的简单介绍中可以知道，虽然在编程上面并不会简洁很多，但是OKHttp内部的一些功能能够帮助我们自动完成一些很复杂的操作，笔者个人认为最大的卖点就是大大节省用户的流量。

## 三、OKHttp的基本使用

### 1、在Gradle中引入依赖库。

```xml
implementation 'com.squareup.okhttp:okhttp:2.5.0'
```

### 2、OKHttp在使用之前，首先要了解下面几个比较核心的类以及它的功能。

- OkHttpClient 客户端对象
- Request是OkHttp中访问的**请求**( 其中，Post请求中需要包含RequestBody )
- Builder是辅助类，用于生产对象
- Response即OkHttp中的响应，响应中可以得到返回是否成功，返回数据
- MediaType 数据类型，用来表明是JSON等一系列格式
- RequestBody 请求数据，在Post请求中用到
- client.newCall(request).execute()是**同步的请求方法**
- client.newCall(request).enqueue(Callback callBack)是**异步的请求方法**
- Callback里面的代码是执行在子线程的，因此不能更新UI。

### 3、OKHttp的基本使用步骤（以POST方式从服务器取JSON数据为例）

1. 创建OkHttpClient对象，官方文档要求我们最好使用单例模式，在后文对OKHttp进行封装的时候会提到。
2. 如果是post请求的话，需要通过FormEncodingBuilder创建RequestBody对象，指定需要post传进去的参数。get请求则不用。
3. 创建Request对象，这个对象是请求对象，需要指定URL。post请求的时候需要指定RequestBody对象，get请求则不用。
4. 调用OkHttpClient的newCall方法，把Request对象传进去，然后执行execute或者enqueue方法，两者的区别在上文已提到。在CallBack中的onResponse方法就可以做你需要做的事。
5. onResponse回调的参数是response，一般情况下，比如我们希望获得返回的字符串，可以通过response.body(). string()获取；如果希望获得返回的二进制字节数组，则调用response.body().bytes()；如果你想拿到返回的inputStream，则调用response.body().byteStream()看到这，你可能会奇怪，竟然还能拿到返回的inputStream，看到这个最起码能意识到一点，这里支持大文件下载，有inputStream我们就可以通过IO的方式写文件。
6. onResponse执行的线程并不是UI线程。如果你希望操作控件，还是需要使用handler等。

```java
private void requestImages() {
        // Get方式的URl
        // String url ="http://112.124.22.238:8081/course_api/banner/query?type=1";
        // Post 方式的URL
        String url ="http://112.124.22.238:8081/course_api/banner/query";

          OkHttpClient client = new OkHttpClient();
    
          /**
           *  Get 方式
           */
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
    
          /**
           * Post 方法
           */
		// RequestBody 请求数据
        // FormEncodingBuilder 表单构造器
        RequestBody body = new FormEncodingBuilder()
                .add("type","1") // 参数通过add添加
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // execute() 方法是同步的  enqueue() 方法是异步的
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                // 成功获取到了json数据
                String json = response.body().string();
                // Type 是fromJson的第二个参数 表示将json数据转化成的类型
                // Type可以是任何类型 这里是将Json数据转化为List<Banner>类型
                Type type  =new TypeToken<List<Banner>>(){}.getType();
                mBanner = mGson.fromJson(json, type);
                // 获取完数据后初始化轮播图
                initSlider();
            }
        });
    
}
```

这里只是一个简单post请求，从服务器获取数据的介绍，至于get请求只不过是去掉RequestBody对象而已。

## 四、OKHttp的简单封装

回顾上面的代码，试想一下如果每次请求都写这么多重复代码，这样会严重降低开发效率，因此需要对OKHttp进行封装。对代码进行封装是我们最为面向对象程序员的基本素养，减少重复代码，降低维护难度以及成本。

```java
/**
 * 这个类用来辅助OKHttp
 */
public class OkHttpHelper {

    public static final String TAG="OkHttpHelper";
    /**
     * 采用单例模式使用OkHttpClient
     */
    private  static  OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;
    private Handler mHandler;


    static {
        mInstance = new OkHttpHelper();
    }

     /**
     * 单例模式，私有构造函数，构造函数里面进行一些初始化
     */
    private OkHttpHelper(){

        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30,TimeUnit.SECONDS);

        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());

    };

     /**
     * 获取实例
     */
    public static  OkHttpHelper getInstance(){
        return  mInstance;
    }



    // 请求的get方法
    public void get(String url,BaseCallback callback){
        // 构建request对象 调用get方法
        Request request = buildGetRequest(url);

        doRequest(request,callback);

    }

    // 请求的post方法 需要参数
    public void post(String url, Map<String,String> param, BaseCallback callback){
        // 构建request对象 调用post方法
        Request request = buildPostRequest(url, param);

        doRequest(request,callback);
    }




    // request 封装网络请求
    public  void doRequest(final Request request, final  BaseCallback callback){

        callback.onBeforeRequest(request);

        // 请求 enqueue异步方法
        mHttpClient.newCall(request).enqueue(new Callback() {

            // 请求服务器失败
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request,e);

            }
            // 请求服务器成功
            @Override
            public void onResponse(Response response) throws IOException {

                callback.onResponse(response);
                // 响应成功
                if(response.isSuccessful()) {
                    // 获取到结果
                    String resultStr = response.body().string();
                    Log.d(TAG, "result=" + resultStr);

                    if (callback.mType == String.class){
                        callbackSuccess(callback,response,resultStr);
                    }
                    else {
                        try {
                            // Json 解析
                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback,response,obj);
                        }
                        catch (com.google.gson.JsonParseException e){ // Json解析的错误
                            callback.onError(response,response.code(),e);
                        }
                    }
                }
                else {
                    callbackError(callback,response,null);
                }

            }
        });


    }


    /**
     * 在主线程中执行的回调
     *
     * @param response
     * @param resString
     * @param callback
     */

    private void callbackSuccess(final  BaseCallback callback , final Response response, final Object obj ){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }


    private void callbackError(final  BaseCallback callback , final Response response, final Exception e ){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }



    private  Request buildPostRequest(String url,Map<String,String> params){

        return  buildRequest(url,HttpMethodType.POST,params);
    }

    private  Request buildGetRequest(String url){

        return  buildRequest(url,HttpMethodType.GET,null);
    }

    private  Request buildRequest(String url,HttpMethodType methodType,Map<String,String> params){


        Request.Builder builder = new Request.Builder()
                .url(url);

        // 判断方法类型
        if (methodType == HttpMethodType.POST){
            RequestBody body = builderFormData(params);
            builder.post(body);
        }
        else if(methodType == HttpMethodType.GET){
            builder.get();
        }

        // return的结果就是request对象
        return builder.build();
    }


    // 添加post方法所需要的参数
    private RequestBody builderFormData(Map<String,String> params){


        FormEncodingBuilder builder = new FormEncodingBuilder();

        if(params !=null){

            for (Map.Entry<String,String> entry :params.entrySet() ){

                builder.add(entry.getKey(),entry.getValue());
            }
        }

        return  builder.build();

    }

	/**
     * 这个枚举用于指明是哪一种提交方式
     */
    enum  HttpMethodType{
        GET,
        POST,
    }

}
```

```java
/**
 * 基本的回调
 */
public abstract class BaseCallback<T> {


    /**
     * type用于方便JSON的解析
     */
    public Type mType;

    /**
     * 把type转换成对应的类，这里不用看明白也行。
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 构造的时候获得type的class
     */
    public BaseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 请求之前调用
     */
    public abstract void onRequestBefore();

    /**
     * 请求失败调用（网络问题）
     *
     * @param request
     * @param e
     */
    public abstract void onFailure(Request request, Exception e);

    /**
     * 请求成功而且没有错误的时候调用
     *
     * @param response
     * @param t
     */
    public abstract void onSuccess(Response response, T t);

    /**
     * 请求成功但是有错误的时候调用，例如Gson解析错误等
     *
     * @param response
     * @param errorCode
     * @param e
     */
    public abstract void onError(Response response, int errorCode, Exception e);

}

```

## 五、OKHttp封装之后的使用

如下面的代码所示。首先得到OkHttpHelper的单例，然后调用get方法就可以了。由于继承了Gson，因此需要在BaseCallback的泛型中传入JSON对应的数据类型，这里是List<Banner>。最后在onSuccess方法中做我们想要做的事情就可以了。

```java
mHttpHelper=OkHttpHelper.getinstance();
mHttpHelper.get(Constants.URL_BANNER, new BaseCallback<List<Banner>>() {

    @Override
    public void onRequestBefore() {

    }

    @Override
    public void onFailure(Request request, Exception e) {

    }

    @Override
    public void onSuccess(Response response, List<Banner> banners) {
        initBanners(banners);
    }

    @Override
    public void onError(Response response, int errorCode, Exception e) {

    }
});

```

