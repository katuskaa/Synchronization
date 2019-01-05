package diabetes.com.synchronization.common.data.http

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import diabetes.com.synchronization.common.data.server.BaseRequestProperties
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import org.json.JSONObject

class JsonObjectRequest(method: Int, url: String, requestBody: String, responseHandler: HttpResponseHandler<*, *>) : JsonObjectRequest(method, url, JSONObject(requestBody), responseHandler as Response.Listener<JSONObject>, responseHandler as Response.ErrorListener), BaseRequestProperties {

    init {
        this.retryPolicy = this.setupRequestProperties()
    }

//    override fun getHeaders(): MutableMap<String, String> {
//        return super.getAuthorizationHeader()
//    }

}