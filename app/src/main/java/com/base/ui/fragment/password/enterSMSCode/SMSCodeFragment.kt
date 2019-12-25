package com.base.ui.fragment.password.enterSMSCode

import android.os.Bundle
import com.base.R
import com.base.base.BaseFragment
import com.base.commons.Constants.smsCode
import com.base.databinding.FragmentEnterSmsCodeBinding
import org.jetbrains.anko.support.v4.alert
import org.koin.androidx.viewmodel.ext.android.viewModel

class SMSCodeFragment : BaseFragment<FragmentEnterSmsCodeBinding, SMSCodeVM>() {

    override val layoutId: Int = R.layout.fragment_enter_sms_code
    override val viewModel: SMSCodeVM by viewModel()

    override fun prepareViews() { }

    override fun initHandler() {
        binding.handler = this
    }

    override fun onBackAlternative(): (() -> Unit)? {
        return { navigateFragment(R.id.nav_action_to_forgot_password) }
    }

    fun onSendClick() {
        hideKeyboard()
        if (isValidCode()) {
            Bundle().apply {
                putAll(arguments)
                navigateFragment(R.id.nav_action_to_renew_password, this)
            }
        } else {
            alert {
                titleResource = R.string.hata
                messageResource = R.string.giris_bilgilerini_kontrol_ediniz
                positiveButton(android.R.string.ok) {
                    navigateFragment(R.id.nav_action_to_forgot_password)
                }
            }.show()
        }
    }

    private fun isValidCode(): Boolean {
        return arguments?.getString(smsCode) == binding.edtCode.text.toString()
                && binding.edtCode.text.toString().isNotBlank()
    }

}
