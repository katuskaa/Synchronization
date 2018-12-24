package diabetes.com.synchronization.communication.providers

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import diabetes.com.synchronization.communication.ApplicationServer
import diabetes.com.synchronization.common.data.server.httpChannel.HttpChannel
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.communication.network.example.ExampleRequestData
import diabetes.com.synchronization.communication.network.example.ExampleResponseBody

class DataProvider(val context: Context) : ApplicationDataProvider {

    private val applicationServer: ApplicationServer = ApplicationServer(HttpChannel())

    override fun connect() = applicationServer.connect(context)

    override fun disconnect() = applicationServer.disconnect()

    override fun isConnected(): Boolean = applicationServer.isConnected()

    override fun sendExample(requestData: ExampleRequestData, liveData: MutableLiveData<ResponseLiveData<ExampleResponseBody>>) {
        applicationServer.executeExampleTransaction(requestData, liveData)
    }

}