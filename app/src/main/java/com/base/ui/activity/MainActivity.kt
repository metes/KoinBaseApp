package com.base.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.base.R
import com.base.base.BaseActivity
import com.base.base.BaseViewModel
import com.base.commons.AnimUtils.startFadeInAnimation
import com.base.commons.AnimUtils.startFadeOutAnimation
import com.base.commons.visibileIf
import com.base.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override val viewModel: BaseViewModel by inject()
    override val layoutId = R.layout.activity_main

    override fun initHandler() {
        binding.handler = this
    }

    override fun initViews() {}

    /**
     *  Navigation'daki fragment degisim action'larini kullanabilmemi saglayan method.
     *  Opsiyonel olarak parametre alabilir
     */
    fun navigateFragment(
        navAction: Int,
        bundle: Bundle? = null,
        navOptions: NavOptions? = null,
        extras: FragmentNavigator.Extras? = null
    ) {
        getCurrentFragment()?.let {
            NavHostFragment.findNavController(it).navigate(
                navAction,
                bundle, // Bundle of args
                navOptions, // NavOptions
                extras
            )
            return
        }
    }

    private fun getCurrentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    fun toolbarVisibility(visible: Boolean) {
        binding.toolbar.visibileIf(visible)
    }

    fun showHideProgress(isShowing: Boolean) {
        if (isShowing) {
            startFadeInAnimation(binding.containerProgress, 300L)
        } else {
            startFadeOutAnimation(binding.containerProgress, 300L)
        }
    }

}
