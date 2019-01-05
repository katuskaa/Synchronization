package diabetes.com.synchronization.communication.network.treatments.deleteTreatment

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Request
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.http.JsonObjectRequest
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import diabetes.com.synchronization.common.data.server.httpChannel.HttpServerTransaction
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import org.json.JSONObject

class DeleteTreatmentTransaction(serverUrl: String, requestData: DeleteTreatmentRequestData, responseLiveData: MutableLiveData<ResponseLiveData<DeleteTreatmentResponseBody>>) : HttpServerTransaction<DeleteTreatmentResponseBody, ResponseLiveData<DeleteTreatmentResponseBody>>(serverUrl, requestData, responseLiveData) {

    override fun getHttpRequest(): Request<*> = JsonObjectRequest(Request.Method.DELETE, this.serverUrl.plus("treatments/").plus((requestData as DeleteTreatmentRequestData).id).plus("?token=").plus(BuildConfig.SERVER_URL_TOKEN), this.requestData.getRequestBody().toJson(), DeleteTreatmentResponseHandler())

    override fun onLoadingStarted() {
        val responseLiveData = ResponseLiveData<DeleteTreatmentResponseBody>()
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onSuccessResponse(responseBody: DeleteTreatmentResponseBody) {
        val responseLiveData = ResponseLiveData(responseBody)
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onErrorResponse(errorCode: Int) {
        val responseLiveData = ResponseLiveData<DeleteTreatmentResponseBody>(errorCode = errorCode)
        this.responseLiveData.postValue(responseLiveData)
    }

    inner class DeleteTreatmentResponseHandler : HttpResponseHandler<JSONObject, DeleteTreatmentResponseBody>() {

        override fun onSuccessResponse(responseBody: JSONObject) {
            this@DeleteTreatmentTransaction.onSuccessResponse(this.parseResponseBody(responseBody.toString(), DeleteTreatmentResponseBody::class.java))
        }

        override fun onErrorResponse(errorCode: Int) {
            this@DeleteTreatmentTransaction.onErrorResponse(errorCode)
        }

        override fun onUnauthorizedResponse() {
            BaseApplication.application.finishApplication()
        }
    }
}