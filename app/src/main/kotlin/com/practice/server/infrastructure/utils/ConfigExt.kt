package com.practice.server.infrastructure.utils

import io.ktor.server.config.*

object ConfigExt {
    fun HoconApplicationConfig.getString(key: String) = property(key).getString()
}