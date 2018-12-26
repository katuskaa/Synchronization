package diabetes.com.synchronization.communication.network.treatments.getTreatments

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Request
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.http.JsonArrayWithJsonObjectRequest
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import diabetes.com.synchronization.common.data.server.httpChannel.HttpServerTransaction
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import org.json.JSONArray

class GetTreatmentsTransaction(serverUrl: String, requestData: GetTreatmentsRequestData, responseLiveData: MutableLiveData<ResponseLiveData<Array<GetTreatmentsResponseBody>>>) : HttpServerTransaction<Array<GetTreatmentsResponseBody>, ResponseLiveData<Array<GetTreatmentsResponseBody>>>(serverUrl, requestData, responseLiveData) {

    override fun getHttpRequest(): Request<*> = JsonArrayWithJsonObjectRequest(Request.Method.GET, this.serverUrl.plus("treatments?count=").plus((requestData as GetTreatmentsRequestData).count), this.requestData.getRequestBody().toJson(), GetTreatmentsResponseHandler(), BaseApplication.application)

    override fun onLoadingStarted() {
        val responseLiveData = ResponseLiveData<Array<GetTreatmentsResponseBody>>()
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onSuccessResponse(responseBody: Array<GetTreatmentsResponseBody>) {
        val responseLiveData = ResponseLiveData(responseBody)
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onErrorResponse(errorCode: Int) {
        val responseLiveData = ResponseLiveData<Array<GetTreatmentsResponseBody>>(errorCode = errorCode)
        this.responseLiveData.postValue(responseLiveData)
    }

    inner class GetTreatmentsResponseHandler : HttpResponseHandler<JSONArray, Array<GetTreatmentsResponseBody>>() {

        override fun onSuccessResponse(responseBody: JSONArray) {
            this@GetTreatmentsTransaction.onSuccessResponse(this.parseResponseBody(responseBody.toString(), Array<GetTreatmentsResponseBody>::class.java))
        }

        override fun onErrorResponse(errorCode: Int) {
            this@GetTreatmentsTransaction.onErrorResponse(errorCode)
        }

        override fun onUnauthorizedResponse() {
            BaseApplication.application.finishApplication()
        }
    }
}