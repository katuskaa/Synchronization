package diabetes.com.synchronization.communication.network.example

import com.google.gson.annotations.SerializedName
import diabetes.com.synchronization.common.data.transaction.RequestData

open class ExampleRequestData(val example: String) : RequestData() {

    override fun getRequestBody(): RequestBody = ExampleRequestBody(this)

    class ExampleRequestBody(requestData: ExampleRequestData) : RequestBody() {

        @SerializedName("example")
        val title = requestData.example
    }

}