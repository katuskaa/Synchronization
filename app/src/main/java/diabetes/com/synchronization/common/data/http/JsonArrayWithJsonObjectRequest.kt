package diabetes.com.synchronization.common.data.http

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import diabetes.com.synchronization.common.data.server.BaseRequestProperties
import diabetes.com.synchronization.common.data.server.httpChannel.HttpResponseHandler
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException

class JsonArrayWithJsonObjectRequest(method: Int, url: String?, requestBody: String?, responseHandler: HttpResponseHandler<*, *>, val context: Context) : JsonRequest<JSONArray>(method, url, requestBody, responseHandler as Response.Listener<JSONArray>, responseHandler as Response.ErrorListener), BaseRequestProperties {

    init {
        this.retryPolicy = this.setupRequestProperties()
    }

//    override fun getHeaders(): MutableMap<String, String> {
//        return super.getAuthorizationHeader(context)
//    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONArray> {
        return try {
            val jsonString = String(response!!.data)
            Response.success(JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response))
        } catch (encodingException: UnsupportedEncodingException) {
            Response.error<JSONArray>(ParseError(encodingException))
        } catch (jsonException: JSONException) {
            Response.error<JSONArray>(ParseError(jsonException))
        }
    }

}