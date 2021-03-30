package com.blefsu.sdk.sample;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import com.blefsu.sdk.data.FsuGeneralPlusInfo;
import com.blefsu.sdk.sdk.BleGeneraFsuPlusSdk;

public class GeneralPlusCmdArray {
    private List<String> mStringList;

    public GeneralPlusCmdArray() {
        mStringList = new ArrayList<>();
        mStringList.add("连接FSU蓝牙模块");
        mStringList.add("断开FSU蓝牙模块");
        mStringList.add("控制电机");
        mStringList.add("获取扩展板信息");
        mStringList.add("设置扩展板信息");

    }

    public List<String> getStringList() {
        return mStringList;
    }

    public String sendCmdArray(Context context, int cmd, final BleGeneraFsuPlusSdk bleGeneraFsuPlusSdk) {
        StringBuilder msg = new StringBuilder(64);

        switch (cmd) {
            case 0:
                bleGeneraFsuPlusSdk.connect();
                break;
            case 1:
                bleGeneraFsuPlusSdk.disconnect();
                break;
            case 2:
                final EditText motorText = new EditText(context);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(context);
                inputDialog.setTitle("Action Motor").setView(motorText);
                inputDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                inputDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bleGeneraFsuPlusSdk.openMotor(Integer.valueOf(motorText.getText().toString().trim()));
                            }
                        }).show();
                break;
            case 3:
                bleGeneraFsuPlusSdk.getPlusInfo();
                break;
            case 4:
                final EditText plusInfoText = new EditText(context);
                AlertDialog.Builder plusInputDialog = new AlertDialog.Builder(context);
                plusInputDialog.setTitle("Set Plus Info").setView(plusInfoText);
                plusInputDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                plusInputDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FsuGeneralPlusInfo fsuGeneralPlusInfo = new FsuGeneralPlusInfo();
                                fsuGeneralPlusInfo.setPlusAddr(Integer.valueOf(plusInfoText.getText().toString().trim()));
                                bleGeneraFsuPlusSdk.setPlusInfo(fsuGeneralPlusInfo);
                            }
                        }).show();
                break;
            default:
                break;
        }
        return "";
    }
}
