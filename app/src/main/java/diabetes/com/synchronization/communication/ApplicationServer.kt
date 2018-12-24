package diabetes.com.synchronization.communication

import android.arch.lifecycle.MutableLiveData
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.data.server.CommunicationChannel
import diabetes.com.synchronization.common.data.server.Server
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.communication.network.example.ExampleRequestData
import diabetes.com.synchronization.communication.network.example.ExampleResponseBody
import diabetes.com.synchronization.communication.network.example.ExampleTransaction

class ApplicationServer(override val httpChannel: CommunicationChannel) : Server(BuildConfig.SERVER_URL) {

    fun executeExampleTransaction(requestData: ExampleRequestData, liveData: MutableLiveData<ResponseLiveData<ExampleResponseBody>>) {
        super.executeTransaction(ExampleTransaction(this.serverUrl, requestData, liveData))
    }

}