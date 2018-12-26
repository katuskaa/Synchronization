package diabetes.com.synchronization.communication

import android.arch.lifecycle.MutableLiveData
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.data.server.CommunicationChannel
import diabetes.com.synchronization.common.data.server.Server
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesRequestData
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesResponseBody
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesTransaction
import diabetes.com.synchronization.communication.network.example.ExampleRequestData
import diabetes.com.synchronization.communication.network.example.ExampleResponseBody
import diabetes.com.synchronization.communication.network.example.ExampleTransaction
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsRequestData
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsResponseBody
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsTransaction

class ApplicationServer(override val httpChannel: CommunicationChannel) : Server(BuildConfig.SERVER_URL) {

    fun executeExampleTransaction(requestData: ExampleRequestData, liveData: MutableLiveData<ResponseLiveData<ExampleResponseBody>>) {
        super.executeTransaction(ExampleTransaction(this.serverUrl, requestData, liveData))
    }

    fun executeGetTreatmentsTransaction(requestData: GetTreatmentsRequestData, liveData: MutableLiveData<ResponseLiveData<Array<GetTreatmentsResponseBody>>>) {
        super.executeTransaction(GetTreatmentsTransaction(this.serverUrl, requestData, liveData))
    }

    fun executeGetEntriesTransaction(requestData: GetEntriesRequestData, liveData: MutableLiveData<ResponseLiveData<Array<GetEntriesResponseBody>>>) {
        super.executeTransaction(GetEntriesTransaction(this.serverUrl, requestData, liveData))
    }

}