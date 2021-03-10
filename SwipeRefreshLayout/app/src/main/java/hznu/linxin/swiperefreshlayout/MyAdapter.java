package hznu.linxin.swiperefreshlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author: BacSon
 * data: 2021/3/11
 */
class MyAdatper extends RecyclerView.Adapter<MyAdatper.ViewHolder> {



    private List<String> mDatas;


    private LayoutInflater inflater;

    private  OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;
    }


    public MyAdatper(List<String> datas){

        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.textView.setText(mDatas.get(position));

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    public  void addData(int position,String city){

        mDatas.add(position,city);

//        notifyItemChanged(position);

        notifyItemInserted(position);

    }



    public void removeData(int position){

        mDatas.remove(position);

        notifyItemRemoved(position);
    }


    public void notifyItemsChange(int start,int itemCount){

        notifyItemRangeChanged(start,itemCount);

    }


    class  ViewHolder extends  RecyclerView.ViewHolder{


        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener !=null){
                        listener.onClick(v,getLayoutPosition(),mDatas.get(getLayoutPosition()));
                    }

                }
            });


        }
    }



    interface  OnItemClickListener{


        void onClick(View v, int position, String city);


    }




}
