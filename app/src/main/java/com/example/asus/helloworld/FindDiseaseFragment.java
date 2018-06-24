package com.example.asus.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * Created by ASUS on 2018/6/22.
 */

public class FindDiseaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.find_disease_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.findByNet);
        final EditText editText = (EditText) getActivity().findViewById(R.id.editor_content);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if(content==null||"".equals(content)){
                    Toast.makeText(getContext(),"输入不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "搜索中...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(getContext(), FindByNetwork.class);
                    intent.putExtra("EditorContent", content);
                    startActivity(intent);
                }
            }
        });
    }
}
