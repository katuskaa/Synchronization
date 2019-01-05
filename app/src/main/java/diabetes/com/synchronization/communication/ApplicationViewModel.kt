package diabetes.com.synchronization.communication

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesRequestData
import diabetes.com.synchronization.communication.network.entries.getEntries.GetEntriesResponseBody
import diabetes.com.synchronization.communication.network.treatments.deleteTreatment.DeleteTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.deleteTreatment.DeleteTreatmentResponseBody
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsRequestData
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsResponseBody
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentResponseBody
import java.util.*

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
        val requestData = PostTreatmentRequestData("", "Meal Bolus", "", 0, 0, Date(), 2.5f, "test poznamky")
        BaseApplication.applicationServer.executePostTreatmentTransaction(requestData, postTreatmentLiveData)
    }

    fun runDeleteTreatmentTransaction() {
        val requestData = DeleteTreatmentRequestData("5c309bc028c9a813c819d0a6")
        BaseApplication.applicationServer.executeDeleteTreatmentTransaction(requestData, deleteTreatmentLiveData)
    }

    fun runGetEntriesTransaction() {
        val requestData = GetEntriesRequestData()
        BaseApplication.applicationServer.executeGetEntriesTransaction(requestData, getEntriesLiveData)
    }

}