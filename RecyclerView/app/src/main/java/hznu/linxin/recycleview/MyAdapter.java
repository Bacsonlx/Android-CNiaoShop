package hznu.linxin.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @author: BacSon
 * data: 2021/3/8
 */

/**
 * 适配器：继承 RecyclerView.Adapter 需要一个泛型 ViewHolder
 *
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mDatas;
    private LayoutInflater inflater;
    private  OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

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

    // Recyclerview 没有OnItemClick方法 所以需要自己定义
    interface  OnItemClickListener{
        void onClick(View v,int position,String city);

    }
}
