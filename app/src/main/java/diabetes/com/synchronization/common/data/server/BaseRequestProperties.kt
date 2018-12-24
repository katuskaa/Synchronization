package diabetes.com.synchronization.common.data.server

import com.android.volley.DefaultRetryPolicy

private const val INITIAL_TIME_OUT = 35000

interface BaseRequestProperties {

    fun setupRequestProperties(): DefaultRetryPolicy = DefaultRetryPolicy(INITIAL_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

    // fun getAuthorizationHeader(context: Context): MutableMap<String, String> = createAuthorizationHeadersForHttpConnection(context)
}