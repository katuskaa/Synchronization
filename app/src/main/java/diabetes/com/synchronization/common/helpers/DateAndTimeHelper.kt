package diabetes.com.synchronization.common.helpers

import java.text.SimpleDateFormat
import java.util.*

val TIME_FORMAT__ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.000'Z'"
val DIABETESM_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

fun convertDiabetesMDateToISO8601(dateTimeFormatted: String, timezone: String): String {
    val diabetesDateFormat = SimpleDateFormat(DIABETESM_TIME_FORMAT, Locale.ENGLISH)
    val ISO8601DateFormat = SimpleDateFormat(TIME_FORMAT__ISO_8601, Locale.ENGLISH)

    diabetesDateFormat.timeZone = TimeZone.getTimeZone(timezone)
    ISO8601DateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = diabetesDateFormat.parse(dateTimeFormatted)

    return ISO8601DateFormat.format(date)
}