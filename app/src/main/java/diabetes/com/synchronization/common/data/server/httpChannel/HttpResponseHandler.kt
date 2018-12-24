package diabetes.com.synchronization.common.data.server.httpChannel

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import diabetes.com.synchronization.common.data.transaction.ResponseCodeStateResolver
import diabetes.com.synchronization.common.data.transaction.ResponseHandler

abstract class HttpResponseHandler<NETWORK_RESPONSE_TYPE, RESPONSE_BODY> : ResponseHandler<RESPONSE_BODY>(), Response.Listener<NETWORK_RESPONSE_TYPE>, Response.ErrorListener {

    override fun onResponse(responseBody: NETWORK_RESPONSE_TYPE) {
        this.onSuccessResponse(responseBody)
    }

    override fun onErrorResponse(error: VolleyError?) {
        var errorCode: Int = UNKNOWN_ERROR_CODE
        error?.let {

            val networkResponse: NetworkResponse? = error.networkResponse
            networkResponse?.let { errorCode = networkResponse.statusCode }
        }

        val responseStatus = ResponseCodeStateResolver().getResponseStatus(errorCode)
        if (ResponseCodeStateResolver.ResponseStatus.UNAUTHORIZED == responseStatus) {
            this.onUnauthorizedResponse()
        } else {
            this.onErrorResponse(errorCode)
        }
    }

    protected abstract fun onSuccessResponse(responseBody: NETWORK_RESPONSE_TYPE)
}