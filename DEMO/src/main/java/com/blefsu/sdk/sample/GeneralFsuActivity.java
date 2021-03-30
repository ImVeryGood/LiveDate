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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

//import com.bleairswitch.sdk.AirSwitchSdk;
//import com.bleairswitch.sdk.AirSwitchSdkCallback;
import com.blefsu.sdk.bean.ResultBean;
import com.blefsu.sdk.data.FsuReportInfo;
import com.blefsu.sdk.sdk.BleGeneraFsuSdkCallback;
import com.blefsu.sdk.sdk.BleGeneralFsuSdk;

/**
 * Created by yanghaojie on 2017/8/9.
 */

public class GeneralFsuActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SimpleFsuActivity.class.getSimpleName();
    private BluetoothDevice mBluetoothDevice;
    private String mMac;
    private Button mBtnSend, mBtnAirSwitchSend;
    private LinearLayout mLayoutStatus;
    private RelativeLayout mLayoutAirSwitchCmd;
    private BleGeneralFsuSdk mBleGeneralFsuSdk;
    private CmdAdapter mCmdAdapter, mAirSwitchCmdAdapter;
    private DataAdapter mDataAdapter;
    private LinearLayoutManager mBleLayoutManager;
    private RecyclerView mLayoutBleData;
    private Spinner mSpBleOrder, mSpAirSwircwOrder;
    private GeneralCmdArray mGeneralCmdArray;
//    private AirSwitchCmdArray mAirSwitchCmdArray;
    private TextView mTvRssi, mTvInfo;
//    private AirSwitchSdk mAirSwitchSdk;

    public static final String EXTRA_BLE_DEVICE = "ble_device";
    private BleHandler mBleHandler;
    private static final int ADD_ADAPTER = 1;
    private static final int UPDATE_LOCK_STATUS = 2;
    private static final int UPDATE_RSSI = 3;

    private BleGeneraFsuSdkCallback mBleGeneraFsuSdkCallback = new BleGeneraFsuSdkCallback() {
        @Override
        public void init(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "init sdk  \n" + JSON.toJSONString(resultBean)).sendToTarget();
            mBleGeneralFsuSdk.startRssiListener();
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
        public void reset(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "reset \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getFsuInfo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get fsu info \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setFsuDeviceName(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set fsu device name \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getFsuStatus(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get fsu status \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getEthernet(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get ethernet \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setEthernet(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set ethernet \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getPpp(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get ppp \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getServer(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get server \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setServer(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set server \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getWireless(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get wireless \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setWireless(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set wireless \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setUpgrade(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set upgrade \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void restart(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "restart \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getMotor(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get motor \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setMotor(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set motor \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setCardUser(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set cardUser \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getParameter(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set parameter \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setParameter(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set parameter \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getDo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get do \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setDo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set do \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void actionDo(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "action do \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void getEvents(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get events \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void openOnline(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "fsu online open \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();

        }

        @Override
        public void getFsuTime(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "get fsuTime \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }

        @Override
        public void setFsuTime(ResultBean resultBean) {
            mBleHandler.obtainMessage(ADD_ADAPTER, "set fsuTime \n" + JSON.toJSONString(resultBean,
                    SerializerFeature.WriteDateUseDateFormat)).sendToTarget();
        }
    };

//    private AirSwitchSdkCallback mAirSwitchSdkCallback = new AirSwitchSdkCallback() {
//        @Override
//        public void readData(ResultBean resultBean) {
//            if (resultBean.isRet()) {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "read data \n" + resultBean.getObj().toString()).sendToTarget();
//            } else {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "read data \n" + JSON.toJSONString(resultBean)).sendToTarget();
//            }
//        }
//
//        @Override
//        public void readAddress(ResultBean resultBean) {
//            if (resultBean.isRet()) {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "read address \n" + resultBean.getObj().toString()).sendToTarget();
//            } else {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "read address \n" + JSON.toJSONString(resultBean)).sendToTarget();
//            }
//        }
//
//        @Override
//        public void setAddress(ResultBean resultBean) {
//
//        }
//
//        @Override
//        public void broadcastTime(ResultBean resultBean) {
//
//        }
//
//        @Override
//        public void setPassword(ResultBean resultBean) {
//
//        }
//
//        @Override
//        public void cleanLog(ResultBean resultBean) {
//            if (resultBean.isRet()) {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "clean log \n" + resultBean.getObj().toString()).sendToTarget();
//            } else {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "clean log \n" + JSON.toJSONString(resultBean)).sendToTarget();
//            }
//        }
//
//        @Override
//        public void tripSwitch(ResultBean resultBean) {
//            if (resultBean.isRet()) {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "trip switch \n" + resultBean.getObj().toString()).sendToTarget();
//            } else {
//                mBleHandler.obtainMessage(ADD_ADAPTER, "trip switch \n" + JSON.toJSONString(resultBean)).sendToTarget();
//            }
//        }
//    };

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
        mBleGeneralFsuSdk = new BleGeneralFsuSdk();
        mBleGeneralFsuSdk.init(this, mMac, "", mBleGeneraFsuSdkCallback);
//        mAirSwitchSdk = new AirSwitchSdk();
//        mAirSwitchSdk.init(mBleGeneralFsuSdk, mAirSwitchSdkCallback);
        mGeneralCmdArray = new GeneralCmdArray();
        this.mCmdAdapter = new CmdAdapter(this.mGeneralCmdArray.getStringList(), this);
        this.mSpBleOrder.setAdapter(this.mCmdAdapter);
//        mAirSwitchCmdArray = new AirSwitchCmdArray();
//        mAirSwitchCmdAdapter = new CmdAdapter(this.mAirSwitchCmdArray.getStringList(), this);
        mSpAirSwircwOrder.setAdapter(mAirSwitchCmdAdapter);
    }

    private void initView() {
        mLayoutStatus = findViewById(R.id.layout_status);
        mLayoutStatus.setVisibility(View.GONE);
        mLayoutAirSwitchCmd = findViewById(R.id.layout_air_switch_cmd);
        mLayoutAirSwitchCmd.setVisibility(View.GONE);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mBtnAirSwitchSend = (Button) findViewById(R.id.btn_air_switch_send);
        mBtnAirSwitchSend.setOnClickListener(this);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvRssi = (TextView) findViewById(R.id.tv_rssi);
        mSpBleOrder = (Spinner) findViewById(R.id.spinner_cmd);
        mSpAirSwircwOrder = (Spinner) findViewById(R.id.spinner_air_switch_cmd);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                if (29 == mSpBleOrder.getSelectedItemPosition()) {
                    mLayoutAirSwitchCmd.setVisibility(View.VISIBLE);
                } else {
                    String msg = mGeneralCmdArray.sendCmdArray(this, mSpBleOrder.getSelectedItemPosition(), mBleGeneralFsuSdk);
                    if (null != msg && !msg.isEmpty()) {
                        addAdapterItem(msg);
                    }
                }
                break;
//            case R.id.btn_air_switch_send:
//                String msg = mAirSwitchCmdArray.sendCmdArray(this, mSpAirSwircwOrder.getSelectedItemPosition(), mAirSwitchSdk);
//                if (null != msg && !msg.isEmpty()) {
//                    addAdapterItem(msg);
//                }
//                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBleGeneralFsuSdk.destroy();
    }

    private void addAdapterItem(String data) {
        if (!data.isEmpty()) {
            mDataAdapter.addItems(data);
            mLayoutBleData.scrollToPosition(mDataAdapter.getItemCount() - 1);
        }
    }

    private void updateLockStatus(FsuReportInfo reportInfo) {

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
