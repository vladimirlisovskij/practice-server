package com.practice.server.domain.usecases.service

import com.practice.network_entities.endpoints.api.service.Create
import com.practice.server.domain.dto.ServiceData
import com.practice.server.domain.repository.ServiceRepository

class CreateServiceUseCase(
    private val userId: Long,
    private val serviceRepository: ServiceRepository,
) {
    suspend operator fun invoke(data: Create.Body) {
        val serviceData = ServiceData(
            id = -1L,
            ownerId = userId,
            title = data.title,
            description = data.description,
            startTime = data.startTime,
            endTime = data.endTime,
            categories = data.categories
        )

        serviceRepository.insertService(serviceData)
    }
}

