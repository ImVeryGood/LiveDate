package com.m.mvp_kotlin.mvvm.base.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.m.mvp_kotlin.mvvm.base.ext.dismissLoadingExt
import com.m.mvp_kotlin.mvvm.base.ext.showLoadingExt
import com.m.mvp_kotlin.mvvm.base.net.AppConstant
import com.m.mvp_kotlin.mvvm.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType


/**
 * createDate:2021/3/29
 * @author:spc
 * @describe：
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM

    lateinit var mDataBinding: DB

    abstract fun layoutId(): Int
    abstract fun create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        observeLoading()
        create()
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(getVmClazz(this))
    }


    private fun initBinding() {
        mDataBinding = DataBindingUtil.setContentView(this, layoutId())
        mDataBinding.lifecycleOwner = this
        mViewModel = createViewModel();
    }

    private fun observeLoading() {
        mViewModel.run {
            isShowLoading.observe(this@BaseActivity, Observer {
                if (it) {
                    showLoadingExt()
                } else {
                    dismissLoadingExt()
                }
            })
        }
    }

    /**
     * 获取当前类绑定的泛型ViewModel-clazz
     */
    private fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }
}