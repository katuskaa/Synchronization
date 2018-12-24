package diabetes.com.synchronization.communication.network.example

import diabetes.com.synchronization.common.data.transaction.RequestData

open class ExampleRequestData(/* parameters */) : RequestData() {

    override fun getRequestBody(): RequestBody = ExampleRequestBody(this)

    class ExampleRequestBody(requestData: ExampleRequestData) : RequestBody()

}