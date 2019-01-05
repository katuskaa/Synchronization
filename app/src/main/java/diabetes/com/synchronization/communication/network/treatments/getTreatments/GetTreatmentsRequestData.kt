package diabetes.com.synchronization.communication.network.treatments.getTreatments

import com.google.gson.annotations.SerializedName
import diabetes.com.synchronization.common.data.transaction.RequestData

open class GetTreatmentsRequestData(val count: Int) : RequestData() {

    override fun getRequestBody(): RequestBody = GetTreatmentsRequestBody(this)

    class GetTreatmentsRequestBody(requestData: GetTreatmentsRequestData) : RequestBody() {
        @SerializedName("count")
        val count = requestData.count
    }
}