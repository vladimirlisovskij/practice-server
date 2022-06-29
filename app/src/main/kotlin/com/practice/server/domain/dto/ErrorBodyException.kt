package com.practice.server.domain.dto

import com.practice.network_entities.response_status.DefaultError
import com.practice.network_entities.response_status.ErrorBody
import com.practice.network_entities.response_status.ErrorType
import com.practice.network_entities.response_status.ResponseStatus

class ErrorBodyException private constructor(
    val statusCode: Int,
    val body: ErrorBody?
): Exception() {
    companion object {
        private fun throwErrorBody(
            statusCode: Int,
            errorType: ErrorType,
            errorCode: Int,
            message: String?
        ): Nothing {
            val body = ErrorBody(
                errorType = errorType,
                errorCode = errorCode,
                message = message
            )

            throw ErrorBodyException(statusCode, body)
        }

        fun throwInternalError(): Nothing = throw ErrorBodyException(ResponseStatus.INTERNAL_SERVER_ERROR, null)

        fun throwLocaleError(): Nothing =
            throwErrorBody(ResponseStatus.BAD_REQUEST, ErrorType.DEFAULT, DefaultError.INVALID_LOCALE_HEADER, null)

        fun throwUnauthorizedError(): Nothing = throw ErrorBodyException(ResponseStatus.UNAUTHORIZED, null)

        fun throwForbiddenError(): Nothing = throw ErrorBodyException(ResponseStatus.FORBIDDEN, null)

        fun throwDefaultError(status: Int, errorCode: Int, message: String): Nothing =
            throwErrorBody(status, ErrorType.DEFAULT, errorCode, message)

        fun throwSpecificError(status: Int, errorCode: Int, message: String): Nothing =
            throwErrorBody(status, ErrorType.SPECIFIC, errorCode, message)
    }
}


