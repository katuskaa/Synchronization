package diabetes.com.synchronization.communication

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.common.helpers.getISO_8601Date
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesRequestData
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesResponseBody
import diabetes.com.synchronization.communication.network.treatments.deleteTreatment.DeleteTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.deleteTreatment.DeleteTreatmentResponseBody
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsRequestData
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsResponseBody
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentResponseBody

class ApplicationViewModel : ViewModel() {

    val getTreatmentsLiveData = MutableLiveData<ResponseLiveData<Array<GetTreatmentsResponseBody>>>()
    val postTreatmentLiveData = MutableLiveData<ResponseLiveData<Array<PostTreatmentResponseBody>>>()
    val deleteTreatmentLiveData = MutableLiveData<ResponseLiveData<DeleteTreatmentResponseBody>>()
    val getEntriesLiveData = MutableLiveData<ResponseLiveData<Array<GetEntriesResponseBody>>>()

    fun runGetTreatmentsTransaction(count: Int) {
        val requestData = GetTreatmentsRequestData(count)
        BaseApplication.applicationServer.executeGetTreatmentsTransaction(requestData, getTreatmentsLiveData)
    }

    fun runPostTreatmentTransaction() {
        val date = getISO_8601Date()
        val requestData = PostTreatmentRequestData("", "Meal Bolus", "", 0, 0, getISO_8601Date(), 2.5f, "test poznamky")
        BaseApplication.applicationServer.executePostTreatmentTransaction(requestData, postTreatmentLiveData)
    }

    fun runDeleteTreatmentTransaction() {
        val requestData = DeleteTreatmentRequestData("5c31619728c9a813c81fd4ef")
        BaseApplication.applicationServer.executeDeleteTreatmentTransaction(requestData, deleteTreatmentLiveData)
    }

    fun runGetEntriesTransaction() {
        val requestData = GetEntriesRequestData()
        BaseApplication.applicationServer.executeGetEntriesTransaction(requestData, getEntriesLiveData)
    }

}