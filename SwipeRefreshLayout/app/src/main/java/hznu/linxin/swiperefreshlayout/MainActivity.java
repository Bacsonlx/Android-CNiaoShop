package hznu.linxin.swiperefreshlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mlayout;
    private List<String> datas = new ArrayList<>();
    private  MyAdatper mAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        mlayout = (SwipeRefreshLayout) this.findViewById(R.id.refreshlayout);

        initDatas();
        initRecyclerView();
        initRefreshLayout();
    }



    @SuppressLint("ResourceType")
    private void  initRefreshLayout(){

        // 设置刷新的颜色
        mlayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // 设置下拉多少距离 开始进行下拉刷新
        mlayout.setDistanceToTriggerSync(100);

        //设置进度条的背景颜色
//        mlayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.item_press));

        // 设置大小
        mlayout.setSize(SwipeRefreshLayout.LARGE);

        // 设置刷新的时候监听，三秒钟之后添加数据完毕
        mlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 添加数据
                        for (int i=0; i<=10;i++){
                            mAdatper.addData(i,"new City:"+i);
                        }
                        mAdatper.notifyItemRangeChanged(0,10);
                        // 滑到item为0的数据的位置
                        mRecyclerView.scrollToPosition(0);
                        //刷新完毕，关闭下拉刷新的组件
                        mlayout.setRefreshing(false);
                        //判断是否在刷新
//                        mlayout.isRefreshing()//isRefreshing

                    }
                },3000); // 刷新时间

            }
        });

    }


    private void initRecyclerView(){

        mAdatper = new MyAdatper(datas);
        mRecyclerView.setAdapter(mAdatper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mAdatper.setOnItemClickListener(new MyAdatper.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String city) {

                Toast.makeText(MainActivity.this, "city:" + city + ",position:" + position, Toast.LENGTH_LONG).show();
            }
        });
    }



    private  void initDatas(){

        datas.add("New York");
        datas.add("Boston");
        datas.add("Washington");
        datas.add("San Francisco");
        datas.add("California");
        datas.add("Chicago");
        datas.add("Houston");
        datas.add("Phoenix");
        datas.add("Philadelphia");
        datas.add("Pennsylvania");
        datas.add("San Antonio");
        datas.add("Austin");
        datas.add("Milwaukee");
        datas.add("Las Vegas");
        datas.add("Oklahoma");
        datas.add("Portland");
        datas.add("Mexico");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}