package diabetes.com.synchronization.communication.network.example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData

class ExampleViewModel : ViewModel() {

    val liveData = MutableLiveData<ResponseLiveData<ExampleResponseBody>>()

    fun runExampleTransaction() {
        val requestData = ExampleRequestData()
        BaseApplication.applicationDataProvider.sendExample(requestData, liveData)
    }
}