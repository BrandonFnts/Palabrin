package mx.uttt.palabrin.presentation.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Any.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, object : TypeToken<T>() {}.type)
}
