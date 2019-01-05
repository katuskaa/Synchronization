package diabetes.com.synchronization.communication.network.treatments.postTreatment

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Request
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.http.JsonArrayWithJsonObjectRequest
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import diabetes.com.synchronization.common.data.server.httpChannel.HttpServerTransaction
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import org.json.JSONArray

class PostTreatmentTransaction(serverUrl: String, requestData: PostTreatmentRequestData, responseLiveData: MutableLiveData<ResponseLiveData<Array<PostTreatmentResponseBody>>>) : HttpServerTransaction<Array<PostTreatmentResponseBody>, ResponseLiveData<Array<PostTreatmentResponseBody>>>(serverUrl, requestData, responseLiveData) {

    override fun getHttpRequest(): Request<*> = JsonArrayWithJsonObjectRequest(Request.Method.POST, this.serverUrl.plus("treatments?token=").plus(BuildConfig.SERVER_URL_TOKEN), this.requestData.getRequestBody().toJson(), PostTreatmentResponseHandler())

    override fun onLoadingStarted() {
        val responseLiveData = ResponseLiveData<Array<PostTreatmentResponseBody>>()
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onSuccessResponse(responseBody: Array<PostTreatmentResponseBody>) {
        val responseLiveData = ResponseLiveData(responseBody)
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onErrorResponse(errorCode: Int) {
        val responseLiveData = ResponseLiveData<Array<PostTreatmentResponseBody>>(errorCode = errorCode)
        this.responseLiveData.postValue(responseLiveData)
    }

    inner class PostTreatmentResponseHandler : HttpResponseHandler<JSONArray, Array<PostTreatmentResponseBody>>() {

        override fun onSuccessResponse(responseBody: JSONArray) {
            this@PostTreatmentTransaction.onSuccessResponse(this.parseResponseBody(responseBody.toString(), Array<PostTreatmentResponseBody>::class.java))
        }

        override fun onErrorResponse(errorCode: Int) {
            this@PostTreatmentTransaction.onErrorResponse(errorCode)
        }

        override fun onUnauthorizedResponse() {
            BaseApplication.application.finishApplication()
        }
    }
}