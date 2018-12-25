package diabetes.com.synchronization.communication

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.communication.network.example.ExampleRequestData
import diabetes.com.synchronization.communication.network.example.ExampleResponseBody

class ApplicationViewModel : ViewModel() {

    val exampleLiveData = MutableLiveData<ResponseLiveData<ExampleResponseBody>>()

    fun runExampleTransaction() {
        val requestData = ExampleRequestData("example")
        BaseApplication.applicationServer.executeExampleTransaction(requestData, exampleLiveData)
    }
}