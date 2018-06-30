package com.example.asus.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class HealthKnowledge extends Fragment {
    private Item[] items = {new Item("健康速递","食品安全  健康速递",R.drawable.health_deliver), new Item("营养科普",
            "营养科普  养生食谱",R.drawable.health_nutrition),
            new Item("人群养生", "男性  女性",R.drawable.health_people), new Item("运动常识","运动常识  有氧瑜伽", R.
            drawable.health_sport),
            new Item("心灵氧吧","心理百科  心灵氧吧", R.drawable.health_heart), new Item("中医理论","体质养生  两性养生", R.drawable.
            health_chinese)};

    private List<Item> itemList = new ArrayList<>();
    private ItemAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.health_knowledge_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initItems();
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }
    private void initItems() {
        itemList.clear();
        for (int i = 0; i < items.length; i++) {
            itemList.add(items[i]);
        }
    }
}
