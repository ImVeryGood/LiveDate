package com.m.livedate.basic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * createDate:2020/7/15
 *
 * @author:spc describe：baseAdapter
 */
public class BaseDBRVAdapter<Data, DB extends ViewDataBinding> extends RecyclerView.Adapter<BaseDBRVHolder> {
    private List<Data> dataList;
    private int itemId;
    protected Context mContext;
    protected int variableId;
    protected OnItemClickListener<Data> listener;

    public BaseDBRVAdapter(int itemId, int variableId) {
        this.itemId = itemId;
        this.variableId = variableId;
        dataList = new ArrayList<>();
    }

    public BaseDBRVAdapter(List<Data> data, int variableId, int itemId) {
        this.dataList = data == null ? new ArrayList<>() : data;
        this.variableId = variableId;
        this.itemId = itemId;
    }

    /**
     * binding.getRoot()==view
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BaseDBRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        DB binding = DataBindingUtil.inflate(inflater, itemId, parent, false);
        return new BaseDBRVHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseDBRVHolder holder, int position) {
        DB binding = DataBindingUtil.getBinding(holder.itemView);
        Data itemData = dataList.get(position);
        binding.setVariable(variableId, itemData);
        onBindViewHolder(itemData, binding, position);
//        迫使数据立即绑定
        binding.executePendingBindings();
//        设置点击事件
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(itemData, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return listener.onItemLongClick(itemData, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 绑定数据
     */
    protected void onBindViewHolder(Data data, DB binding, int position) {
    }
    /**
     * 设置新数据
     *
     * @param data
     */
    public void setNewData(List<Data> data) {
        this.dataList.clear();
        this.dataList.addAll(data);
        Log.d("SSSSSSSSSS", "addData: "+new Gson().toJson(this.dataList));
        notifyDataSetChanged();
    }
    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(Data data) {
        this.dataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(List<Data> data) {
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 设置Item 长按、点击事件
     */
    public void setOnItemListener(OnItemClickListener<Data> listener) {
        this.listener = listener;
    }
}
