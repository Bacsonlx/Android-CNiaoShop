package hznu.linxin.cniaoshop_04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hznu.linxin.cniaoshop_04.R;
import hznu.linxin.cniaoshop_04.bean.Campaign;
import hznu.linxin.cniaoshop_04.bean.HomeCampaign;
import hznu.linxin.cniaoshop_04.bean.HomeCategory;

/**
 * @author: BacSon
 * data: 2021/3/8
 */
public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {

    // 控制奇偶
    private  static int VIEW_TYPE_L=0;
    private  static int VIEW_TYPE_R=1;

    private LayoutInflater mInflater;
    private List<HomeCampaign> mDatas;

    private Context mContext;
    private  OnCampaignClickListener mListener;

    public HomeCatgoryAdapter(List<HomeCampaign> datas, Context context){
        mDatas = datas;
        this.mContext = context;
    }

    /**
     * 设置图片的事件监听器
     * @param listener
     */
    public void setOnCampaignClickListener(OnCampaignClickListener listener){
        this.mListener = listener;
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


        HomeCampaign homeCampaign = mDatas.get(i);
        // 获得标题
        viewHolder.textTitle.setText(homeCampaign.getTitle());
        // 获得图片 需要将图片下载下来进行缓存下来
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(viewHolder.imageViewBig);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(viewHolder.imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(viewHolder.imageViewSmallBottom);


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
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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


            // 绑定图片点击事件
            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        // 真正判断点击哪张图片
        @Override
        public void onClick(View v) {

            HomeCampaign homeCampaign = mDatas.get(getLayoutPosition());
            if(mListener !=null){

                switch (v.getId()){

                    case  R.id.imgview_big:
                        mListener.onClick(v,homeCampaign.getCpOne());
                        break;

                    case  R.id.imgview_small_top:
                        mListener.onClick(v,homeCampaign.getCpTwo());
                        break;

                    case  R.id.imgview_small_bottom:
                        mListener.onClick(v,homeCampaign.getCpThree());
                        break;

                }
            }


        }
    }

    public  interface OnCampaignClickListener{
        // 第二个参数表示在同一个View内 点击的是哪张图片(三张图遍历判断)
        void onClick(View view, Campaign campaign);
    }
}

