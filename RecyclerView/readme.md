### RecyclerView的使用

博客地址：https://blog.csdn.net/weixin_43750332/article/details/114537301

实现效果：

<img src="C:\Users\11609\Pictures\Temp\4-1.jpg" alt="4-1" style="zoom: 25%;" /><img src="C:\Users\11609\Pictures\Temp\4-2.jpg" alt="4-2" style="zoom:25%;" />

## 一、 Recycler的简介

RecyclerView是一种新的视图组，目标是为任何基于适配器的视图提供相似的渲染方式。该控件用于在有限的窗口中展示大量数据集，它被**作为ListView和GridView控件的继承者**。

​    那么有了ListView、GridView为什么还需要RecyclerView这样的控件呢？整体上看RecyclerView架构，提供了一种插拔式的体验，**高度解耦**，异常的灵活，通过设置它提供的不同LayoutManager，ItemDecoration , ItemAnimator实现令人瞠目的效果。

1. **Adapter**：适配器，绑定数据集 
2. **ViewHolder**：根据当前的数据保存视图 
3. **LayoutManager**：布局管理器。决定item如何摆放 
4. **ItemDecoration**：勉强理解为item装饰器，可以美化item
5. **ItemAnimator**：动画（当item被增加，删除，重新摆放时动画才有效）。
6.  **Listener**： 事件。RecyclerView 本身不提供OnItemClickListener 等事件

## 二、 RecyclerView的使用

1. ### 添加依赖库：

   ```xml
   implementation 'com.android.support:recyclerview-v7:23.0.1'
   ```

2. ### 在布局文件中加入RecycleView

   ```xml
   <androidx.recyclerview.widget.RecyclerView
           android:id="@+id/recyclerView"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           >
   </androidx.recyclerview.widget.RecyclerView>
   ```

3. ### 创建适配器类继承自RecycleView.Adpter，步骤如下：

   - **继承RecyclerView.Adapter**，并且在Adapter里面声明ViewHolder类继承RecyclerView.ViewHolder，最后把自己的ViewHolder类丢进自己的Adapter类的泛型中去。

   - 在自定义ViewHolder类的构造方法中可以通过ID找到布局的控件，控件需要声明为自定义ViewHolder类的成员变量。

   - 实现RecyclerView.Adapter的所有未实现的函数，onCreateViewHolder主要负责加载布局（加载的时候注意要把父布局写到参数里去），创建自定义ViewHolder类的对象；onBindViewHolder主要负责把数据设置到Item的控件中；getItemCount主要负责得到数据的数目。

   - 最好把数据声明为成员变量，在构造函数里面传进来。

     由于RecycleView原生不支持点击事件，需要自己添加接口进行回调。

   代码如下：

   ```java
   /**
    * 适配器：继承 RecyclerView.Adapter 需要一个泛型 ViewHolder
    *
    */
   public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
   
       private List<String> mDatas;
       private LayoutInflater inflater;
       private  OnItemClickListener listener;
   
       // 定义构造方法将数据传递进来
       public MyAdapter(List<String> datas){
           mDatas = datas;
       }
   
   
       @NonNull
       @Override
       public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   
           inflater = LayoutInflater.from(parent.getContext());
           // 读取item布局文件
           View view = inflater.inflate(R.layout.item, parent, false);
           // ViewHolder 需要一个参数view -> item
           return new ViewHolder(view);
       }
   
       // 绑定数据
       @Override
       public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
   
           holder.textView.setText(mDatas.get(position));
       }
   
       // 添加数据
       public  void addData(int position,String city){
           mDatas.add(position,city);
           // 通知Item要插入数据
           notifyItemInserted(position);
       }
       // 删除数据
       public void removeData(int position){
           mDatas.remove(position);
           // 通知Item要删除数据
           notifyItemRemoved(position);
       }
   
       @Override
       public int getItemCount() {
           return mDatas.size();
       }
       
       // 设置事件监听器
   	public void setOnItemClickListener(OnItemClickListener listener){
           this.listener = listener;
       }
       // 一定要继承 RecyclerView.ViewHolder, 相当于getView方法
       class ViewHolder extends RecyclerView.ViewHolder {
           // 绑定item中的控件
           private TextView textView;
           public ViewHolder(@NonNull View itemView) {
               super(itemView);
   
               textView = (TextView) itemView.findViewById(R.id.text);
               // textView的点击事件
               textView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(listener !=null){
                           listener.onClick(v, getLayoutPosition(), mDatas.get(getLayoutPosition()));
                       }
                   }
               });
   
               // 如果还需要添加其他控件的点击事件 一样添加setOnClickListener即可
               // 例如 btn.setOnClickListener...
           }
       }
   
       // Recyclerview 没有所谓的OnItemClick方法 所以需要自己实现事件监听
       interface  OnItemClickListener{
           void onClick(View v,int position,String city);
   
       }
   }
   
   ```

   ### 4、下面是MainActivity中需要进行的处理。核心的步骤（某些步骤可不分先后）如下：

   - 通过ID找到RecyclerView。

   - RecyclerView设置Adapter。

   - RecyclerView设置布局管理器，常用的布局管理器有

     ① **LinearLayoutManager**：线性布局，LayoutManager的实现类，类型包括Vertical和Horizontal

     ② **GridLayoutManager**：格子布局，继承自LinearLayoutManager，实现效果类似GridView；

     ③ **StaggeredGridLayoutManager：**交错的格子布局，同样也是LayoutManager的实现类，类型包括Vertical和Horizontal，与GridLayoutManager很相似，不过是交错的格子，也就是宽高不等的格子视图，类似瀑布流的效果。

   - RecyclerView设置数据插入、删除的时候的动画效果，这里使用默认的。下面的网址是GitHub上面的开源动画效果：https://github.com/gabrielemariotti/RecyclerViewItemAnimators

   - RecyclerView设置Item之间的装饰器。

代码如下：

```java
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
```

