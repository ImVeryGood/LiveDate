package com.m.livedate.kotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.m.livedate.R
import kotlinx.android.synthetic.main.activity_k.*

class KActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_k)
        init();

    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        txt.text = "66666"
        txt.setTextColor(resources.getColor(R.color.textRed));
//        相当于new
        var mKotlin = MKotlin();
        mKotlin.setInterFace(object : MKotlin.M {
            override fun gM(string: String) {

            }
        })

    }

    fun largeNum(num1: Int, num2: Int): Int = if (num1 > num2) num1 else num2;
}