package com.practice.server.domain.repository

import com.practice.network_entities.endpoints.api.service.Categories
import com.practice.network_entities.endpoints.api.service.ServiceList
import com.practice.server.domain.dto.ServiceData

interface ServiceRepository {
    suspend fun insertService(data: ServiceData): Long

    fun getService(id: Long): ServiceData?

    fun getServiceList(
        page: Long,
        ownerId: Long? = null,
        categoryId: Long? = null
    ): List<ServiceList.Service>

    fun getCategories(): List<Categories.Category>

    fun getServiceCategories(serviceId: Long): List<Long>
}