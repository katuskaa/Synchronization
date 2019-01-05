package diabetes.com.synchronization.common.helpers

import android.util.Base64
import diabetes.com.synchronization.BuildConfig

fun createAuthorizationHeadersForHttpConnection(): MutableMap<String, String> {
    val parameters = mutableMapOf<String, String>()

    val auth = "Bearer " + Base64.encodeToString(BuildConfig.SERVER_TOKEN.toByteArray(), Base64.NO_WRAP)
    parameters["Authorization"] = auth

    return parameters
}
