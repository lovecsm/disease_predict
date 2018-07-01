package com.example.asus.helloworld;

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

public class PredictFragment extends Fragment {
    private Item[] items = {new Item("胃部","对胃部疾病的预测",R.drawable.ic_stomach), new Item("心血管",
            "心血管方面的疾病预测",R.drawable.ic_heart_blood),
            new Item("肝脏", "肝脏我们都保护好了吗",R.drawable.ic_liver), new Item("肛肠","是不是肚子突然疼痛呢", R.
            drawable.ic_intestine),
            new Item("五官科","要保护好我们的五官哦", R.drawable.ic_five_organs), new Item("口腔","你的私人口腔医生", R.drawable.
            ic_mouth), new Item("骨科","老年人骨质疏松怎么办", R.drawable.ic_bone), new Item("皮肤","一定要防止皮肤疾病的入侵", R.drawable.
            ic_skin), new Item("神经科","神经衰弱也属于亚健康状态哦", R.drawable.ic_spirit), new Item("泌尿系统","轻松排毒从预测做起", R.
            drawable.ic_urinary)};

    private List<Item> itemList = new ArrayList<>();
    private PredictItemAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.predict_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initItems();
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.predict_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PredictItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }
    private void initItems() {
        itemList.clear();
        for (int i = 0; i < items.length; i++) {
            itemList.add(items[i]);
        }
    }
}
