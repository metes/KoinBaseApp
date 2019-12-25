package com.base.di

import com.base.commons.SharedPrefHelper
import com.base.network.APIClient
import com.base.ui.activity.MainActivityVM
import com.base.ui.fragment.login.LoginVM
import com.base.ui.fragment.password.enterSMSCode.SMSCodeVM
import com.base.ui.fragment.password.forgotPassword.ForgotPassVM
import com.base.ui.fragment.password.renewPass.RenewPassVM
import com.base.ui.fragment.webview.WebViewVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  Koin (D.I.) icin gerekli olan modullerin tanimlanmasi
 */
object Modules {

    val mainActivityViewModelModule = module {
        viewModel { MainActivityVM(get(), get()) }
        single { SharedPrefHelper(get()) }
        single { APIClient() }
    }
    val loginViewModelModule = module {
        viewModel { LoginVM(get(), get()) }
    }
    val forgotPassModelModule = module {
        viewModel { ForgotPassVM(get(), get()) }
    }
    val renewPassViewModule = module {
        viewModel { RenewPassVM(get(), get()) }
    }
    val webViewModule = module {
        viewModel { WebViewVM(get(), get()) }
    }
    val smsViewModule = module {
        viewModel { SMSCodeVM(get(), get()) }
    }


}
