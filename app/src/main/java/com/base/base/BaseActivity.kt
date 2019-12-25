package com.base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<BindingType : ViewDataBinding, ViewModelType : BaseViewModel> :
    AppCompatActivity() {

    lateinit var binding: BindingType
    abstract fun initHandler()
    abstract fun initViews()
    protected abstract val viewModel: ViewModelType
    protected abstract val layoutId: Int
    var onBackPressAlternative: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        initHandler()
        initViews()
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun onBackPressed() {
        if (onBackPressAlternative != null) {
            onBackPressAlternative!!()
        } else {
            super.onBackPressed()
        }
    }
}