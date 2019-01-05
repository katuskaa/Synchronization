package diabetes.com.synchronization.common.data.http

import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import diabetes.com.synchronization.common.data.server.BaseRequestProperties
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import org.json.JSONArray

class JsonArrayRequest(method: Int, url: String, requestBody: String, responseHandler: HttpResponseHandler<*, *>) : JsonArrayRequest(method, url, JSONArray(requestBody), responseHandler as Response.Listener<JSONArray>, responseHandler as Response.ErrorListener), BaseRequestProperties {

    init {
        this.retryPolicy = this.setupRequestProperties()
    }

//    override fun getHeaders(): MutableMap<String, String> {
//        return super.getAuthorizationHeader()
//    }

}