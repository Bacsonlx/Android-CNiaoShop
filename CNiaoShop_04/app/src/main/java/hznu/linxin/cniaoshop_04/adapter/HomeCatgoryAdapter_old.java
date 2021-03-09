package hznu.linxin.cniaoshop_04.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hznu.linxin.cniaoshop_04.R;
import hznu.linxin.cniaoshop_04.bean.HomeCategory;

/**
 * @author: BacSon
 * data: 2021/3/8
 */
public class HomeCatgoryAdapter_old extends RecyclerView.Adapter<HomeCatgoryAdapter_old.ViewHolder> {

    // 控制奇偶
    private  static int VIEW_TYPE_L=0;
    private  static int VIEW_TYPE_R=1;

    private LayoutInflater mInflater;
    private List<HomeCategory> mDatas;

    public HomeCatgoryAdapter_old(List<HomeCategory> datas){
        mDatas = datas;
    }

    /**
     *  Item的布局类型
     * @param viewGroup
     * @param type
     * @return
     */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        mInflater = LayoutInflater.from(viewGroup.getContext());

        if(type == VIEW_TYPE_R){
            return  new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2,viewGroup,false));
        }

        return  new ViewHolder(mInflater.inflate(R.layout.template_home_cardview,viewGroup,false));
    }

    /**
     * 绑定数据
     * @param viewHolder
     * @param i item位置
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        HomeCategory category = mDatas.get(i);
        viewHolder.textTitle.setText(category.getName());
        viewHolder.imageViewBig.setImageResource(category.getImgBig());
        viewHolder.imageViewSmallTop.setImageResource(category.getImgSmallTop());
        viewHolder.imageViewSmallBottom.setImageResource(category.getImgSmallBottom());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     *  返回奇偶
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if(position % 2==0){
            return  VIEW_TYPE_R;
        }
        else return VIEW_TYPE_L;


    }

    // 将所有item中的控件
    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
        }

    }
}

