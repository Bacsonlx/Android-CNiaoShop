package hznu.linxin.cniaoshop_03.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import hznu.linxin.cniaoshop_03.R;


/**
 * @author BacSon
 */

/**
 *  使用SliderLayout实现轮播图
 */
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
