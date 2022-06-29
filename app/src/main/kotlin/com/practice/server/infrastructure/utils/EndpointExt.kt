package com.practice.server.infrastructure.utils

import com.practice.server.di.subcomponent.request.AuthorizedRequestComponent
import com.practice.server.di.subcomponent.request.UnAuthorizedRequestComponent
import com.practice.server.domain.dto.ErrorBodyException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

object EndpointExt {
    suspend fun PipelineContext<Unit, ApplicationCall>.processCatching(
        block: suspend PipelineContext<Unit, ApplicationCall>.() -> Unit
    ) {
        try {
            block.invoke(this)
        } catch (errorThrowable: ErrorBodyException) {
            call.respond(HttpStatusCode.fromValue(errorThrowable.statusCode), errorThrowable.body ?: "")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e.toString())
        }
    }

    suspend fun PipelineContext<Unit, ApplicationCall>.processUnauthorized(
        unAuthorizedRequestComponentFactory: UnAuthorizedRequestComponent.Factory,
        block: suspend PipelineContext<Unit, ApplicationCall>.(UnAuthorizedRequestComponent) -> Unit
    ) = processCatching {
        block.invoke(
            this,
            unAuthorizedRequestComponentFactory.create(HeadersExt.extractDefaultHeaders(call.request.headers))
        )
    }

    suspend fun PipelineContext<Unit, ApplicationCall>.processAuthorized(
        authorizedRequestComponentFactory: AuthorizedRequestComponent.Factory,
        body: suspend PipelineContext<Unit, ApplicationCall>.(AuthorizedRequestComponent) -> Unit
    ) = processCatching {
        val headers = call.request.headers
        val extractedHeaders = HeadersExt.extractDefaultHeaders(headers)
        val uid = JwtService.getUidFromHeaders(headers, extractedHeaders.locale)
        body.invoke(
            this,
            authorizedRequestComponentFactory.create(extractedHeaders, uid)
        )
    }
}