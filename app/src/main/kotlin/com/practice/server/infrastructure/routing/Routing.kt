package com.practice.server.infrastructure.routing

import com.practice.network_entities.endpoints.api.auth.Login
import com.practice.network_entities.endpoints.api.auth.Registration
import com.practice.network_entities.endpoints.api.profile.ProfileGet
import com.practice.network_entities.endpoints.api.service.Categories
import com.practice.network_entities.endpoints.api.service.Create
import com.practice.network_entities.endpoints.api.service.Details
import com.practice.network_entities.endpoints.api.service.ServiceList
import com.practice.network_entities.params.UserType
import com.practice.network_entities.response_status.DefaultError
import com.practice.network_entities.response_status.ErrorType
import com.practice.network_entities.response_status.ResponseStatus
import com.practice.server.di.subcomponent.request.AuthorizedRequestComponent
import com.practice.server.di.subcomponent.request.UnAuthorizedRequestComponent
import com.practice.server.domain.Messages
import com.practice.server.domain.dto.ErrorBodyException
import com.practice.server.infrastructure.utils.EndpointExt.processAuthorized
import com.practice.server.infrastructure.utils.EndpointExt.processUnauthorized
import com.practice.server.infrastructure.utils.EnumExt.getEnumParamOrThrow
import com.practice.server.infrastructure.utils.JwtService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object Routing {
    fun Application.configureRouting(
        unAuthorizedRequestComponentFactory: UnAuthorizedRequestComponent.Factory,
        authorizedRequestComponentFactory: AuthorizedRequestComponent.Factory
    ) {
        routing {
            post(Registration.ENDPOINT) {
                processUnauthorized(unAuthorizedRequestComponentFactory) { component ->
                    val params = call.request.queryParameters
                    val uid = when (params.getEnumParamOrThrow<UserType>(component.getHeaders().locale)) {
                        UserType.INDIVIDUAL -> {
                            val body = call.receive(Registration.Body.Individual::class)
                            component.getIndividualRegistrationUseCase().invoke(body)
                        }

                        UserType.LEGAL -> {
                            val body = call.receive(Registration.Body.Legal::class)
                            component.getLegalRegistrationUseCase().invoke(body)
                        }
                    }

                    call.respond(JwtService.createJwt(uid))
                }
            }

            post(Login.ENDPOINT) {
                processUnauthorized(unAuthorizedRequestComponentFactory) { component ->
                    val params = call.request.queryParameters
                    val body = call.receive(Login.Body.LoginDetails::class)
                    val uid = component
                        .getLoginUseCase()
                        .invoke(
                            body,
                            params.getEnumParamOrThrow(component.getHeaders().locale)
                        )

                    call.respond(JwtService.createJwt(uid))
                }
            }

            get(ProfileGet.ENDPOINT) {
                processAuthorized(authorizedRequestComponentFactory) { component ->
                    when (component.getUserType()) {
                        UserType.INDIVIDUAL -> call.respond(component.getIndividualProfileUseCase().invoke())
                        UserType.LEGAL -> call.respond(component.getLegalProfileUseCase().invoke())
                    }
                }
            }

            get(Categories.ENDPOINT) {
                processAuthorized(authorizedRequestComponentFactory) { component ->
                    val categories = component.getCategoriesUseCase().invoke()
                    call.respond(categories)
                }
            }

            post(Create.ENDPOINT) {
                processAuthorized(authorizedRequestComponentFactory) { component ->
                    val body = call.receive(Create.Body::class)
                    component.getCreateServiceUseCase().invoke(body)
                    call.respond("")
                }
            }

            get(Details.ENDPOINT) {
                processAuthorized(authorizedRequestComponentFactory) { component ->
                    val serviceId = call.parameters[Details.Param.ID]?.toLong()
                        ?: ErrorBodyException.throwDefaultError(
                            ResponseStatus.BAD_REQUEST,
                            DefaultError.INVALID_PARAM,
                            Messages.errorParam.localise(component.getHeaders().locale).format(Details.Param.ID)
                        )

                    val serviceDetails = component.getServiceDetailsUseCase().invoke(serviceId)
                    call.respond(serviceDetails)
                }
            }

            get(ServiceList.ENDPOINT) {
                processAuthorized(authorizedRequestComponentFactory) { component ->
                    val params = call.parameters
                    val page = params[ServiceList.Param.PAGE]?.toLong()
                    val category = params[ServiceList.Param.CATEGORY]?.toLong()
                    val isCurrentUser = params[ServiceList.Param.CURRENT_USER] != null
                    val serviceList = component.getServiceListUseCase().invoke(
                        page = page,
                        categoryId = category,
                        isCurrentUser = isCurrentUser
                    )

                    call.respond(serviceList)
                }
            }
        }
    }
}