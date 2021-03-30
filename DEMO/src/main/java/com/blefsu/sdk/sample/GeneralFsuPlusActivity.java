package com.blefsu.sdk.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.blefsu.sdk.bean.ResultBean;
import com.blefsu.sdk.sdk.BleGeneraFsuPlusCallback;
import com.blefsu.sdk.sdk.BleGeneraFsuPlusSdk;

public class GeneralFsuPlusActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SimpleFsuActivity.class.getSimpleName();
    private BluetoothDevice mBluetoothDevice;
    private String mMac;
    private Button mBtnSend;

    private BleGeneraFsuPlusSdk mBleGeneraFsuPlusSdk;
    private CmdAdapter mCmdAdapter;
    private DataAdapter mDataAdapter;
    private LinearLayoutManager mBleLayoutManager;
    private RecyclerView mLayoutBleData;
    private Spinner mSpBleOrder;
    private GeneralPlusCmdArray mGeneralPlusCmdArray;
    private TextView mTvRssi, mTvInfo;

    public static final String EXTRA_BLE_DEVICE = "ble_device";
    private BleHandler mBleHandler;
    private static final int ADD_ADAPTER = 1;

    private BleGeneraFsuPlusCallback mBleGeneraFsuPlusCallback = new BleGeneraFsuPlusCallback() {
        @Override
        public void init(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "init sdk  \n" + JSON.toJSONString(resultBean)).sendToTarget();
        }

        @Override
        public void connect(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "connect fsu plus \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void disconnect(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "disconnect fsu plus \n" + JSON.toJSONString(resultBean)).sendToTarget();
        }

        @Override
        public void openMotor(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "open motor \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getPlusInfo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get fsu plus info \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setPlusInfo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set fsu plus info \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsu);
        initView();
        initData();
    }

    private void initData() {
        mBluetoothDevice = (BluetoothDevice) getIntent().getExtras().get(EXTRA_BLE_DEVICE);
        mTvInfo.setText("Name:" + mBluetoothDevice.getName() + "\n" + "Address:" + mBluetoothDevice.getAddress());
        mBleHandler = new BleHandler();
        mMac = mBluetoothDevice.getAddress();
        mBleGeneraFsuPlusSdk = new BleGeneraFsuPlusSdk();
        mBleGeneraFsuPlusSdk.init(this, mMac, "", mBleGeneraFsuPlusCallback);
        mGeneralPlusCmdArray = new GeneralPlusCmdArray();
        this.mCmdAdapter = new CmdAdapter(this.mGeneralPlusCmdArray.getStringList(), this);
        this.mSpBleOrder.setAdapter(this.mCmdAdapter);
    }

    private void initView() {
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvRssi = (TextView) findViewById(R.id.tv_rssi);
        mSpBleOrder = (Spinner) findViewById(R.id.spinner_cmd);
        mLayoutBleData = (RecyclerView) findViewById(R.id.view_data);
        mBleLayoutManager = new LinearLayoutManager(this);
        mBleLayoutManager.setReverseLayout(true);
        mBleLayoutManager.setStackFromEnd(true);
        mDataAdapter = new DataAdapter();
        mLayoutBleData.setAdapter(mDataAdapter);
        mLayoutBleData.setLayoutManager(mBleLayoutManager);
        mLayoutBleData.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String msg = mGeneralPlusCmdArray.sendCmdArray(this, mSpBleOrder.getSelectedItemPosition(), mBleGeneraFsuPlusSdk);
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
        mBleGeneraFsuPlusSdk.destroy();
    }

    private void addAdapterItem(String data) {
        if (!data.isEmpty()) {
            mDataAdapter.addItems(data);
            mLayoutBleData.scrollToPosition(mDataAdapter.getItemCount() - 1);
        }
    }

    private class BleHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADD_ADAPTER:
                    addAdapterItem((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
