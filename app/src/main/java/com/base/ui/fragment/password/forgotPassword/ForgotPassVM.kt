package com.base.ui.fragment.password.forgotPassword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.base.base.BaseViewModel
import com.base.model.retrofit.request.ForgotPassRequest
import com.base.model.retrofit.response.forgotPassword.ForgotPasswordResponse
import com.base.network.APIClient
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ForgotPassVM(app: Application, private val client: APIClient) : BaseViewModel(app, client) {

    val forgotPassLiveData = MutableLiveData<ForgotPasswordResponse>()

    @ExperimentalCoroutinesApi
    fun forgotPass(request: ForgotPassRequest) {
        forgotPassLiveData.sendRequest { client.forgotPassClient(request) }

//         flow Test
//        viewModelScope.launch {
//            sendFlowRequest(
//                { client.forgotPassClient(request) },
//                { client.forgotPassClient(request) },
//                { client.forgotPassClient(request) },
//                { client.forgotPassClient(request) },
//                { client.forgotPassClient(request) },
//                { client.forgotPassClient(request) },
//                { client.forgotPassClient(request) }
//            ).collect {
//                if (it is Result<GenericResponse<ForgotPasswordResponse>>) {
//                    Log.d("ForgotPassVM", "ForgotPassVM collect it is Result: " + it.getOrNull()?.isSuccess)
//                } else {
//                    Log.d("ForgotPassVM", "ForgotPassVM collect it is NOT Result: " + it.getOrNull()?.isSuccess)
//                }
//            }
//        }

    }


}