package com.base.ui.fragment.splash

import com.base.R
import com.base.base.BaseFragment
import com.base.commons.SharedPrefHelper
import com.base.commons.splashAnimation
import com.base.databinding.FragmentSplashBinding
import com.base.ui.fragment.login.LoginVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, LoginVM>() {

    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: LoginVM by viewModel()

    private val sharedPrefHelper: SharedPrefHelper by inject()

    override fun prepareViews() {
        changeToolbarVisibility()
        binding.imageViewLogo.splashAnimation(::onLogoAnimationEnd)
    }

    override fun initHandler() { }

    /**
     *  Animasyon bitiminde calistirilacak
     */
    private fun onLogoAnimationEnd() {
        if (sharedPrefHelper.loadLoginState()) {
            navigateFragment(R.id.nav_action_to_WebViewFragment)
        } else {
            navigateFragment(R.id.nav_action_splash_to_login)
        }
    }

}

