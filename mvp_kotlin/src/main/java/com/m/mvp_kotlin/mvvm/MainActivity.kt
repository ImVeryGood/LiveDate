package com.m.mvp_kotlin.mvvm

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.m.mvp_kotlin.R
import com.m.mvp_kotlin.databinding.ActivityMain2Binding
import com.m.mvp_kotlin.mvvm.base.ext.dismissLoadingExt
import com.m.mvp_kotlin.mvvm.base.ext.showLoadingExt
import com.m.mvp_kotlin.mvvm.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main2.*

@Suppress("UNREACHABLE_CODE")
class MainActivity : BaseActivity<MainViewModel, ActivityMain2Binding>() {
    lateinit var name: TextView
    private val TAG: String = "SSSSSSSSSSSSSS"
    override fun layoutId() = R.layout.activity_main2;
    override fun create() {
        observe();

    }

    private fun observe() {
        mViewModel.run {
            textRigger.observe(this@MainActivity, Observer {
                dismissLoadingExt()
                text.text = it;
                Log.d(TAG, "create: $it")
            })

            listArticle.observe(this@MainActivity, Observer {
                Log.d(TAG, "observe: "+it[0].imgUrl)
            })
            list.observe(this@MainActivity, Observer {
                text2.text = it?.get(0)
                Log.d(TAG, "observe: $it")
            })
        }

    }

    fun getClick(view: View) {
        mViewModel.getNetData("index")

    }
}