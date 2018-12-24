package diabetes.com.synchronization.common.helpers

import com.google.gson.Gson
import com.google.gson.JsonElement

fun objectToJson(any: Any): String {
    val gson = Gson()
    return gson.toJson(any)
}

fun <T> jsonToObject(any: JsonElement, tClass: Class<T>): T {
    val gson = Gson()
    return gson.fromJson(any, tClass)
}

fun <T> jsonToObject(any: String, tClass: Class<T>): T {
    val gson = Gson()
    return gson.fromJson(any, tClass)
}