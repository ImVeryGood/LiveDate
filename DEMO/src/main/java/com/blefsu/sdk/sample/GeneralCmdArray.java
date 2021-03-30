package com.blefsu.sdk.sample;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.blefsu.sdk.data.FsuDoInfo;
import com.blefsu.sdk.data.FsuEthernetInfo;
import com.blefsu.sdk.data.FsuMotorInfo;
import com.blefsu.sdk.data.FsuParameterInfo;
import com.blefsu.sdk.data.FsuServerInfo;
import com.blefsu.sdk.data.FsuUserInfo;
import com.blefsu.sdk.data.FsuWirelessInfo;
import com.blefsu.sdk.sdk.BleGeneralFsuSdk;

/**
 * Created by yanghaojie on 2017/8/9.
 */

public class GeneralCmdArray<T> {
    private List<String> mStringList;

    public GeneralCmdArray() {
        mStringList = new ArrayList<>();
        mStringList.add("连接FSU蓝牙模块");
        mStringList.add("断开FSU蓝牙模块");
        mStringList.add("获取设备基本信息");
        mStringList.add("设置设备名称");
        mStringList.add("获取以太网信息");
        mStringList.add("设置以太网信息");
        mStringList.add("获取PPP信息");
        mStringList.add("获取目标服务器信息");
        mStringList.add("设置目标服务器信息");
        mStringList.add("获取无线网络信息");
        mStringList.add("设置无线网络信息");
        mStringList.add("设置在线升级配置");
        mStringList.add("获取设备时间");
        mStringList.add("设置设备时间");
        mStringList.add("设备重启");
        mStringList.add("获取电机信息");
        mStringList.add("设置电机信息");
        mStringList.add("增加卡权限");
        mStringList.add("删除卡权限");
        mStringList.add("获取卡权限");
        mStringList.add("设备重置");
        mStringList.add("获取设备参数信息");
        mStringList.add("设置设备参数信息");
        mStringList.add("获取DO配置信息");
        mStringList.add("设置DO配置信息");
        mStringList.add("获取设备DI/AI信息");
        mStringList.add("蓝牙在线开门");
        mStringList.add("控制电机");
        mStringList.add("控制DO");
        mStringList.add("空气开关");

    }

    public List<String> getStringList() {
        return mStringList;
    }

    public String sendCmdArray(Context context, int cmd, final BleGeneralFsuSdk bleGeneralFsuSdk) {
        StringBuilder msg = new StringBuilder(64);
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        switch (cmd) {
            case 0:
                byte[] sysCode = {0x36, 0x36, 0x36, 0x36};
                bleGeneralFsuSdk.connect(sysCode);
                break;
            case 1:
                bleGeneralFsuSdk.disconnect();
                break;
            case 2:
                bleGeneralFsuSdk.getFsuInfo();
                break;
            case 3:
                final EditText editText = new EditText(context);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(context);
                inputDialog.setTitle("BLE new name").setView(editText);
                inputDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                inputDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bleGeneralFsuSdk.setFsuDeviceName(editText.getText().toString().trim());
                            }
                        }).show();
                break;
            case 4:
                bleGeneralFsuSdk.getEthernet();
                break;
            case 5:
                AlertDialog.Builder ethernetDialog = new AlertDialog.Builder(context);
                final View ethernetDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_ethernet_info, null);
                ethernetDialog.setView(ethernetDialogView);
                ethernetDialog.setTitle("Ethernet Information");
                ethernetDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                ethernetDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edtIp1 = (EditText) ethernetDialogView.findViewById(R.id.edt_ip1);
                        EditText edtIp2 = (EditText) ethernetDialogView.findViewById(R.id.edt_ip2);
                        EditText edtIp3 = (EditText) ethernetDialogView.findViewById(R.id.edt_ip3);
                        EditText edtIp4 = (EditText) ethernetDialogView.findViewById(R.id.edt_ip4);
                        EditText edtMask1 = (EditText) ethernetDialogView.findViewById(R.id.edt_mask1);
                        EditText edtMask2 = (EditText) ethernetDialogView.findViewById(R.id.edt_mask2);
                        EditText edtMask3 = (EditText) ethernetDialogView.findViewById(R.id.edt_mask3);
                        EditText edtMask4 = (EditText) ethernetDialogView.findViewById(R.id.edt_mask4);
                        EditText edtGt1 = (EditText) ethernetDialogView.findViewById(R.id.edt_gt1);
                        EditText edtGt2 = (EditText) ethernetDialogView.findViewById(R.id.edt_gt2);
                        EditText edtGt3 = (EditText) ethernetDialogView.findViewById(R.id.edt_gt3);
                        EditText edtGt4 = (EditText) ethernetDialogView.findViewById(R.id.edt_gt4);
                        EditText edtSubnet1 = (EditText) ethernetDialogView.findViewById(R.id.edt_subnet1);
                        EditText edtSubnet2 = (EditText) ethernetDialogView.findViewById(R.id.edt_subnet2);
                        EditText edtSubnet3 = (EditText) ethernetDialogView.findViewById(R.id.edt_subnet3);
                        EditText edtSubnet4 = (EditText) ethernetDialogView.findViewById(R.id.edt_subnet4);
                        EditText edtBroadcast1 = (EditText) ethernetDialogView.findViewById(R.id.edt_broadcast1);
                        EditText edtBroadcast2 = (EditText) ethernetDialogView.findViewById(R.id.edt_broadcast2);
                        EditText edtBroadcast3 = (EditText) ethernetDialogView.findViewById(R.id.edt_broadcast3);
                        EditText edtBroadcast4 = (EditText) ethernetDialogView.findViewById(R.id.edt_broadcast4);

                        FsuEthernetInfo fsuEthernetInfo = new FsuEthernetInfo();
                        byte ip[] = {(byte) Integer.parseInt(edtIp1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp4.getText().toString().trim())};
                        byte mask[] = {(byte) Integer.parseInt(edtMask1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtMask2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtMask3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtMask4.getText().toString().trim())};
                        byte gateway[] = {(byte) Integer.parseInt(edtGt1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtGt2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtGt3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtGt4.getText().toString().trim())};
                        byte subnet[] = {(byte) Integer.parseInt(edtSubnet1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtSubnet2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtSubnet3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtSubnet4.getText().toString().trim())};
                        byte broadcast[] = {(byte) Integer.parseInt(edtBroadcast1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtBroadcast2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtBroadcast3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtBroadcast4.getText().toString().trim())};
                        fsuEthernetInfo.setIp(ip);
                        fsuEthernetInfo.setMask(mask);
                        fsuEthernetInfo.setGateway(gateway);
                        fsuEthernetInfo.setSubnet(subnet);
                        fsuEthernetInfo.setBroadcast(broadcast);
                        bleGeneralFsuSdk.setEthernet(fsuEthernetInfo);
                    }
                });
                ethernetDialog.show();
                break;
            case 6:
                bleGeneralFsuSdk.getPpp();
                break;
            case 7:
                bleGeneralFsuSdk.getServer();
                break;
            case 8:
                AlertDialog.Builder serverDialog = new AlertDialog.Builder(context);
                final View serverDialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_server_info, null);
                serverDialog.setView(serverDialogView);
                serverDialog.setTitle("Server Information");
                serverDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                serverDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edtIp1 = (EditText) serverDialogView.findViewById(R.id.edt_ip1);
                        EditText edtIp2 = (EditText) serverDialogView.findViewById(R.id.edt_ip2);
                        EditText edtIp3 = (EditText) serverDialogView.findViewById(R.id.edt_ip3);
                        EditText edtIp4 = (EditText) serverDialogView.findViewById(R.id.edt_ip4);
                        EditText edtPort = (EditText) serverDialogView.findViewById(R.id.edt_port);
                        FsuServerInfo fsuServerInfo = new FsuServerInfo();
                        byte ip[] = {(byte) Integer.parseInt(edtIp1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp4.getText().toString().trim())};
                        fsuServerInfo.setIp(ip);
                        fsuServerInfo.setPort(Integer.valueOf(edtPort.getText().toString().trim()));
                        bleGeneralFsuSdk.setServer(fsuServerInfo);
                    }
                });
                serverDialog.show();
                break;
            case 9:
                bleGeneralFsuSdk.getWireless();
                break;
            case 10:
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
                        bleGeneralFsuSdk.setWireless(fsuWirelessInfo);
                    }
                });
                wirelessDialog.show();
                break;
            case 11:
                AlertDialog.Builder upgradeDialog = new AlertDialog.Builder(context);
                final View upgradeDialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_upgrade_info, null);
                upgradeDialog.setView(upgradeDialogView);
                upgradeDialog.setTitle("Upgrade Information");
                upgradeDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                upgradeDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edtIp1 = (EditText) upgradeDialogView.findViewById(R.id.edt_ip1);
                        EditText edtIp2 = (EditText) upgradeDialogView.findViewById(R.id.edt_ip2);
                        EditText edtIp3 = (EditText) upgradeDialogView.findViewById(R.id.edt_ip3);
                        EditText edtIp4 = (EditText) upgradeDialogView.findViewById(R.id.edt_ip4);
                        EditText edtName = (EditText) upgradeDialogView.findViewById(R.id.edt_name);

                        byte ip[] = {(byte) Integer.parseInt(edtIp1.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp2.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp3.getText().toString().trim()),
                                (byte) Integer.parseInt(edtIp4.getText().toString().trim())};
                        bleGeneralFsuSdk.setUpgrade(ip, edtName.getText().toString().trim().getBytes());
                    }
                });
                upgradeDialog.show();
                break;
            case 12:
                bleGeneralFsuSdk.getFsuTime();
                break;
            case 13:
                bleGeneralFsuSdk.setFsuTime(new Date());
                break;
            case 14:
                bleGeneralFsuSdk.restart(1);
                break;
            case 15:
                bleGeneralFsuSdk.getMotor();
                break;
            case 16:
                FsuMotorInfo fsuMotorInfo = new FsuMotorInfo();
                for (int i = 0; i < 8; i++) {
                    FsuMotorInfo.MotorInfo motorInfo = new FsuMotorInfo.MotorInfo();
                    motorInfo.setAction(FsuMotorInfo.MOTOR_NO_ACTION);
                    motorInfo.setNumber(i + 1);
                    motorInfo.setKeepTSec((i + 1) * 17);
                    motorInfo.setDelaySec(i);
                    motorInfo.setRunningTenMsec(100);
                    motorInfo.setWrite(true);
                    fsuMotorInfo.getMotorInfos().add(motorInfo);
                }
                bleGeneralFsuSdk.setMotor(fsuMotorInfo);
                break;
            case 17:
                bleGeneralFsuSdk.setCardUser(new FsuUserInfo());
                break;
            case 18:
                bleGeneralFsuSdk.setCardUser(new FsuUserInfo());
                break;
            case 19:
                bleGeneralFsuSdk.getCardUser(new FsuUserInfo.FsuUserOpenInfo());
                break;
            case 20:
                bleGeneralFsuSdk.reset(1);
                break;
            case 21:
                bleGeneralFsuSdk.getParameter();
                break;
            case 22:
                bleGeneralFsuSdk.setParameter(new FsuParameterInfo());
                break;
            case 23:
                bleGeneralFsuSdk.getDo();
                break;
            case 24:
                bleGeneralFsuSdk.setDo(new FsuDoInfo());
                break;
            case 25:
                bleGeneralFsuSdk.getFsuStatus();
                break;
            case 26:
                end.add(Calendar.HOUR_OF_DAY, 15);
                bleGeneralFsuSdk.openOnline(26, begin.getTime(), end.getTime(), 1);
                break;
            case 27:
                final List<Integer> motorChoices = new ArrayList<>();
                final String[] items = {"Motor1", "Motor2", "Motor3", "Motor4", "Motor5", "Motor6", "Motor7", "Motor8"};
                // 设置默认选中的选项，全为false默认均未选中
                final boolean initChoiceSets[] = {false, false, false, false, false, false, false, false};
                motorChoices.clear();
                AlertDialog.Builder actionMotorDialog = new AlertDialog.Builder(context);
                actionMotorDialog.setTitle("Action Motors");
                actionMotorDialog.setMultiChoiceItems(items, initChoiceSets,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    motorChoices.add(which);
                                } else {
                                    motorChoices.remove(which);
                                }
                            }
                        });
                actionMotorDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                actionMotorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FsuMotorInfo fsuMotorInfo = new FsuMotorInfo();
                        for (int i = 0; i < motorChoices.size(); i++) {
                            FsuMotorInfo.MotorInfo motorInfo = new FsuMotorInfo.MotorInfo();
                            motorInfo.setAction(FsuMotorInfo.MOTOR_OPEN);
                            motorInfo.setNumber(motorChoices.get(i) + 1);
                            motorInfo.setKeepTSec((i + 1) * 20);
                            motorInfo.setDelaySec(i);
                            motorInfo.setRunningTenMsec(100);
                            motorInfo.setWrite(false);
                            fsuMotorInfo.getMotorInfos().add(motorInfo);
                        }
                        bleGeneralFsuSdk.setMotor(fsuMotorInfo);
                    }
                });
                actionMotorDialog.show();
                break;
            case 28:
                FsuDoInfo fsuDoInfo = new FsuDoInfo();
                fsuDoInfo.getFsuDoActionInfoList().add(new FsuDoInfo.FsuDoActionInfo(1, 1));
                bleGeneralFsuSdk.actionDo(fsuDoInfo);
                break;
            default:
                break;
        }

        return "";
    }
}
