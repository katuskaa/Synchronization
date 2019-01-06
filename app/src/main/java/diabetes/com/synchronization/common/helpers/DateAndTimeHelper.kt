package diabetes.com.synchronization.common.helpers

import java.text.SimpleDateFormat
import java.util.*

private val TIME_FORMAT__ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.000'Z'"

fun getISO_8601Date(): String {
    val dateFormat = SimpleDateFormat(TIME_FORMAT__ISO_8601)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC");
    return dateFormat.format(Date())
}
