package com.practice.server.domain.utils

object EnumExt {
    inline fun <reified T : Enum<T>> valueOrNull(type: String) = runCatching { java.lang.Enum.valueOf(T::class.java, type) }.getOrNull()
    inline fun <reified T : Enum<T>> requireName() = T::class.simpleName!!
}
