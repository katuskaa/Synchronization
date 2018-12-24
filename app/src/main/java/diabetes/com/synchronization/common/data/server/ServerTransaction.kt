package diabetes.com.synchronization.common.data.server

import android.arch.lifecycle.MutableLiveData
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData

abstract class ServerTransaction<in RESPONSE_BODY, MUTABLE_DATA_TYPE : ResponseLiveData<*>>(protected val responseLiveData: MutableLiveData<MUTABLE_DATA_TYPE>) {

    abstract fun onLoadingStarted()
    abstract fun onSuccessResponse(responseBody: RESPONSE_BODY)
    abstract fun onErrorResponse(errorCode: Int)
}
