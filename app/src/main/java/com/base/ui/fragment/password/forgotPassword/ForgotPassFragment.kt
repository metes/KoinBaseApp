package com.base.ui.fragment.password.forgotPassword

import android.os.Bundle
import com.base.R
import com.base.base.BaseFragment
import com.base.commons.Constants.accessToken
import com.base.commons.Constants.smsCode
import com.base.commons.Constants.userId
import com.base.databinding.FragmentForgotPassBinding
import com.base.model.retrofit.request.ForgotPassRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPassFragment : BaseFragment<FragmentForgotPassBinding, ForgotPassVM>() {

    override val layoutId: Int = R.layout.fragment_forgot_pass
    override val viewModel: ForgotPassVM by viewModel()

    override fun prepareViews() {  }

    override fun onBackAlternative(): (() -> Unit)? {
        return { navigateFragment(R.id.nav_action_to_login) }
    }

    override fun initHandler() {
        binding.handler = this
    }

    fun onSendClick() {
        hideKeyboard()
        val request = ForgotPassRequest(binding.edtUser.text.toString())
        viewModel.forgotPass(request)
    }

    override fun subscribe() {
        viewModel.forgotPassLiveData.observeThis {
            Bundle().apply {
                putString(userId, it.userid)
                putString(accessToken, it.token)
                putString(smsCode, it.verifyCode)
                navigateFragment(R.id.nav_action_to_smsCodeFragment, this)
            }
        }
    }

}
