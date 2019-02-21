package diabetes.com.synchronization.communication.network.treatments.postTreatment

import com.google.gson.annotations.SerializedName
import diabetes.com.synchronization.common.data.transaction.RequestData

open class PostTreatmentRequestData(val enteredBy: String, val eventType: String, val carbs: Float, val createdAt: String, val insulin: Float, val notes: String, val reason: String = "", val duration: Int = 0) : RequestData() {

    override fun getRequestBody(): RequestBody = PostTreatmentRequestBody(this)

    class PostTreatmentRequestBody(requestData: PostTreatmentRequestData) : RequestBody() {

        @SerializedName("enteredBy")
        val enteredBy = requestData.enteredBy

        @SerializedName("eventType")
        val eventType = requestData.eventType

        @SerializedName("reason")
        val reason = requestData.reason

        @SerializedName("carbs")
        val carbs = requestData.carbs

        @SerializedName("duration")
        val duration = requestData.duration

        @SerializedName("created_at")
        val createdAt = requestData.createdAt

        @SerializedName("insulin")
        val insulin = requestData.insulin

        @SerializedName("notes")
        val notes = requestData.notes
    }
}