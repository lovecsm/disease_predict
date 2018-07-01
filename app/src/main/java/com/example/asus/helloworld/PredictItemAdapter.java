package com.example.asus.helloworld;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;


public class PredictItemAdapter extends RecyclerView.Adapter<PredictItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView itemImg;
        TextView title;
        TextView subtitle;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            itemImg = (ImageView) view.findViewById(R.id.predict_item_icon);
            title = (TextView) view.findViewById(R.id.predict_item_title);
            subtitle = (TextView) view.findViewById(R.id.predict_item_subtitle);
        }
    }

    public PredictItemAdapter(List<Item> itemList) {
        mItemList = itemList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.predict_each_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = mItemList.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getContent());
        Glide.with(mContext).load(item.getImgId()).into(holder.itemImg);
        //对于Item中控件的监听
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setClass(mContext,HealthSecondActivity.class);
                intent.putExtra("health",item.getTitle());
                mContext.startActivity(intent);*/
                switch (item.getTitle()){
                    case "胃部":
                        Intent intent = new Intent();
                        intent.setClass(mContext,PredictActivity.class);
                        intent.putExtra("disease","胃部");
                        mContext.startActivity(intent);
                        break;
                    default:
                        Toast.makeText(mContext,"该功能暂未实现，试试预测胃部疾病吧",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
