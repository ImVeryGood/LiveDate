package com.blefsu.sdk.sample;

import android.bluetooth.BluetoothDevice;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanghaojie on 2017/4/17.
 */

public class BleAdapter extends RecyclerView.Adapter<BleAdapter.BleViewHolder> {
    private List<BluetoothDevice> mDatas;
    private OnClickButtonListener onClickButtonListener;

    public BleAdapter(OnClickButtonListener onClickButtonListener) {
        this.mDatas = new ArrayList<>();
        this.onClickButtonListener = onClickButtonListener;
    }

    @Override
    public BleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BleViewHolder holder = new BleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ble, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final BleViewHolder holder, final int position) {
        BluetoothDevice bluetoothDevice = mDatas.get(position);
        if (null == bluetoothDevice) {
            return;
        }
        holder.mTvAdapterBleName.setText(bluetoothDevice.getName());
        holder.mTvAdapterBleMac.setText(bluetoothDevice.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickButtonListener)
                    onClickButtonListener.onItemClick(holder.itemView, mDatas.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void addItems(BluetoothDevice bleBeans) {
        if (bleBeans == null)
            return;
        this.mDatas.add(bleBeans);
        this.notifyItemRangeInserted(mDatas.size(), 1);
    }

    public void clean() {
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    class BleViewHolder extends RecyclerView.ViewHolder {
        TextView mTvAdapterBleName;
        TextView mTvAdapterBleMac;

        public BleViewHolder(View view) {
            super(view);
            mTvAdapterBleName = (TextView) view.findViewById(R.id.tv_adapter_ble_name);
            mTvAdapterBleMac = (TextView) view.findViewById(R.id.tv_adapter_ble_mac);
        }
    }

    public interface OnClickButtonListener {
        void onItemClick(View view, BluetoothDevice bluetoothDevice);
    }
}
