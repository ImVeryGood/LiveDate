package com.m.livedate.mvvm.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.m.livedate.R;
import com.m.livedate.databinding.FragmentThirdBinding;
import com.m.livedate.mvvm.basic.base.BaseFragment;
import com.m.livedate.mvvm.basic.retrofit.ApiResponse;
import com.m.livedate.mvvm.ui.adapter.BinderAdapter;
import com.m.livedate.mvvm.ui.bean.ImageBen;
import com.m.livedate.mvvm.ui.bean.ListBean;
import com.m.livedate.mvvm.ui.model.MViewModel;
import com.m.livedate.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ThirdFragment extends BaseFragment<MViewModel, FragmentThirdBinding> {
    private ImageBen imageBen;
    private BinderAdapter binderAdapter;
    private List<Object> list;

    @Override
    protected void setDataBinding() {

    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_third;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        binderAdapter = new BinderAdapter();
        imageBen = new ImageBen();
        imageBen.setUrl("https://www.baidu.com/img/dong_e8b80aecc2ee2ab14545e57e1ee7642b.gif");
        dataBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        dataBinding.recycler.setAdapter(binderAdapter);
//        mViewModel.getListBeanData().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<ListBean.DataBean>>>() {
//            @Override
//            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
//                list.addAll(listApiResponse.getData());
//                list.add(1,imageBen);
//                binderAdapter.setNewInstance(list);
//            }
//        });

    }

    @Override
    protected void initListener() {
         binderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
             @Override
             public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                 ToastUtils.showShortToast(position+"");
             }
         });
    }

}