package diabetes.com.synchronization.communication.network.treatments.updateTreatment

import com.google.gson.annotations.SerializedName

open class UpdateTreatmentResponseBody(@SerializedName("_id") var id: String, @SerializedName("enteredBy") var enteredBy: String, @SerializedName("eventType") var eventType: String, @SerializedName("reason") var reason: String, @SerializedName("carbs") var carbs: Int, @SerializedName("duration") var duration: Int, @SerializedName("created_at") var createdAt: String, @SerializedName("insulin") var insulin: Float, @SerializedName("notes") var notes: String)