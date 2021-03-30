//package com.blefsu.sdk.sample;
//
//import android.content.Context;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import com.bleairswitch.sdk.AirSwitchSdk;
//import com.bleairswitch.sdk.data.AirSwitchBasicData;
//import com.bleairswitch.sdk.data.DataAddr;
//import com.bleairswitch.sdk.data.DataCmd;
//import com.bleairswitch.sdk.data.UserData;
//import com.bleairswitch.sdk.utils.HexUtil;
//
//public class AirSwitchCmdArray<T> {
//    private List<String> mStringList;
//
//    public AirSwitchCmdArray() {
//        mStringList = new ArrayList<>();
//        mStringList.add("获取通信地址");
//        mStringList.add("正向有功总电能");
//        mStringList.add("A相电压");
//        mStringList.add("A相电流");
//        mStringList.add("瞬时总有功功率");
//        mStringList.add("跳闸/合闸");
//        mStringList.add("广播校时");
//        mStringList.add("电表清零");
//        mStringList.add("设置设备地址");
//
//    }
//
//    public List<String> getStringList() {
//        return mStringList;
//    }
//
//    public String sendCmdArray(Context context, int cmd, final AirSwitchSdk airSwitchSdk) {
//        StringBuilder msg = new StringBuilder(64);
//        AirSwitchBasicData airSwitchBasicData = new AirSwitchBasicData();
//        byte[] address = new byte[]{0x01, 0x0, 0x0, 0x0, 0x0, 0x0};
//        switch (cmd) {
//            case 0:
//                airSwitchSdk.readAddress();
//                break;
//            case 2:
//                airSwitchBasicData.setCmd(DataCmd.READ_DATA_CMD);
//                airSwitchBasicData.setAddress(address);
//                airSwitchBasicData.setData(DataAddr.x00010102);
//                airSwitchSdk.readData(airSwitchBasicData);
//                break;
//            case 1:
//                airSwitchBasicData.setCmd(DataCmd.READ_DATA_CMD);
//                airSwitchBasicData.setAddress(address);
//                airSwitchBasicData.setData(DataAddr.x00000100);
//                airSwitchSdk.readData(airSwitchBasicData);
//                break;
//            case 3:
//                airSwitchBasicData.setCmd(DataCmd.READ_DATA_CMD);
//                airSwitchBasicData.setAddress(address);
//                airSwitchBasicData.setData(DataAddr.x00010202);
//                airSwitchSdk.readData(airSwitchBasicData);
//                break;
//            case 4:
//                airSwitchBasicData.setCmd(DataCmd.READ_DATA_CMD);
//                airSwitchBasicData.setAddress(address);
//                airSwitchBasicData.setData(DataAddr.x00000302);
//                airSwitchSdk.readData(airSwitchBasicData);
//                break;
//            case 5:
//                airSwitchSdk.tripSwitch(HexUtil.encodeHexStr(address), new UserData());
//                break;
//            case 6:
//                airSwitchSdk.broadcastTime(new Date());
//                break;
//            case 7:
//                UserData userData = new UserData();
////                userData.setPassword(new byte[]{2, 0, 0, 0});
//                airSwitchSdk.cleanLog(HexUtil.encodeHexStr(address), userData);
//                break;
//            case 8:
////                UserData userData = new UserData();
//////                userData.setPassword(new byte[]{2, 0, 0, 0});
////                airSwitchSdk.cleanLog(HexUtil.encodeHexStr(address), userData);
//                break;
//        }
//        return "";
//    }
//}
