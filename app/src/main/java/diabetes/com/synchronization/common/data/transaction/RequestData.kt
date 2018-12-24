package diabetes.com.synchronization.common.data.transaction

import diabetes.com.synchronization.common.helpers.objectToJson

abstract class RequestData {

    abstract fun getRequestBody(): RequestBody

    abstract class RequestBody {
        fun toJson(): String = objectToJson(this)
    }
}