package hznu.linxin.cniaoshop_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hznu.linxin.cniaoshop_03.bean.Tab;
import hznu.linxin.cniaoshop_03.fragment.CartFragment;
import hznu.linxin.cniaoshop_03.fragment.CategoryFragment;
import hznu.linxin.cniaoshop_03.fragment.HomeFragment;
import hznu.linxin.cniaoshop_03.fragment.HotFragment;
import hznu.linxin.cniaoshop_03.fragment.MineFragment;
import hznu.linxin.cniaoshop_03.widget.FragmentTabHost;

/**
 * @author: BacSon
 * data: 2021/3/4
 */
public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;

    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList<Tab>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *  优化前： 单独添加 需要重复五遍相似的代码
         */
        /*
        mInflater = LayoutInflater.from(this);

        mTabHost = this.findViewById(R.id.tabhost);
        //调用FragmentTabHost的setup()方法，设置FragmentManager，以及指定用于装载Fragment的布局容器。
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 创建一个TabSpec添加主页
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("Home");

        // tab_indicator 底部的导航 包含一张图片和文字
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        // 设置对应图标文字
        img.setImageResource(R.mipmap.icon_home);
        text.setText("主页");
        // 将view添加到tabSpec中
        tabSpec.setIndicator(view);

        mTabHost.addTab(tabSpec, HomeFragment.class, null);*/

        /**
         *  优化思路： 只有对应的图标 文字 和 Fragment 是动态变化的 可以封装成一个类(Tab)
         *  再使用一个for循环遍历所有的底部导航栏
         */
        initTab();

    }

    private void initTab() {
        // 将这五个Tab放在一个List里就能够用for循环实现动态添加每一个图标文字和Fragment
        // 注意： 这里的图标采用的是selector的样式 实现选中时图标的更换
        Tab tab_home = new Tab(HomeFragment.class, R.string.home, R.drawable.selector_icon_home);
        Tab tab_hot = new Tab(HotFragment.class,R.string.hot,R.drawable.selector_icon_hot);
        Tab tab_category = new Tab(CategoryFragment.class,R.string.catagory,R.drawable.selector_icon_category);
        Tab tab_cart = new Tab(CartFragment.class,R.string.cart,R.drawable.selector_icon_cart);
        Tab tab_mine = new Tab(MineFragment.class,R.string.mine,R.drawable.selector_icon_mine);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);

        mTabHost = this.findViewById(R.id.tabhost);
        //调用FragmentTabHost的setup()方法，设置FragmentManager，以及指定用于装载Fragment的布局容器。
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);


        for (Tab tab : mTabs) {
            // 创建一个TabSpec添加Indicator
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));

            // 将view添加到tabSpec中
            tabSpec.setIndicator(buildIndicator(tab));

            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        // 去掉分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        // 默认选择第一个
        mTabHost.setCurrentTab(0);

    }
    // 创建指示器 Indicator
    private View buildIndicator(Tab tab) {
        // Indicator 底部导航的指示器 包含一张图片和文字
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        // 设置对应图标文字
        img.setImageResource(tab.getIcon());
        text.setText(tab.getTitle());

        return view;
    }
}