package com.m.livedate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.m.livedate.retrofit.ApiResponse;
import com.m.livedate.retrofit.ListBean;
import com.m.livedate.retrofit.RetrofitManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MViewModel mViewModel;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.txt);
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MViewModel.class);
        mViewModel.getNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText(String.valueOf(integer));
            }
        });
    }

    public void add(View view) {
        mViewModel.doSet(1);
        getData();
    }

    public void reduce(View view) {
        mViewModel.doSet(-1);
        textView.setText(mViewModel.getNumber().getValue() + "");

    }

    public void getData() {
        new RetrofitManager().getApiService().getListBeanData().observe(this, new Observer<ApiResponse<List<ListBean.DataBean>>>() {
            @Override
            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
                Log.d("SSSSSSSSSSSSSSSS", "onChanged: "+new Gson().toJson(listApiResponse));
            }
        });
    }
}
