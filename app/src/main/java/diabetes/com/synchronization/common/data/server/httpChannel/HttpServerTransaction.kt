package diabetes.com.synchronization.common.data.server.httpChannel

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Request
import diabetes.com.synchronization.common.data.server.ServerTransaction
import diabetes.com.synchronization.common.data.transaction.RequestData
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData

abstract class HttpServerTransaction<in RESPONSE_DATA, MUTABLE_DATA_TYPE : ResponseLiveData<*>>(protected val serverUrl: String, protected val requestData: RequestData, responseLiveData: MutableLiveData<MUTABLE_DATA_TYPE>) : ServerTransaction<RESPONSE_DATA, MUTABLE_DATA_TYPE>(responseLiveData) {

    val request: Request<*> by lazy {
        this.getHttpRequest()
    }

    abstract fun getHttpRequest(): Request<*>
}