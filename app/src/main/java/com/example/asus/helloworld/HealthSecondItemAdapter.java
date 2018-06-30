package com.example.asus.helloworld;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class HealthSecondItemAdapter extends RecyclerView.Adapter<HealthSecondItemAdapter.ViewHolder> {
    private Context mContext;
    private List<HealthSecondItem> mHealthSecondItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView HealthSecondItemImg;
        TextView title;
        TextView content;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            HealthSecondItemImg = (ImageView) view.findViewById(R.id.health_second_item_img);
            title = (TextView) view.findViewById(R.id.health_second_item_title);
            content = (TextView) view.findViewById(R.id.health_second_item_content);
        }
    }

    public HealthSecondItemAdapter(List<HealthSecondItem> HealthSecondItemList) {
        mHealthSecondItemList = HealthSecondItemList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.each_health_second_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HealthSecondItem HealthSecondItem = mHealthSecondItemList.get(position);
        holder.title.setText(HealthSecondItem.getTitle());
        holder.content.setText(HealthSecondItem.getContent());
        Log.i("matcher",HealthSecondItem.getImgUrl());
        Glide.with(mContext).load(HealthSecondItem.getImgUrl()).into(holder.HealthSecondItemImg);
        //如果对于HealthSecondItem中控件的监听，可以在此处实现
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, HealthSecondItem.getEssayUrl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("seeMore",HealthSecondItem.getEssayUrl());
                intent.setClass(mContext,EssayDetail.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHealthSecondItemList.size();
    }
}
