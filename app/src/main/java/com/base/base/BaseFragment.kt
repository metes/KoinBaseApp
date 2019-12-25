package com.base.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import com.base.R
import com.base.ui.activity.MainActivity
import org.jetbrains.anko.support.v4.alert

abstract class BaseFragment<BindingType : ViewDataBinding, out ViewModelType: BaseViewModel> : Fragment() {

    var TAG = this.javaClass.simpleName

    /**
     * ViewDataBinding tipindeki generic type, child'dan gonderiliyor.
     */
    lateinit var binding: BindingType
    /**
     * BaseViewModel tipindeki generic type, child'dan gonderiliyor
      */
    abstract val viewModel: ViewModelType
    /**
     * layoutId, child'da tanimlaniyor
      */
    protected abstract val layoutId: Int
    /**
     *  Fragment'a ozel view duzenlemeleri burada yapiliyor
     */
    abstract fun prepareViews()
    /**
     *  XML icerisindeki degiskenin tanimlanmasi
     */
    abstract fun initHandler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        prepareViews()
        prepareHandler()
        subscribe()
        subscribeErrors()

        return binding.root
    }

    private fun prepareHandler() {
        binding.lifecycleOwner = this
        initHandler()
    }

    override fun onStart() {
        super.onStart()
        setOnBackPressed(onBackAlternative()) // onBackAlternative = null // Add this
    }

    override fun onStop() {
        (activity as MainActivity?)?.showHideProgress(false)
        super.onStop()
    }

    open fun setOnBackPressed(onBackAlternative: (() -> Unit)?) {
        (activity as BaseActivity<*, *>).onBackPressAlternative = onBackAlternative
    }

    private fun subscribeErrors() {
        viewModel.networkErrorDetection.observeThis {
            alert {
                titleResource = R.string.hata
                message = it
                positiveButton(android.R.string.ok) { it.dismiss() }
            }.show()
        }
        viewModel.loadingDetection.observeThis {
            (activity as MainActivity?)?.showHideProgress(it)
        }
    }

    open fun subscribe() {
        timber("Not have a subscription")
    }

    open fun onBackAlternative(): (() -> Unit)? {
        timber("Not have a special onBack function")
        return null
    }

    @SuppressLint("BinaryOperationInTimber", "LogNotTimber")
    fun timber(message: String, objectsAr: Array<String>? = null) {
//        Timber.tag(TAG).d("TimberLog: $message", objectsAr)
        Log.d(TAG, "TimberLog: $message")
    }

    fun hideKeyboard() {
        context?.let {
            val imm = ContextCompat.getSystemService(it, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
        }
    }

    fun navigateFragment(
        navAction: Int,
        bundle: Bundle? = null,
        navOptions: NavOptions? = null,
        extras: FragmentNavigator.Extras? = null
    ) {
        (activity as MainActivity).navigateFragment(navAction, bundle, navOptions, extras)
    }

    /**
     *  Default visible
     */
    fun changeToolbarVisibility(visible: Boolean = false) {
        (activity as MainActivity).toolbarVisibility(visible)
    }

    fun <T>LiveData<T?>.observeThis(function: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer {
            it?.let {
                function(it)
            }
        })
    }
}
