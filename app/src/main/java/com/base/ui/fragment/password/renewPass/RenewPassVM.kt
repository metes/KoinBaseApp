package com.base.ui.fragment.password.renewPass

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.base.base.BaseViewModel
import com.base.model.retrofit.request.RenewPassRequest
import com.base.network.APIClient

class RenewPassVM(app: Application, private val client: APIClient) : BaseViewModel(app, client) {

    val renewPassLiveData = MutableLiveData<String>()

    fun renewPass(request: RenewPassRequest) {
        renewPassLiveData.sendRequest { client.renewPassClient(request) }
    }


}