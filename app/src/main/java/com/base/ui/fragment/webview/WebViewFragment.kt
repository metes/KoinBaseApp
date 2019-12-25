package com.base.ui.fragment.webview

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.*
import android.widget.LinearLayout
import com.base.BuildConfig
import com.base.R
import com.base.base.BaseActivity
import com.base.base.BaseFragment
import com.base.commons.SharedPrefHelper
import com.base.commons.visibileIf
import com.base.databinding.FragmentWebviewBinding
import kotlinx.android.synthetic.main.fragment_webview.*
import org.jetbrains.anko.support.v4.alert
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


const val ARG_WEB_URL = "ARG_WEB_URL"

class WebViewFragment : BaseFragment<FragmentWebviewBinding, WebViewVM>() {


    override val layoutId: Int = R.layout.fragment_webview
    override val viewModel: WebViewVM by viewModel()

    private val sharedPrefHelper: SharedPrefHelper by inject()

    override fun prepareViews() {
        hideKeyboard()
        addStatusBar()
        loadWebView(getWebViewURL())
    }

    private fun getWebViewURL(): String {
        return BuildConfig.WEBVIEW_STARTUP_URL
    }

    override fun subscribe() {
        viewModel.exitButtonVisibilityLiveData.observeThis {
            binding.fabExit.visibileIf(it)
        }
    }

    override fun onBackAlternative(): (() -> Unit)? {
        return {
            when (myWebView.url) {
                BuildConfig.WEBVIEW_STARTUP_URL -> activity?.finish()
                else -> myWebView.goBack()
            }
        }
    }

    private fun addStatusBar() {
        val params = LinearLayout.LayoutParams(
            MATCH_PARENT,
            (activity as BaseActivity<*, *>).getStatusBarHeight()
        )
        binding.statusBarSizeView.layoutParams = params
    }

    override fun initHandler() {
        binding.handler = this
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadWebView(url: String) {
        viewModel.loadingDetection.postValue(true)
        binding.myWebView.apply {
            settings.javaScriptEnabled = true
            loadUrl(url + sharedPrefHelper.loadToken())

            webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    viewModel.loadingDetection.postValue(true)
                    view.loadUrl(request.url.toString())
                    timber("Web URL: " + request.url.toString())
                    return false
                }

                override fun onPageFinished(view: WebView, url: String) {
                    viewModel.loadingDetection.postValue(false)
                }

            }

            webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(
                    view: WebView,
                    url: String,
                    message: String,
                    result: JsResult
                ): Boolean {
                    showAlertDialog(message, result, view)
                    return true
                }

                private fun showAlertDialog(dialogMessage: String, result: JsResult, view: WebView) {
                    alert {
                        titleResource = R.string.app_name
                        message = dialogMessage
                        positiveButton(R.string.tamam) {
                            result.confirm()
                        }
                    }.show()
                }
            }

            viewTreeObserver.addOnScrollChangedListener { viewModel.updateScrollPosition(scrollY) }
        }
    }

    fun onClickExit() {
        if (getWebViewURL() == BuildConfig.WEBVIEW_STARTUP_URL) {
            alert {
                titleResource = R.string.uyari
                messageResource = R.string.cikis_yapmak_istediginizden__
                positiveButton(R.string.cikis_yap) {
                    sharedPrefHelper.saveToken("")
                    sharedPrefHelper.saveLoginState(false)
                    navigateFragment(R.id.nav_action_to_login)
                }
                negativeButton(R.string.vazgec) {}
            }.show()
        } else {
            navigateFragment(R.id.nav_action_to_login)
        }
    }

}
