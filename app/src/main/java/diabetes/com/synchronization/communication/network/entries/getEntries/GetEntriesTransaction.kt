package diabetes.com.synchronization.communication.network.entries.getEntries

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Request
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.http.JsonArrayWithJsonObjectRequest
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import diabetes.com.synchronization.common.data.server.httpChannel.HttpServerTransaction
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import org.json.JSONArray

class GetEntriesTransaction(serverUrl: String, requestData: GetEntriesRequestData, responseLiveData: MutableLiveData<ResponseLiveData<Array<GetEntriesResponseBody>>>) : HttpServerTransaction<Array<GetEntriesResponseBody>, ResponseLiveData<Array<GetEntriesResponseBody>>>(serverUrl, requestData, responseLiveData) {

    override fun getHttpRequest(): Request<*> = JsonArrayWithJsonObjectRequest(Request.Method.GET, this.serverUrl.plus("entries.json?token=").plus(BuildConfig.SERVER_URL_TOKEN), this.requestData.getRequestBody().toJson(), GetEntriesResponseHandler())

    override fun onLoadingStarted() {
        val responseLiveData = ResponseLiveData<Array<GetEntriesResponseBody>>()
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onSuccessResponse(responseBody: Array<GetEntriesResponseBody>) {
        val responseLiveData = ResponseLiveData(responseBody)
        this.responseLiveData.postValue(responseLiveData)
    }

    override fun onErrorResponse(errorCode: Int) {
        val responseLiveData = ResponseLiveData<Array<GetEntriesResponseBody>>(errorCode = errorCode)
        this.responseLiveData.postValue(responseLiveData)
    }

    inner class GetEntriesResponseHandler : HttpResponseHandler<JSONArray, Array<GetEntriesResponseBody>>() {

        override fun onSuccessResponse(responseBody: JSONArray) {
            this@GetEntriesTransaction.onSuccessResponse(this.parseResponseBody(responseBody.toString(), Array<GetEntriesResponseBody>::class.java))
        }

        override fun onErrorResponse(errorCode: Int) {
            this@GetEntriesTransaction.onErrorResponse(errorCode)
        }

        override fun onUnauthorizedResponse() {
            BaseApplication.application.finishApplication()
        }
    }
}