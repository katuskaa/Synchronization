package diabetes.com.synchronization.common.helpers

import diabetes.com.synchronization.BuildConfig

fun getEnteredBy(): String {
    return BuildConfig.SERVER_URL_TOKEN.split("-")[0]
}