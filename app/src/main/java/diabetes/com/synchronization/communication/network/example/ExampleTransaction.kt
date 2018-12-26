package diabetes.com.synchronization.communication.network.example

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Request
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.http.JsonObjectRequest
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import diabetes.com.synchronization.common.data.server.httpChannel.HttpServerTransaction
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import org.json.JSONObject

class ExampleTransaction(serverUrl: String, requestData: ExampleRequestData, responseLiveData: MutableLiveData<ResponseLiveData<ExampleResponseBody>>) : HttpServerTransaction<ExampleResponseBody, ResponseLiveData<ExampleResponseBody>>(serverUrl, requestData, responseLiveData) {

    override fun getHttpRequest(): Request<*> = JsonObjectRequest(Request.Method.POST, this.serverUrl.plus("examples"), this.requestData.getRequestBody().toJson(), ExampleResponseHandler(), BaseApplication.application)

    override fun onLoadingStarted() {
        val responseLiveData = ResponseLiveData<ExampleResponseBody>()
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onSuccessResponse(responseBody: ExampleResponseBody) {
        val responseLiveData = ResponseLiveData(responseBody)
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onErrorResponse(errorCode: Int) {
        val responseLiveData = ResponseLiveData<ExampleResponseBody>(errorCode = errorCode)
        this.responseLiveData.postValue(responseLiveData)
    }

    inner class ExampleResponseHandler : HttpResponseHandler<JSONObject, ExampleResponseBody>() {

        override fun onSuccessResponse(responseBody: JSONObject) {
            this@ExampleTransaction.onSuccessResponse(this.parseResponseBody(responseBody.toString(), ExampleResponseBody::class.java))
        }

        override fun onErrorResponse(errorCode: Int) {
            this@ExampleTransaction.onErrorResponse(errorCode)
        }

        override fun onUnauthorizedResponse() {
            BaseApplication.application.finishApplication()
        }
    }
}