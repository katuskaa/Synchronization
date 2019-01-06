package diabetes.com.synchronization.common.helpers

import java.text.SimpleDateFormat
import java.util.*

private val TIME_FORMAT__ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

fun getISO_8601Date(): String {
    val dateFormat = SimpleDateFormat(TIME_FORMAT__ISO_8601)
    return dateFormat.format(Date())
}
