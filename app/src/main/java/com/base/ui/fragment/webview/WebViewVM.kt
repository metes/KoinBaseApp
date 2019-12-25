package com.base.ui.fragment.webview

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.base.base.BaseViewModel
import com.base.network.APIClient

/**
Created by EiAppCompany
Project:
Monday-November-2019
2:16 PM
 **/
class WebViewVM(app: Application, client: APIClient) : BaseViewModel(app, client) {

    private var previousYPosition = 0

    var exitButtonVisibilityLiveData = MutableLiveData<Boolean>()

    fun updateScrollPosition(scrollY: Int) {
        val isScrollUp = previousYPosition > scrollY
        previousYPosition = scrollY
        if (exitButtonVisibilityLiveData.value != isScrollUp) {
            exitButtonVisibilityLiveData.postValue(isScrollUp)
        }
    }
}
