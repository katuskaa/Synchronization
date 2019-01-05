package diabetes.com.synchronization.communication

import android.arch.lifecycle.MutableLiveData
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.data.server.CommunicationChannel
import diabetes.com.synchronization.common.data.server.Server
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesRequestData
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesResponseBody
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesTransaction
import diabetes.com.synchronization.communication.network.treatments.deleteTreatment.DeleteTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.deleteTreatment.DeleteTreatmentResponseBody
import diabetes.com.synchronization.communication.network.treatments.deleteTreatment.DeleteTreatmentTransaction
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsRequestData
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsResponseBody
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsTransaction
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentResponseBody
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentTransaction

class ApplicationServer(override val httpChannel: CommunicationChannel) : Server(BuildConfig.SERVER_URL) {

    fun executeGetTreatmentsTransaction(requestData: GetTreatmentsRequestData, liveData: MutableLiveData<ResponseLiveData<Array<GetTreatmentsResponseBody>>>) {
        super.executeTransaction(GetTreatmentsTransaction(this.serverUrl, requestData, liveData))
    }

    fun executePostTreatmentTransaction(requestData: PostTreatmentRequestData, liveData: MutableLiveData<ResponseLiveData<Array<PostTreatmentResponseBody>>>) {
        super.executeTransaction(PostTreatmentTransaction(this.serverUrl, requestData, liveData))
    }

    fun executeDeleteTreatmentTransaction(requestData: DeleteTreatmentRequestData, liveData: MutableLiveData<ResponseLiveData<DeleteTreatmentResponseBody>>) {
        super.executeTransaction(DeleteTreatmentTransaction(this.serverUrl, requestData, liveData))
    }

    fun executeGetEntriesTransaction(requestData: GetEntriesRequestData, liveData: MutableLiveData<ResponseLiveData<Array<GetEntriesResponseBody>>>) {
        super.executeTransaction(GetEntriesTransaction(this.serverUrl, requestData, liveData))
    }

}