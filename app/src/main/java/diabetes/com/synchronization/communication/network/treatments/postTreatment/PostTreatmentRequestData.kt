package diabetes.com.synchronization.communication.network.treatments.postTreatment

import com.google.gson.annotations.SerializedName
import diabetes.com.synchronization.common.data.transaction.RequestData

open class PostTreatmentRequestData(val enteredBy: String, val eventType: String, val reason: String, val carbs: Int, val duration: Int, val createdAt: String, val insulin: Float, val notes: String) : RequestData() {

    override fun getRequestBody(): RequestBody = PostTreatmentRequestBody(this)

    class PostTreatmentRequestBody(requestData: PostTreatmentRequestData) : RequestBody() {

        @SerializedName("enteredBy")
        var enteredBy = requestData.enteredBy

        @SerializedName("eventType")
        var eventType = requestData.eventType

        @SerializedName("reason")
        var reason = requestData.reason

        @SerializedName("carbs")
        var carbs = requestData.carbs

        @SerializedName("duration")
        var duration = requestData.duration

        @SerializedName("created_at")
        var createdAt = requestData.createdAt

        @SerializedName("insulin")
        var insulin = requestData.insulin

        @SerializedName("notes")
        var notes = requestData.notes
    }
}