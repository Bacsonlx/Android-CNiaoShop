package hznu.linxin.cniaoshop_02;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import hznu.linxin.cniaoshop_02.R;

/**
 * @author: BacSon
 * data: 2021/3/5
 */

/**
 *  原生ToolBar
 */
public class TestActvity extends AppCompatActivity {
    private Toolbar mToolBar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mToolBar = (Toolbar) this.findViewById(R.id.toolbar);
        // 左边Navigation图标的点击事件
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActvity.this,"Navigation Clicked",Toast.LENGTH_LONG).show();
            }
        });


        // 绑定Menu
        mToolBar.inflateMenu(R.menu.menu_main);
        // menu菜单的点击事件
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 获取menu的id
                int id = item.getItemId();
                if(id == R.id.action_settings){
                    Toast.makeText(TestActvity.this,"action_settings Clicked",Toast.LENGTH_LONG).show();
                    return  true;
                }
                return false;
            }
        });
    }
}
