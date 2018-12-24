package diabetes.com.synchronization.communication.network.example

import com.google.gson.annotations.SerializedName

open class ExampleResponseBody(
        @SerializedName("example") var example: String
)