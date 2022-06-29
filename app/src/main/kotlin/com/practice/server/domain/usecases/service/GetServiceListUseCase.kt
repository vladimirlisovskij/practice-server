package com.practice.server.domain.usecases.service

import com.practice.network_entities.endpoints.api.service.ServiceList
import com.practice.server.domain.repository.ServiceRepository

class GetServiceListUseCase(
    private val uid: Long,
    private val serviceRepository: ServiceRepository
) {
    suspend operator fun invoke(
        page: Long?,
        categoryId: Long?,
        isCurrentUser: Boolean
    ): List<ServiceList.Service> {
        return serviceRepository.getServiceList(
            page = page ?: 0L,
            categoryId = categoryId,
            ownerId = if (isCurrentUser) uid else null
        )
    }
}