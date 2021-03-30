package com.blefsu.sdk.sample;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanghaojie on 2017/4/20.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<String> mDatas;

    public DataAdapter() {
        this.mDatas = new ArrayList<>();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DataViewHolder holder = new DataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_data, parent, false));

        return holder;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void addItems(String data) {
        if (data.isEmpty())
            return;
        this.mDatas.add(data);
        this.notifyItemRangeInserted(mDatas.size(), 1);
    }

    public void clean() {
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.mTxtAdapterData.setText(mDatas.get(position));
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtAdapterData;

        public DataViewHolder(View view) {
            super(view);
            mTxtAdapterData = (TextView) view.findViewById(R.id.txt_adapter_data);

        }
    }

}
