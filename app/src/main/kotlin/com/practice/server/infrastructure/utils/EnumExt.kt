package com.practice.server.infrastructure.utils

import com.practice.network_entities.headers.Locale
import com.practice.network_entities.response_status.DefaultError
import com.practice.network_entities.response_status.ResponseStatus
import com.practice.server.domain.dto.ErrorBodyException
import com.practice.server.domain.utils.EnumExt.requireName
import com.practice.server.domain.utils.EnumExt.valueOrNull
import com.practice.server.domain.Messages
import io.ktor.http.*

object EnumExt {
    inline fun <reified T : Enum<T>> Headers.getEnumHeader() = get(requireName<T>())?.let { valueOrNull<T>(it) }
    inline fun <reified T : Enum<T>> Parameters.getEnumParam() = get(requireName<T>())?.let { valueOrNull<T>(it) }
    inline fun <reified T : Enum<T>> Parameters.getEnumParamOrThrow(locale: Locale) = getEnumParam<T>()
            ?: ErrorBodyException.throwDefaultError(
                ResponseStatus.BAD_REQUEST,
                DefaultError.INVALID_PARAM,
                Messages.errorParam.localise(locale).format(requireName<T>())
            )

    inline fun <reified T : Enum<T>> Headers.getEnumHeaderOrThrow(locale: Locale) = getEnumHeader<T>()
        ?: ErrorBodyException.throwDefaultError(
            ResponseStatus.BAD_REQUEST,
            DefaultError.INVALID_PARAM,
            Messages.errorParam.localise(locale).format(requireName<T>())
        )
}
