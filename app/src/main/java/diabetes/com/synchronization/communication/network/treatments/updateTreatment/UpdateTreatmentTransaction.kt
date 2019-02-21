package diabetes.com.synchronization.communication.network.treatments.updateTreatment

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Request
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.http.JsonArrayWithJsonObjectRequest
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import diabetes.com.synchronization.common.data.server.httpChannel.HttpServerTransaction
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import org.json.JSONArray

class UpdateTreatmentTransaction(serverUrl: String, requestData: UpdateTreatmentRequestData, responseLiveData: MutableLiveData<ResponseLiveData<Array<UpdateTreatmentResponseBody>>>) : HttpServerTransaction<Array<UpdateTreatmentResponseBody>, ResponseLiveData<Array<UpdateTreatmentResponseBody>>>(serverUrl, requestData, responseLiveData) {

    override fun getHttpRequest(): Request<*> = JsonArrayWithJsonObjectRequest(Request.Method.PUT, this.serverUrl.plus("treatments?token=").plus(BuildConfig.SERVER_URL_TOKEN), this.requestData.getRequestBody().toJson(), UpdateTreatmentResponseHandler())

    override fun onLoadingStarted() {
        val responseLiveData = ResponseLiveData<Array<UpdateTreatmentResponseBody>>()
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onSuccessResponse(responseBody: Array<UpdateTreatmentResponseBody>) {
        val responseLiveData = ResponseLiveData(responseBody)
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onErrorResponse(errorCode: Int) {
        val responseLiveData = ResponseLiveData<Array<UpdateTreatmentResponseBody>>(errorCode = errorCode)
        this.responseLiveData.postValue(responseLiveData)
    }

    inner class UpdateTreatmentResponseHandler : HttpResponseHandler<JSONArray, Array<UpdateTreatmentResponseBody>>() {

        override fun onSuccessResponse(responseBody: JSONArray) {
            this@UpdateTreatmentTransaction.onSuccessResponse(this.parseResponseBody(responseBody.toString(), Array<UpdateTreatmentResponseBody>::class.java))
        }

        override fun onErrorResponse(errorCode: Int) {
            this@UpdateTreatmentTransaction.onErrorResponse(errorCode)
        }

        override fun onUnauthorizedResponse() {
            BaseApplication.application.finishApplication()
        }
    }
}