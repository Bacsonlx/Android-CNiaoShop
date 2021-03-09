package hznu.linxin.cniaoshop_04.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hznu.linxin.cniaoshop_04.Contants;
import hznu.linxin.cniaoshop_04.R;
import hznu.linxin.cniaoshop_04.adapter.DividerItemDecortion;
import hznu.linxin.cniaoshop_04.adapter.HomeCatgoryAdapter;
import hznu.linxin.cniaoshop_04.bean.Banner;
import hznu.linxin.cniaoshop_04.bean.Campaign;
import hznu.linxin.cniaoshop_04.bean.HomeCampaign;
import hznu.linxin.cniaoshop_04.bean.HomeCategory;
import hznu.linxin.cniaoshop_04.http.BaseCallback;
import hznu.linxin.cniaoshop_04.http.OkHttpHelper;
import hznu.linxin.cniaoshop_04.http.SpotsCallBack;


/**
 * @author BacSon
 */

/**
 *  使用SliderLayout实现轮播图
 */
public class HomeFragment extends Fragment {

    private static  final  String TAG="HomeFragment";
    private SliderLayout sliderLayout;
    private PagerIndicator mIndicator;
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;
    // 接受的Gson数据
    private Gson mGson = new Gson();
    // 接受到的数据用List存储
    private List<Banner> mBanner;
    // 获取okHttpHelper实例
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);

        // initSlider();
        requestImages();

        initRecyclerView(view);
        return  view;
    }

    private void requestImages() {
        // Get方式的URl
         String url ="http://112.124.22.238:8081/course_api/banner/query?type=1";
        // Post 方式的URL
//        String url ="http://112.124.22.238:8081/course_api/banner/query";

        /**
         * ******************okHttp 封装前************
         */
//        // MediaType 数据类型
//        OkHttpClient client = new OkHttpClient();
//
//        *//**
//         *  Get 方式
//         *//*
//        *//*Request request = new Request.Builder()
//                .url(url)
//                .build();
//        *//*
//        *//**
//         * Post 方法
//         *//*
//        // RequestBody 请求数据
//        // FormEncodingBuilder 表单构造器
//        RequestBody body = new FormEncodingBuilder()
//                .add("type","1") // 参数通过add添加
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//
//        // execute() 方法是同步的  enqueue() 方法是异步的
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                // 成功获取到了json数据
//                String json = response.body().string();
//                // Type 是fromJson的第二个参数 表示将json数据转化成的类型
//                // Type可以是任何类型 这里是将Json数据转化为List<Banner>类型
//                Type type  =new TypeToken<List<Banner>>(){}.getType();
//                mBanner = mGson.fromJson(json, type);
//                // 获取完数据后初始化轮播图
//                initSlider();
//            }
//        });*/
        /***************************************************/

        /********************封装okHttp******************
         *  代码更加简洁
         */
        httpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()){

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanner = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });



    }

    private void initRecyclerView(View view) {

        /**
         *  ******************* 使用本地资源 **************************
         */
        /*mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        List<HomeCategory> datas = new ArrayList<>(15);

        HomeCategory category = new HomeCategory("热门活动",R.drawable.img_big_1,R.drawable.img_1_small1,R.drawable.img_1_small2);
        datas.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        datas.add(category);
        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        datas.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        datas.add(category);

        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        datas.add(category);


        mAdatper = new HomeCatgoryAdapter(datas);

        mRecyclerView.setAdapter(mAdatper);

        mRecyclerView.addItemDecoration(new DividerItemDecortion());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));*/

        /**
         * ******************* 从服务端获取数据  **************************
         */

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initData(homeCampaigns);
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private  void initData(List<HomeCampaign> homeCampaigns){


        mAdatper = new HomeCatgoryAdapter(homeCampaigns, getActivity());

        mAdatper.setOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {

                Toast.makeText(getContext(),"title="+campaign.getTitle(),Toast.LENGTH_LONG).show();
            }
        });

        mRecyclerView.setAdapter(mAdatper);

        mRecyclerView.addItemDecoration(new DividerItemDecortion());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }


    // 初始化Slider
    private void initSlider() {

        if (mBanner != null) {
            // 遍历List把所有的数据取出来
            for (Banner banner : mBanner ) {
                // 添加图片和描述
                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView
                        .description(banner.getName())
                        .image(banner.getImgUrl());
                // 设置图片缩放
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                // 在sliderLayout里设置图片
                sliderLayout.addSlider(textSliderView);


                // 为图片添加点击事件
                /*textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {

                    }
                });*/
            }
        }

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
                Log.d("HomeFragment", "onPageScrolled: ");

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("HomeFragment", "onPageSelected: ");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("HomeFragment", "onPageScrollStateChanged: ");

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止轮播图
        sliderLayout.stopAutoCycle();
    }
}
