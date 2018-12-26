package diabetes.com.synchronization.communication.network.entries.getEntries

import com.google.gson.annotations.SerializedName
import java.util.*

open class GetEntriesResponseBody(@SerializedName("_id") var id: String, @SerializedName("date") var date: Long, @SerializedName("dateString") var dateString: Date, @SerializedName("rssi") var rssi: Int, @SerializedName("device") var device: String, @SerializedName("direction") var direction: String, @SerializedName("rawbg") var rawbg: Int, @SerializedName("sgv") var sgv: Int, @SerializedName("type") var type: String)