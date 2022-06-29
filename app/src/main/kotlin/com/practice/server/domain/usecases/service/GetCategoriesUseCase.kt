package com.practice.server.domain.usecases.service

import com.practice.network_entities.endpoints.api.service.Categories
import com.practice.server.domain.repository.ServiceRepository

class GetCategoriesUseCase(
    private val serviceRepository: ServiceRepository
) {
    suspend operator fun invoke(): List<Categories.Category> {
        return serviceRepository.getCategories()
    }
}