package com.practice.server.infrastructure.utils

import com.practice.network_entities.headers.Locale
import com.practice.network_entities.headers.Version
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.dto.ErrorBodyException
import com.practice.server.infrastructure.utils.EnumExt.getEnumHeader
import com.practice.server.infrastructure.utils.EnumExt.getEnumHeaderOrThrow
import io.ktor.http.*

object HeadersExt {
    fun extractDefaultHeaders(headers: Headers): DefaultHeaders {
        val locale = headers.getEnumHeader<Locale>() ?: ErrorBodyException.throwLocaleError()
        val version = headers.getEnumHeaderOrThrow<Version>(locale)
        return DefaultHeaders(locale, version)
    }
}