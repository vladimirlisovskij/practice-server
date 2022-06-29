package com.practice.server.infrastructure.repository

import com.practice.network_entities.endpoints.api.service.Categories
import com.practice.network_entities.endpoints.api.service.ServiceList
import com.practice.server.Database
import com.practice.server.di.scopes.AppScope
import com.practice.server.domain.dto.ServiceData
import com.practice.server.domain.repository.ServiceRepository
import com.practice.server.infrastructure.utils.Constants
import com.practice.server.infrastructure.utils.Mapper
import com.practice.server.infrastructure.utils.Mapper.mapToDomainLayer
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@AppScope
class ServiceRepositoryImpl @Inject constructor(
    private val database: Database
) : ServiceRepository {
    private val insertServiceMutex = Mutex()

    override suspend fun insertService(data: ServiceData): Long {
        return insertServiceMutex.withLock {
            database.transactionWithResult {
                val curId = (
                        database.customSequenceQueries
                            .getTableKeyId(Constants.SERVICE_TABLE)
                            .executeAsOneOrNull() ?: 0L
                        ) + 1L

                database.serviceQueries.insertService(
                    id = curId,
                    ownerId = data.ownerId,
                    title = data.title,
                    description = data.description,
                    startTime = data.startTime,
                    endTime = data.endTime
                )

                data.categories.forEach { categoryId ->
                    database.categoryServiceQueries.addCategory(
                        categoryId = categoryId,
                        serviceId = curId
                    )
                }

                database.customSequenceQueries.insertOrReplace(Constants.SERVICE_TABLE, curId)
                curId
            }
        }
    }

    override fun getService(id: Long): ServiceData? {
        val service = database.serviceQueries.getService(id)
            .executeAsOneOrNull() ?: return null

        val categories = database.categoryServiceQueries.getServiceCategories(id).executeAsList()
        return service.mapToDomainLayer(categories)
    }

    override fun getServiceList(page: Long, ownerId: Long?, categoryId: Long?): List<ServiceList.Service> {
        return categoryId?.let {
            database.serviceQueries.getServiceListForCategory(
                categoryId = it,
                ownerId = ownerId,
                page = page,
                limit = 20L,
                mapper = Mapper::mapServiceDetails
            ).executeAsList()
        } ?: database.serviceQueries.getServiceList(
            ownerId = ownerId,
            page = page,
            limit = 20L,
            mapper = Mapper::mapServiceDetails
        ).executeAsList()
    }

    override fun getCategories(): List<Categories.Category> {
        return database.categoryQueries.getCategories(Mapper::mapCategory).executeAsList()
    }

    override fun getServiceCategories(serviceId: Long): List<Long> {
        return database.categoryServiceQueries.getServiceCategories(serviceId).executeAsList()
    }
}