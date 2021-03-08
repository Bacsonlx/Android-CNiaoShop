package hznu.linxin.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> datas = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        // 新建一个Adapter
        mAdapter = new MyAdapter(datas);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // setAdapter 设置适配器
        mRecyclerView.setAdapter(mAdapter);
        // setLayoutManager 设置RecyclerView的形式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // addItemDecoration 相当于装饰器 可以实现分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        // 设置添加和删除数据时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 实现item的点击事件
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            // 这里重写了在adapter里定义的onClick方法 具体的实现了点击事件
            @Override
            public void onClick(View v, int position, String city) {
                Toast.makeText(MainActivity.this,"city:"+city+",position:"+position,Toast.LENGTH_LONG).show();
            }
        });

    }

    // 初始化数据
    private void initDatas() {
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
        // 获取menu的布局
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.id_action_add:
                // 添加数据
                mAdapter.addData(0,"new Citiy");
                break;
            case R.id.id_action_delete:
                // 删除数据
                mAdapter.removeData(1);
                break;
            case R.id.id_action_gridview:

                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.id_action_horizontalGridView:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;

        }
        return true;
    }
}