package diabetes.com.synchronization.common.data.transaction

import diabetes.com.synchronization.common.helpers.jsonToObject

abstract class ResponseHandler<RESPONSE_BODY> {

    companion object {
        val UNKNOWN_ERROR_CODE = -1
        val SOCKET_NOT_CONNECTED__ERROR_CODE = -2
    }

    protected abstract fun onErrorResponse(errorCode: Int)

    protected abstract fun onUnauthorizedResponse()

    fun parseResponseBody(json: String, tClass: Class<RESPONSE_BODY>): RESPONSE_BODY {
        return jsonToObject(json, tClass)
    }
}