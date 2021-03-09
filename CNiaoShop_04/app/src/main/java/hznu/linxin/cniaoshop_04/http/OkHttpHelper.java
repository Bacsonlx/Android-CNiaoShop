package hznu.linxin.cniaoshop_04.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: BacSon
 * data: 2021/3/9
 */
public class OkHttpHelper {

    public static final String TAG="OkHttpHelper";
    private  static  OkHttpHelper mInstance;

    private OkHttpClient mHttpClient;
    private Gson mGson;
    private Handler mHandler;


    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper(){

        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30,TimeUnit.SECONDS);

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());

    };

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
//                    获取到结果
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


    // 线程通过Handler来改变UI
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



    enum  HttpMethodType{
        GET,
        POST,
    }

}
