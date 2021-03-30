package com.blefsu.sdk.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.blefsu.sdk.bean.ResultBean;
import com.blefsu.sdk.data.FsuReportInfo;
import com.blefsu.sdk.sdk.BleSimpleFsuSdkCallback;
import com.blefsu.sdk.sdk.BleSimpleSimpleFsuSdk;
import com.blefsu.sdk.utils.TimeUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blefsu.sdk.sample.R;

public class SimpleFsuActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SimpleFsuActivity.class.getSimpleName();
    private BluetoothDevice mBluetoothDevice;
    private String mMac;
    private Button mBtnSend;
    private TextView mTvInfo, mTvRssi, mTvBattery, mTvTemperature, mTvShake, mTvWaterlogging, mDi3, mDi6;
    private TextView mTvGradienter, mTvTime, mTvEventCount, mTvLock1, mTvLock2, mTvMagnetometer1, mTvMagnetometer2;
    private BleSimpleSimpleFsuSdk mBleSimpleFsuSdk;
    private com.blefsu.sdk.sample.CmdAdapter mCmdAdapter;
    private com.blefsu.sdk.sample.DataAdapter mDataAdapter;
    private LinearLayoutManager mBleLayoutManager;
    private RecyclerView mLayoutBleData;
    private Spinner mSpBleOrder;
    private com.blefsu.sdk.sample.SimpleCmdArray mSimpleCmdArray;

    public static final String EXTRA_BLE_DEVICE = "ble_device";
    private BleHandler mBleHandler;
    private static final int ADD_ADAPTER = 1;
    private static final int UPDATE_LOCK_STATUS = 2;
    private static final int UPDATE_RSSI = 3;

    private BleSimpleFsuSdkCallback mBleSimpleFsuSdkCallback = new BleSimpleFsuSdkCallback() {
        @Override
        public void init(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "init sdk  \n" + JSON.toJSONString(resultBean)).sendToTarget();
//            mBleSimpleFsuSdk.startRssiListener();
        }

        @Override
        public void connect(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "connect fsu  \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();

        }

        @Override
        public void rssi(int rssi) {
            mBleHandler.obtainMessage(UPDATE_RSSI, String.valueOf(rssi)).sendToTarget();

        }

        @Override
        public void disConnect(ResultBean resultBean) {
            mBleHandler.obtainMessage(UPDATE_RSSI, "disconnect").sendToTarget();

            mBleHandler.obtainMessage(ADD_ADAPTER, "disconnect fsu  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void onlineOpen(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "fsu online open \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void onReport(ResultBean resultBean) {
            if (null == resultBean.getObj()) {
                mBleHandler.obtainMessage(ADD_ADAPTER, "fsu report \n" + JSON.toJSONString(resultBean)).sendToTarget();
            } else {
                mBleHandler.obtainMessage(UPDATE_LOCK_STATUS, resultBean.getObj()).sendToTarget();
            }
        }

        @Override
        public void readEvent(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "read fsu events  \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setSystemCode(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set fsu system-code  \n" + JSON.toJSONString(resultBean)).sendToTarget();
        }

        @Override
        public void setDeviceId(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set fsu device id  \n" + JSON.toJSONString(resultBean)).sendToTarget();
        }

        @Override
        public void reset(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "reset fsu  \n" + JSON.toJSONString(resultBean)).sendToTarget();
        }

        @Override
        public void getPpp(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get ppp  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void getServer(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get server info  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void setServer(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set server info  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void getWireless(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get wireless  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void setWireless(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set wireless  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void setThresholdInfo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set warn threshold  \n" + JSON.toJSONString(resultBean)).sendToTarget();
        }

        @Override
        public void getThresholdInfo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get warn threshold  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void setNetworkTransfer(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get network transfer  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void getSimInfo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get sim info  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void askRemoteOpen(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "ask remote open  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void enableWarn(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "enable warn  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void setBleName(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "setBleName  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void setNbiotInfo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "setNbiotInfo  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void restart(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "restart  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }

        @Override
        public void getFsuStatus(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "restart  \n" + JSON.toJSONString(resultBean)).sendToTarget();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsu);
        initView();
        initData();
    }

    private void initView() {
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvRssi = (TextView) findViewById(R.id.tv_rssi);
        mTvBattery = (TextView) findViewById(R.id.tv_battery);
        mTvGradienter = (TextView) findViewById(R.id.tv_gradienter);
        mTvEventCount = (TextView) findViewById(R.id.tv_event_count);
        mTvShake = (TextView) findViewById(R.id.tv_shake);
        mTvTemperature = (TextView) findViewById(R.id.tv_temperature);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvWaterlogging = (TextView) findViewById(R.id.tv_water_logging);
        mTvLock1 = (TextView) findViewById(R.id.tv_lock1);
        mTvLock2 = (TextView) findViewById(R.id.tv_lock2);
        mTvMagnetometer1 = (TextView) findViewById(R.id.tv_lock_magnetometer1);
        mTvMagnetometer2 = (TextView) findViewById(R.id.tv_lock_magnetometer2);
        mSpBleOrder = (Spinner) findViewById(R.id.spinner_cmd);
        mDi3 = (TextView) findViewById(R.id.tv_di3);
        mDi6 = (TextView) findViewById(R.id.tv_di6);
        mLayoutBleData = (RecyclerView) findViewById(R.id.view_data);
        mBleLayoutManager = new LinearLayoutManager(this);
        mBleLayoutManager.setReverseLayout(true);
        mBleLayoutManager.setStackFromEnd(true);
        mDataAdapter = new com.blefsu.sdk.sample.DataAdapter();
        mLayoutBleData.setAdapter(mDataAdapter);
        mLayoutBleData.setLayoutManager(mBleLayoutManager);
        mLayoutBleData.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mBluetoothDevice = (BluetoothDevice) getIntent().getExtras().get(EXTRA_BLE_DEVICE);
        mTvInfo.setText("Name:" + mBluetoothDevice.getName() + "\n" + "Address:" + mBluetoothDevice.getAddress());
        mBleHandler = new BleHandler();
        mMac = mBluetoothDevice.getAddress();
//        mMac = "";

//        BluetoothGattAttributes.TST_VCOMService = "0000ffe0-0000-1000-8000-00805f9b34fb";
//        BluetoothGattAttributes.RST_VCOM = "0000ffe1-0000-1000-8000-00805f9b34fb";
//        BluetoothGattAttributes.TST_VCOM = "0000ffe1-0000-1000-8000-00805f9b34fb";

        mBleSimpleFsuSdk = new BleSimpleSimpleFsuSdk();
        mBleSimpleFsuSdk.init(this, mMac, "", mBleSimpleFsuSdkCallback);
        mSimpleCmdArray = new com.blefsu.sdk.sample.SimpleCmdArray();
        this.mCmdAdapter = new com.blefsu.sdk.sample.CmdAdapter(this.mSimpleCmdArray.getStringList(), this);
        this.mSpBleOrder.setAdapter(this.mCmdAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String msg = mSimpleCmdArray.sendCmdArray(this, mSpBleOrder.getSelectedItemPosition(), mBleSimpleFsuSdk);
                if (null != msg && !msg.isEmpty()) {
                    addAdapterItem(msg);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBleSimpleFsuSdk.destroy();
    }

    private void addAdapterItem(String data) {
        if (!data.isEmpty()) {
            mDataAdapter.addItems(data);
            mLayoutBleData.scrollToPosition(mDataAdapter.getItemCount() - 1);
        }
    }

    private void updateLockStatus(FsuReportInfo reportInfo) {
        if (null != reportInfo) {
            mTvBattery.setText(String.format("电量:%d%%", reportInfo.getBattery()));
            mTvTime.setText(String.format("时间:%s", TimeUtils.dateToNotYMDHM(reportInfo.getTime())));
            mTvMagnetometer2.setText(String.format("门磁2:%d", reportInfo.getLockStatus().get(1).getMagnetometer()));
            mTvMagnetometer1.setText(String.format("门磁1:%d", reportInfo.getLockStatus().get(0).getMagnetometer()));
            mTvLock2.setText(String.format("门锁2:%d", reportInfo.getLockStatus().get(1).getLock()));
            mTvLock1.setText(String.format("门锁1:%d", reportInfo.getLockStatus().get(0).getLock()));
            mTvTemperature.setText(String.format("温度:%S", reportInfo.getTemperature()));
            mTvShake.setText(String.format("震动:%d", reportInfo.getShake()));
            mTvWaterlogging.setText(String.format("水浸:%d", reportInfo.getWaterlogging()));
            mTvEventCount.setText(String.format("事件:%d", reportInfo.getEventCount()));
            mTvGradienter.setText(String.format("水平仪:%d", reportInfo.getGradienter()));
            mDi3.setText(String.format("DI3:%d", reportInfo.getDi3()));
            mDi6.setText(String.format("DI6:%d", reportInfo.getDi6()));
        }
    }

    private void updateRssi(String rssi) {
        mTvRssi.setText(rssi);
    }

    private class BleHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADD_ADAPTER:
                    addAdapterItem((String) msg.obj);
                    break;
                case UPDATE_LOCK_STATUS:
                    updateLockStatus((FsuReportInfo) msg.obj);
                    break;
                case UPDATE_RSSI:
                    updateRssi((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
