package com.base.ui.fragment.password.renewPass

import com.base.R
import com.base.base.BaseFragment
import com.base.commons.Constants.accessToken
import com.base.commons.Constants.userId
import com.base.databinding.FragmentRenewPassBinding
import com.base.model.retrofit.request.RenewPassRequest
import org.jetbrains.anko.support.v4.alert
import org.koin.androidx.viewmodel.ext.android.viewModel

class RenewPassFragment : BaseFragment<FragmentRenewPassBinding, RenewPassVM>() {

    override val layoutId: Int = R.layout.fragment_renew_pass
    override val viewModel: RenewPassVM by viewModel()

    override fun prepareViews() { }

    override fun onBackAlternative(): (() -> Unit)? {
        return { navigateFragment(R.id.nav_action_to_forgot_password) }
    }

    override fun initHandler() {
        binding.handler = this
    }

    fun onSendClick() {
        hideKeyboard()
        val request = RenewPassRequest(
            binding.edtPass1.text.toString(),
            binding.edtPass2.text.toString(),
            arguments?.getString(accessToken)?: "",
            arguments?.getString(userId)?: ""
        )
        viewModel.renewPass(request)
    }

    override fun subscribe() {
        viewModel.renewPassLiveData.observeThis {
            alert {
                titleResource = R.string.sifre_degistir
                message = it
                positiveButton(android.R.string.ok) {
                    navigateFragment(R.id.nav_action_to_login)
                }
            }.show()
        }
    }

}
