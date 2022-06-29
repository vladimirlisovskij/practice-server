package com.practice.server.infrastructure.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.practice.network_entities.headers.Locale
import com.practice.network_entities.response_status.DefaultError
import com.practice.network_entities.response_status.ResponseStatus
import com.practice.server.domain.Messages
import com.practice.server.domain.dto.ErrorBodyException
import io.ktor.http.*
import java.util.*

object JwtService {
    private const val UID = "UID"
    private const val HOUR_MILLIS = 3600000
    private const val TOKEN_LIFE_TIME = 3 * HOUR_MILLIS
    private const val AUTHORIZATION = "Authorization"

    private val algorithm get() = Algorithm.HMAC256(Constants.JWT_SECRET)

    fun createJwt(uid: Long) = JWT.create()
        .withClaim(UID, uid)
        .withExpiresAt(Date(System.currentTimeMillis() + TOKEN_LIFE_TIME))
        .sign(algorithm)

    fun getUidFromHeaders(headers: Headers, locale: Locale): Long {
        val decodedToken = try {
            JWT.decode(headers[AUTHORIZATION]!!.split(" ")[1]).apply(algorithm::verify)
        } catch (e: Throwable) {
            ErrorBodyException.throwDefaultError(
                ResponseStatus.BAD_REQUEST,
                DefaultError.INVALID_PARAM,
                Messages.errorParam.localise(locale).format(AUTHORIZATION)
            )
        }

        if (decodedToken.expiresAt < Date(System.currentTimeMillis())) ErrorBodyException.throwUnauthorizedError()
        return decodedToken.claims[UID]!!.asLong()!!
    }
}