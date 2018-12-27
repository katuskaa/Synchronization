package diabetes.com.synchronization.common.helpers

import android.util.Base64

fun createAuthorizationHeadersForHttpConnection(): MutableMap<String, String> {
    val parameters = mutableMapOf<String, String>()

    //TODO header_user and header_password should be in gradle in build config defined
    val credentials = String.format("%s:%s", "header_user", "header_password")

    val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
    parameters["Authorization"] = auth

    return parameters
}
