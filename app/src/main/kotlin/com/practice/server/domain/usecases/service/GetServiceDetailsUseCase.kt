package com.practice.server.domain.usecases.service

import com.practice.network_entities.endpoints.api.service.Details
import com.practice.network_entities.params.UserType
import com.practice.network_entities.response_status.DefaultError
import com.practice.network_entities.response_status.ResponseStatus
import com.practice.server.domain.Messages
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.dto.ErrorBodyException
import com.practice.server.domain.repository.ServiceRepository
import com.practice.server.domain.repository.UserRepository

class GetServiceDetailsUseCase(
    private val defaultHeaders: DefaultHeaders,
    private val serviceRepository: ServiceRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Long): Details.ServiceDetails {
        val service = serviceRepository.getService(id)
            ?: ErrorBodyException.throwDefaultError(
                ResponseStatus.BAD_REQUEST,
                DefaultError.INVALID_PARAM,
                Messages.errorParam.localise(defaultHeaders.locale).format(Details.Param.ID)
            )

        val user = userRepository.getUser(service.ownerId)!!
        val ownerName = when(user.userType) {
            UserType.INDIVIDUAL -> userRepository.getIndividual(service.ownerId).fullName
            UserType.LEGAL -> userRepository.getLegal(service.ownerId).organizationName
        }

        return Details.ServiceDetails(
            title = service.title,
            description = service.description,
            startTime = service.startTime,
            endTime = service.endTime,
            owner = ownerName,
            ownerType = user.userType,
            categories = service.categories
        )
    }
}