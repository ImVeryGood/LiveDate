package com.m.livedate.mvvm.basic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.m.livedate.databinding.ImageLayoutBinding;
import com.m.livedate.databinding.RecyclerItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * createDate:2020/7/15
 *
 * @author:spc describe：baseAdapter
 */
public class MultiDBRVAdapter<Data> extends RecyclerView.Adapter<BaseDBRVHolder> {
    private List<Data> dataList;
    private int[] itemIds;
    protected Context mContext;
    protected int[] variableIds;
    protected OnItemClickListener<Data> listener;
    protected int type;
    protected RecyclerItemBinding recyclerItemBinding;
    protected ImageLayoutBinding imageLayoutBinding;

    public MultiDBRVAdapter(int[] itemId, int[] variableIds) {
        this.itemIds = itemId;
        this.variableIds = variableIds;
        dataList = new ArrayList<>();
    }

    public MultiDBRVAdapter(List<Data> data, int[] variableIds, int[] items) {
        this.dataList = data == null ? new ArrayList<>() : data;
        this.variableIds = variableIds;
        this.itemIds = items;
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
        switch (viewType) {
            case 0:
                imageLayoutBinding = DataBindingUtil.inflate(inflater, itemIds[0], parent, false);
                return new BaseDBRVHolder(imageLayoutBinding.getRoot());
            default:
                recyclerItemBinding = DataBindingUtil.inflate(inflater, itemIds[1], parent, false);
                return new BaseDBRVHolder(recyclerItemBinding.getRoot());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            type = 0;
        } else {
            type = 1;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseDBRVHolder holder, int position) {
        Data  itemData = dataList.get(position);
        if (position == 0) {
            int variableId = variableIds[0];
            imageLayoutBinding = DataBindingUtil.getBinding(holder.itemView);
            imageLayoutBinding.setVariable(variableId, itemData);
//        迫使数据立即绑定
            imageLayoutBinding.executePendingBindings();
        } else {
            int variableId = variableIds[0];
            recyclerItemBinding = DataBindingUtil.getBinding(holder.itemView);
            recyclerItemBinding.setVariable(variableId, itemData);
//        迫使数据立即绑定
            recyclerItemBinding.executePendingBindings();
        }

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
     * 设置新数据
     *
     * @param data
     */
    public void setNewData(List<Data> data) {
        if (this.dataList != null && data != null) {
            this.dataList.clear();
            this.dataList.addAll(data);
            notifyDataSetChanged();
        }

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
