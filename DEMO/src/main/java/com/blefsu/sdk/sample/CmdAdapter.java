package com.blefsu.sdk.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yanghaojie on 2017/8/9.
 */

public class CmdAdapter extends BaseAdapter {
    private List<String> cmds;
    private Context context;

    public CmdAdapter(List<String> cmds, Context context) {
        this.cmds = cmds;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cmds.size();
    }

    @Override
    public String getItem(int position) {
        return cmds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(context);
        convertView = _LayoutInflater.inflate(R.layout.adapter_cmd, null);
        if (convertView != null) {
            TextView txtCmd = (TextView) convertView.findViewById(R.id.txt_cmd);
            txtCmd.setText(cmds.get(position));
        }
        return convertView;
    }
}
