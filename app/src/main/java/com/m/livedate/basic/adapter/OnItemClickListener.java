package com.m.livedate.basic.adapter;

/**
 * createDate:2020/7/15
 *
 * @author:spc describe：recycler 点击事件
 */
public interface OnItemClickListener<Data> {
    /**
     * item 点击事件
     *
     * @param data     item数据
     * @param position item下标
     */
    void onItemClickListener(Data data, int position);

    /**
     * Item 长按事件
     *
     * @param data     item的数据
     * @param position item的下标
     */
    boolean onItemLongClick(Data data, int position);

}
