package diabetes.com.synchronization.communication.providers

import android.arch.lifecycle.MutableLiveData
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.communication.network.example.ExampleRequestData
import diabetes.com.synchronization.communication.network.example.ExampleResponseBody

interface ApplicationDataProvider {

    fun connect()
    fun disconnect()
    fun isConnected(): Boolean

    fun sendExample(requestData: ExampleRequestData, liveData: MutableLiveData<ResponseLiveData<ExampleResponseBody>>)

}