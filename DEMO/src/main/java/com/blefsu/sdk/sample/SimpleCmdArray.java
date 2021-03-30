package com.blefsu.sdk.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.blefsu.sdk.data.FsuBasicInfo;
import com.blefsu.sdk.data.FsuNbiotInfo;
import com.blefsu.sdk.data.FsuNetworkTransfer;
import com.blefsu.sdk.data.FsuServerInfo;
import com.blefsu.sdk.data.FsuSystemCodeInfo;
import com.blefsu.sdk.data.FsuThresholdInfo;
import com.blefsu.sdk.data.FsuWirelessInfo;
import com.blefsu.sdk.data.OnlineOpenInfo;
import com.blefsu.sdk.sdk.BleSimpleSimpleFsuSdk;
import com.litesuits.common.utils.HexUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import com.blefsu.sdk.sample.R;


/**
 * Created by yanghaojie on 2017/4/20.
 */

public class SimpleCmdArray {
    private List<String> mStringList;
    private int yourChoice = 1;
    private int yourChoice2 = 1;

    public SimpleCmdArray() {
        mStringList = new ArrayList<>();
        mStringList.add("连接FSU蓝牙模块直接开门");
        mStringList.add("连接FSU蓝牙模块并获取信息");
        mStringList.add("断开FSU蓝牙模块");
        mStringList.add("读取FSU事件");
        mStringList.add("蓝牙在线开门");
        mStringList.add("更改FSU系统密码");
        mStringList.add("设置FSU设备ID");
        mStringList.add("重置FSU");
        mStringList.add("获取拨号状态信息");
        mStringList.add("获取目标服务器信息");
        mStringList.add("设置目标服务器信息");
        mStringList.add("获取无线配置");
        mStringList.add("设置无线配置");
        mStringList.add("设置告警阀值信息");
        mStringList.add("BLE蓝牙透传");
        mStringList.add("获取告警阀值信息");
        mStringList.add("获取SIM卡信息");
        mStringList.add("申请远程开门");
        mStringList.add("告警撤防/布防");
        mStringList.add("设置蓝牙名称");
        mStringList.add("设置NBIoT参数");
        mStringList.add("设备重启");
        mStringList.add("获取设备状态");
    }

    public List<String> getStringList() {
        return mStringList;
    }

    public String sendCmdArray(final Context context, int cmd, final BleSimpleSimpleFsuSdk bleSimpleFsuSdk) {
        StringBuilder msg = new StringBuilder(64);
        final Calendar begin = Calendar.getInstance();
        final Calendar end = Calendar.getInstance();
        switch (cmd) {
            case 0:
                bleSimpleFsuSdk.connectAndOpen(3, begin.getTime(), end.getTime(), 15);
                break;
            case 1:
                bleSimpleFsuSdk.connect(FsuBasicInfo.DEFINE_SYS_CODE, true);
                break;
            case 2:
                bleSimpleFsuSdk.disconnect();
                break;
            case 3:
                bleSimpleFsuSdk.readEvents(0);
                break;
            case 4:
                end.add(Calendar.HOUR_OF_DAY, 15);
                final String[] items = {"ALL", "LOCK1", "LOCK2"};
                yourChoice = 0;
                AlertDialog.Builder onlineOpenDialog = new AlertDialog.Builder(context);
                onlineOpenDialog.setTitle("Open Online");
                onlineOpenDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
                onlineOpenDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                onlineOpenDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bleSimpleFsuSdk.openOnline(3, begin.getTime(), end.getTime(), yourChoice, 15);
                    }
                });
                onlineOpenDialog.show();
//                final EditText sleepText = new EditText(context);
//                sleepText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                sleepText.setText("10");
//                AlertDialog.Builder sleepDialog = new AlertDialog.Builder(context);
//                sleepDialog.setTitle("延时时间（单位:毫秒）").setView(sleepText);
//                sleepDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                sleepDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        try {
//                            int sleep = Integer.valueOf(sleepText.getText().toString().trim());
//                            bleSimpleFsuSdk.openOnline(3, begin.getTime(), end.getTime(), 1, 15);
//                            Thread.sleep(sleep);
//                            bleSimpleFsuSdk.openOnline(3, begin.getTime(), end.getTime(), 2, 15);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }).show();
                break;
            case 5:
                FsuSystemCodeInfo fsuSystemCodeInfo = new FsuSystemCodeInfo();
                fsuSystemCodeInfo.setOldCode(FsuBasicInfo.DEFINE_SYS_CODE);
                fsuSystemCodeInfo.setNewCode(FsuBasicInfo.DEFINE_SYS_CODE);
                bleSimpleFsuSdk.setSystemCode(fsuSystemCodeInfo);
                break;
            case 6:
                final EditText editText = new EditText(context);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(context);
                inputDialog.setTitle("FSU DeviceID").setView(editText);
                inputDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String deviceId = editText.getText().toString().trim();
                        if (deviceId != null && deviceId.length() == 8) {
                            bleSimpleFsuSdk.setDeviceId(HexUtil.decodeHex(deviceId.toCharArray()));
                        } else {
                            Toast.makeText(context, "device id is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
                break;
            case 7:
                bleSimpleFsuSdk.reset();
                break;
            case 8:
                bleSimpleFsuSdk.getPpp();
                break;
            case 9:
                bleSimpleFsuSdk.getServer();
                break;
            case 10:
                AlertDialog.Builder serverDialog = new AlertDialog.Builder(context);
                final View dialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_server_info, null);
                serverDialog.setView(dialogView);
                serverDialog.setTitle("Server Information");
                serverDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                serverDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edtIp1 = (EditText) dialogView.findViewById(R.id.edt_ip1);
                        EditText edtIp2 = (EditText) dialogView.findViewById(R.id.edt_ip2);
                        EditText edtIp3 = (EditText) dialogView.findViewById(R.id.edt_ip3);
                        EditText edtIp4 = (EditText) dialogView.findViewById(R.id.edt_ip4);
                        EditText edtPort = (EditText) dialogView.findViewById(R.id.edt_port);
                        FsuServerInfo fsuServerInfo = new FsuServerInfo();
                        byte ip[] = {(byte) Integer.parseInt(edtIp1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp4.getText().toString().trim())};
                        fsuServerInfo.setIp(ip);
                        fsuServerInfo.setPort(Integer.valueOf(edtPort.getText().toString().trim()));
                        bleSimpleFsuSdk.setServer(fsuServerInfo);
                    }
                });
                serverDialog.show();
                break;
            case 11:
                bleSimpleFsuSdk.getWireless();
                break;
            case 12:
                AlertDialog.Builder wirelessDialog = new AlertDialog.Builder(context);
                final View wirelessDialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_wireless_info, null);
                wirelessDialog.setView(wirelessDialogView);
                wirelessDialog.setTitle("Wireless Information");
                wirelessDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                wirelessDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText user = (EditText) wirelessDialogView.findViewById(R.id.edt_user);
                        EditText password = (EditText) wirelessDialogView.findViewById(R.id.edt_password);
                        EditText apn = (EditText) wirelessDialogView.findViewById(R.id.edt_apn);
                        EditText phone = (EditText) wirelessDialogView.findViewById(R.id.edt_phone);
                        FsuWirelessInfo fsuWirelessInfo = new FsuWirelessInfo();
                        fsuWirelessInfo.setUser(user.getText().toString().trim());
                        fsuWirelessInfo.setPassword(password.getText().toString().trim());
                        fsuWirelessInfo.setApn(apn.getText().toString().trim());
                        fsuWirelessInfo.setPhone(phone.getText().toString().trim());
                        bleSimpleFsuSdk.setWireless(fsuWirelessInfo);
                    }
                });
                wirelessDialog.show();
                break;
            case 13:
                final View thresholdDialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_threshold_info, null);
                final AlertDialog.Builder thresholdDialog = new AlertDialog.Builder(context);
                thresholdDialog.setView(thresholdDialogView);
                thresholdDialog.setTitle("Warn Threshold");
                thresholdDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                thresholdDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FsuThresholdInfo fsuThresholdInfo = new FsuThresholdInfo();
                        EditText minTemp = (EditText) thresholdDialogView.findViewById(R.id.edt_min_temp);
                        EditText maxTemp = (EditText) thresholdDialogView.findViewById(R.id.edt_max_temp);
                        EditText battery = (EditText) thresholdDialogView.findViewById(R.id.edt_battery);
                        Switch swGradienter = (Switch) thresholdDialogView.findViewById(R.id.switch_gradienter);
                        Switch swWaterLogger = (Switch) thresholdDialogView.findViewById(R.id.switch_shake);
                        Switch swShake = (Switch) thresholdDialogView.findViewById(R.id.switch_shake);
                        Switch swCamera = (Switch) thresholdDialogView.findViewById(R.id.switch_camera);
                        Switch swOpenLid = (Switch) thresholdDialogView.findViewById(R.id.switch_openLid);
                        EditText edtCloseTimeout = (EditText) thresholdDialogView.findViewById(R.id.edt_closeTimeout);
                        EditText round = (EditText) thresholdDialogView.findViewById(R.id.edt_round);
                        Switch swLock1 = (Switch) thresholdDialogView.findViewById(R.id.switch_lock1);
                        Switch swLock2 = (Switch) thresholdDialogView.findViewById(R.id.switch_lock2);
                        Switch swLock3 = (Switch) thresholdDialogView.findViewById(R.id.switch_lock3);
                        Switch swLock4 = (Switch) thresholdDialogView.findViewById(R.id.switch_lock4);

                        fsuThresholdInfo.setMinTemperature(Integer.valueOf(minTemp.getText().toString().trim()));
                        fsuThresholdInfo.setMaxTemperature(Integer.valueOf(maxTemp.getText().toString().trim()));
                        fsuThresholdInfo.setMinBattery(Integer.valueOf(battery.getText().toString().trim()));
                        fsuThresholdInfo.setGradienter(swGradienter.isChecked() ? 1 : 0);
                        fsuThresholdInfo.setWaterLogger(swWaterLogger.isChecked() ? 1 : 0);
                        fsuThresholdInfo.setShake(swShake.isChecked() ? 1 : 0);
                        fsuThresholdInfo.setCamera(swCamera.isChecked() ? 1 : 0);
                        fsuThresholdInfo.setOpenLid(swOpenLid.isChecked() ? 1 : 0);
                        fsuThresholdInfo.setCloseTimeout(Integer.valueOf(edtCloseTimeout.getText().toString().trim()));
                        fsuThresholdInfo.setRound(Integer.valueOf(round.getText().toString().trim()));
                        boolean[] lock = new boolean[4];
                        lock[0] = swLock1.isChecked();
                        lock[1] = swLock2.isChecked();
                        lock[2] = swLock3.isChecked();
                        lock[3] = swLock4.isChecked();
                        fsuThresholdInfo.setLockEnable(lock);
                        bleSimpleFsuSdk.setThresholdInfo(fsuThresholdInfo);
                    }
                });
                thresholdDialog.show();
                break;
            case 14:
                final EditText editBleTransferText = new EditText(context);
                AlertDialog.Builder bleTransferDialog = new AlertDialog.Builder(context);
                bleTransferDialog.setTitle("Ble Transfer").setView(editBleTransferText);
                bleTransferDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                bleTransferDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String data = editBleTransferText.getText().toString().trim();
                        if (data != null && data.length() % 2 == 20) {
                            FsuNetworkTransfer fsuNetworkTransfer = new FsuNetworkTransfer();
                            fsuNetworkTransfer.setData(data.getBytes());
                            bleSimpleFsuSdk.setNetworkTransfer(fsuNetworkTransfer);
                        } else {
                            Toast.makeText(context, "ble name is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
//                View networkTransferView = LayoutInflater.from(context)
//                        .inflate(R.layout.dialog_transfer_info, null);
//                final AlertDialog.Builder networkTransferDialog = new AlertDialog.Builder(context);
//                networkTransferDialog.setTitle("BLE Transfer").setView(networkTransferView);
//                networkTransferDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                final Dialog dialog = networkTransferDialog.show();

//                Button btnSendSpinner = networkTransferView.findViewById(R.id.btn_send_spinner);
//                Button btnSendEdt = networkTransferView.findViewById(R.id.btn_txt_send);
//                final EditText edtData = networkTransferView.findViewById(R.id.txt_data);
//                final Spinner spinner = networkTransferView.findViewById(R.id.spinner_cmd);
//                final String[] data = context.getResources().getStringArray(R.array.nbiot_data);
//                btnSendSpinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FsuNetworkTransfer fsuNetworkTransfer = new FsuNetworkTransfer();
//                        fsuNetworkTransfer.setData(data[spinner.getSelectedItemPosition()].getBytes());
//                        bleSimpleFsuSdk.setNetworkTransfer(fsuNetworkTransfer);
//                        dialog.dismiss();
//                    }
//                });
//                btnSendEdt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FsuNetworkTransfer fsuNetworkTransfer = new FsuNetworkTransfer();
//                        fsuNetworkTransfer.setData(edtData.getText().toString().trim().getBytes());
//                        bleSimpleFsuSdk.setNetworkTransfer(fsuNetworkTransfer);
//                        dialog.dismiss();
//                    }
//                });
                break;
            case 15:
                bleSimpleFsuSdk.getThresholdInfo();
                break;
            case 16:
                bleSimpleFsuSdk.getSimInfo();
                break;
            case 17:
                final View remoteOpenDialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_remote_open, null);
                AlertDialog.Builder remoteOpenDialog = new AlertDialog.Builder(context);
                remoteOpenDialog.setView(remoteOpenDialogView);
                remoteOpenDialog.setTitle("Warn Threshold");
                remoteOpenDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                remoteOpenDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OnlineOpenInfo onlineOpenInfo = new OnlineOpenInfo();
                        EditText lockId = (EditText) remoteOpenDialogView.findViewById(R.id.edt_lock);
                        EditText userId = (EditText) remoteOpenDialogView.findViewById(R.id.edt_user);

                        onlineOpenInfo.setUserId(Integer.valueOf(userId.getText().toString().trim()));
                        onlineOpenInfo.setLockId(Integer.valueOf(lockId.getText().toString().trim()));
                        bleSimpleFsuSdk.askRemoteOpen(onlineOpenInfo);
                    }
                });
                remoteOpenDialog.show();
                break;
            case 18:
                end.add(Calendar.HOUR_OF_DAY, 15);
                final String[] enableWarnItems = {"撤防", "布防"};
                yourChoice = 1;
                AlertDialog.Builder enableWarnDialog = new AlertDialog.Builder(context);
                enableWarnDialog.setTitle("撤防/布防");
                enableWarnDialog.setSingleChoiceItems(enableWarnItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which + 1;
                    }
                });
                enableWarnDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                enableWarnDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bleSimpleFsuSdk.enableWarn(yourChoice == 1 ? false : true);
                    }
                });
                enableWarnDialog.show();
                break;
            case 19:
                final EditText editBleNameText = new EditText(context);
                AlertDialog.Builder bleNameDialog = new AlertDialog.Builder(context);
                bleNameDialog.setTitle("Set Ble Name").setView(editBleNameText);
                bleNameDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                bleNameDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editBleNameText.getText().toString().trim();
                        if (name != null && name.length() <= 20 && name.length() > 0) {
                            bleSimpleFsuSdk.setBleName(name);
                        } else {
                            Toast.makeText(context, "ble name is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
                break;
            case 20:
                final View nbiotDialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_nbiot_info, null);
                final AlertDialog.Builder nbiotDialog = new AlertDialog.Builder(context);
                final RadioGroup rgProtocols = nbiotDialogView.findViewById(R.id.rg_protocols);
                final RadioGroup rgOperator = nbiotDialogView.findViewById(R.id.rg_operator);
                nbiotDialog.setTitle("NBIoT Setting").setView(nbiotDialogView);
                nbiotDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                nbiotDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FsuNbiotInfo fsuNbiotInfo = new FsuNbiotInfo();
                        for (int p = 0; p < rgProtocols.getChildCount(); p++) {
                            if (((RadioButton) rgProtocols.getChildAt(p)).isChecked()) {
                                fsuNbiotInfo.setProtocols(p);
                                break;
                            }
                        }
                        for (int o = 0; o < rgOperator.getChildCount(); o++) {
                            if (((RadioButton) rgOperator.getChildAt(o)).isChecked()) {
                                fsuNbiotInfo.setOperator(o);
                                break;
                            }
                        }
                        bleSimpleFsuSdk.setNBIoTInfo(fsuNbiotInfo);
                    }
                });
                nbiotDialog.show();
                break;
            case 21:
                final String[] restart = {"重启", "停用", "启用"};
                yourChoice = 0;
                AlertDialog.Builder restartDialog = new AlertDialog.Builder(context);
                restartDialog.setTitle("重启");
                restartDialog.setSingleChoiceItems(restart, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
                restartDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (bleSimpleFsuSdk.FSU_RESTART == yourChoice)
                                    bleSimpleFsuSdk.restart(bleSimpleFsuSdk.FSU_RESTART);
                                else if (bleSimpleFsuSdk.FSU_STOP == yourChoice)
                                    bleSimpleFsuSdk.restart(bleSimpleFsuSdk.FSU_STOP);
                                else if (bleSimpleFsuSdk.FSU_RUN == yourChoice)
                                    bleSimpleFsuSdk.restart(bleSimpleFsuSdk.FSU_RUN);
                                else {

                                }
                            }
                        });
                restartDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                restartDialog.show();
                break;
            case 22:
                bleSimpleFsuSdk.getFsuStatus();
                break;
            default:
                break;
        }
        return msg.toString();
    }
}