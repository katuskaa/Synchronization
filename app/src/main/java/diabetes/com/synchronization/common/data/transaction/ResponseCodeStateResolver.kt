package diabetes.com.synchronization.common.data.transaction

class ResponseCodeStateResolver {

    fun getResponseStatus(statusCode: Int): ResponseStatus =
        when {
            this.isSuccessStatusCode(statusCode) -> ResponseStatus.SUCCESS
            this.isUnauthorizedResponseCode(statusCode) -> ResponseStatus.UNAUTHORIZED
            else -> ResponseStatus.ERROR
        }


    private fun isSuccessStatusCode(statusCode: Int): Boolean {
        return statusCode in 200..206
    }

    private fun isUnauthorizedResponseCode(statusCode: Int): Boolean {
        return statusCode == 401
    }

    enum class ResponseStatus {
        SUCCESS, ERROR, UNAUTHORIZED
    }
}
