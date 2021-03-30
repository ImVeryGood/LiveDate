package com.blefsu.sdk.sample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blefsu.sdk.ble.BleScanCallback;
import com.blefsu.sdk.ble.BluetoothLeScan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yanghaojie on 2017/7/12.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnScan;
    private RecyclerView mLayoutScan;
    private List<String> mBleName;
    private boolean isScan;
    private BluetoothLeScan mBluetoothLeScan;
    private com.blefsu.sdk.sample.BleAdapter mBleAdapter;
    private LinearLayoutManager mBleLayoutManager;
    private HashMap<String, BluetoothDevice> mBluetoothDeviceHashMap;
    private BleHandler mBleHandler;
    private static final int ADD_ADAPTER = 1;

    public static final String EXTRA_GJ_BLE_NAME = "RAYONICS_GJ_CTL";
    public static final String EXTRA_DH_BLE_NAME = "RAYONICS_DH_CTL";
    public static final String EXTRA_RS_DH_BLE_NAME = "RS02002";
    public static final String EXTRA_RS_DH_PLUS_BLE_NAME = "RS02001";
    public static final String EXTRA_RS_GJ_NAME = "RS03";
    public static final String EXTRA_RS_MJ_NAME = "RS070";

    public static final String EXTRA_RS_KPIC_NAME = "KPIC";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        initView();
        initData();
    }

    private BleScanCallback mBleScanCallback = new BleScanCallback() {
        @Override
        public void openBluetooth() {

        }

        @Override
        public void findBle(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
            Log.d("SSSSSSSSSSSSSSSS", "findBle: "+bluetoothDevice.getAddress());
            mBleHandler.obtainMessage(ADD_ADAPTER, bluetoothDevice).sendToTarget();
        }

        @Override
        public void finishScan() {

        }
    };

    private com.blefsu.sdk.sample.BleAdapter.OnClickButtonListener mOnClickButtonListener = new com.blefsu.sdk.sample.BleAdapter.OnClickButtonListener() {
        @Override
        public void onItemClick(View view, BluetoothDevice bluetoothDevice) {
            mBluetoothLeScan.stopReceiver();
            mBtnScan.setText("搜索");
            isScan = false;
            clickItem(bluetoothDevice);
        }
    };

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnScan.setOnClickListener(this);
        mLayoutScan = (RecyclerView) findViewById(R.id.layout_scan);
        mBleLayoutManager = new LinearLayoutManager(this);
        mBleAdapter = new com.blefsu.sdk.sample.BleAdapter(mOnClickButtonListener);
        mLayoutScan.setAdapter(mBleAdapter);
        mLayoutScan.setLayoutManager(mBleLayoutManager);
        mLayoutScan.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mBleName = new ArrayList<>();
        mBleName.add(EXTRA_GJ_BLE_NAME);
        mBleName.add(EXTRA_DH_BLE_NAME);
        mBleName.add(EXTRA_RS_DH_BLE_NAME);
        mBleName.add(EXTRA_RS_GJ_NAME);
        mBleName.add(EXTRA_RS_DH_PLUS_BLE_NAME);
        mBleName.add(EXTRA_RS_KPIC_NAME);
        mBluetoothLeScan = new BluetoothLeScan(BluetoothAdapter.getDefaultAdapter(), 0, mBleScanCallback);
        mBleHandler = new BleHandler();
        isScan = false;
        mBluetoothDeviceHashMap = new HashMap<>();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isScan) mBluetoothLeScan.stopReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isScan) mBluetoothLeScan.startReceiver();
    }

    private void clickItem(BluetoothDevice bluetoothDevice) {
        Intent intent = null;
        if (bluetoothDevice.getName().contains(EXTRA_GJ_BLE_NAME) || bluetoothDevice.getName().contains(EXTRA_RS_GJ_NAME)
                || bluetoothDevice.getName().contains(EXTRA_RS_MJ_NAME) || bluetoothDevice.getName().contains(EXTRA_RS_KPIC_NAME)) {
            intent = new Intent(this, com.blefsu.sdk.sample.SimpleFsuActivity.class);
            intent.putExtra(com.blefsu.sdk.sample.SimpleFsuActivity.EXTRA_BLE_DEVICE, bluetoothDevice);
        } else if (bluetoothDevice.getName().contains(EXTRA_DH_BLE_NAME) || bluetoothDevice.getName().contains(EXTRA_RS_DH_BLE_NAME)) {
            intent = new Intent(this, com.blefsu.sdk.sample.GeneralFsuActivity.class);
            intent.putExtra(com.blefsu.sdk.sample.GeneralFsuActivity.EXTRA_BLE_DEVICE, bluetoothDevice);
        } else if (bluetoothDevice.getName().contains(EXTRA_RS_DH_PLUS_BLE_NAME)) {
            intent = new Intent(this, com.blefsu.sdk.sample.GeneralFsuPlusActivity.class);
            intent.putExtra(com.blefsu.sdk.sample.GeneralFsuActivity.EXTRA_BLE_DEVICE, bluetoothDevice);
        } else {
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                if (!isScan) {
                    mBluetoothDeviceHashMap = new HashMap<>();
                    mBleAdapter.clean();
                    mBluetoothLeScan.startReceiver();
                    mBtnScan.setText("停止");
                    isScan = true;
                } else {
                    mBluetoothLeScan.stopReceiver();
                    mBtnScan.setText("搜索");
                    isScan = false;
                }
                break;
            default:
                break;
        }
    }

    private void addAdapterItem(BluetoothDevice bluetoothDevice) {
        if (mBleName.size() == 0 || checkName(bluetoothDevice.getName())) {
            if (!mBluetoothDeviceHashMap.containsKey(bluetoothDevice.getAddress())) {
                mBleAdapter.addItems(bluetoothDevice);
                mBluetoothDeviceHashMap.put(bluetoothDevice.getAddress(), bluetoothDevice);
                mLayoutScan.scrollToPosition(mBleAdapter.getItemCount() - 1);
            }
        }
    }

    private boolean checkName(String name) {
        if (null == name) return false;
        for (String ble : mBleName) {
            if (name.contains(ble)) {
                return true;
            }
        }
        return false;
    }

    private class BleHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADD_ADAPTER:
                    addAdapterItem((BluetoothDevice) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
