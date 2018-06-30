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

/**
 * Created by ASUS on 2018/6/26.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView itemImg;
        TextView title;
        TextView content;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            itemImg = (ImageView) view.findViewById(R.id.item_img);
            title = (TextView) view.findViewById(R.id.item_title);
            content = (TextView) view.findViewById(R.id.item_content);
        }
    }

    public ItemAdapter(List<Item> itemList) {
        mItemList = itemList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.each_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = mItemList.get(position);
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
        Glide.with(mContext).load(item.getImgId()).into(holder.itemImg);
        //如果对于Item中控件的监听，可以在此处实现
            holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,HealthSecondActivity.class);
                intent.putExtra("health",item.getTitle());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
