package diabetes.com.synchronization.communication.network.treatments.deleteTreatment

import com.google.gson.annotations.SerializedName
import diabetes.com.synchronization.common.data.transaction.RequestData

open class DeleteTreatmentRequestData(val id: String) : RequestData() {

    override fun getRequestBody(): RequestBody = DeleteTreatmentRequestBody(this)

    class DeleteTreatmentRequestBody(requestData: DeleteTreatmentRequestData) : RequestBody() {

        @SerializedName("_id")
        var id = requestData.id
    }
}