package com.base.ui.fragment.login

import android.os.Bundle
import android.text.Editable
import com.base.BuildConfig
import com.base.R
import com.base.base.BaseFragment
import com.base.commons.SharedPrefHelper
import com.base.databinding.FragmentLoginBinding
import com.base.model.retrofit.request.LoginRequest
import com.base.model.retrofit.response._loginAuth.LoginResponse
import com.base.ui.fragment.webview.ARG_WEB_URL
//import com.onesignal.OneSignal
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginVM>() {

    override val layoutId: Int = R.layout.fragment_login
    override val viewModel by viewModel<LoginVM>()

    private val sharedPrefHelper: SharedPrefHelper by inject()

    override fun prepareViews() {
//        timber(OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId)
    }

    override fun initHandler() {
        binding.handler = this
    }

    override fun subscribe() {
        viewModel.loginLiveData.observeThis { loginUser(it) }
    }

    override fun onBackAlternative(): (() -> Unit)? {
        return { activity?.finish() }
    }

    private fun loginUser(it: LoginResponse) {
        hideKeyboard()
        sharedPrefHelper.apply {
            saveLoginState(true)
            saveToken(it.access_token)
        }
        Bundle().apply {
            putString(ARG_WEB_URL, BuildConfig.WEBVIEW_STARTUP_URL)
            navigateFragment(R.id.nav_action_to_WebViewFragment, this)
        }
    }

    fun onLoginClick(user: Editable, pass: Editable) {
        viewModel.login(LoginRequest(user.toString(), pass.toString()))
    }

    fun onCreateUserClick() {
        Bundle().apply {
            putString(ARG_WEB_URL, BuildConfig.WEBVIEW_SIGNUP_URL)
            navigateFragment(R.id.nav_action_to_WebViewFragment, this)
        }
    }

    fun onForgotPassClick() {
        navigateFragment(R.id.nav_action_to_forgot_password)
    }

}
