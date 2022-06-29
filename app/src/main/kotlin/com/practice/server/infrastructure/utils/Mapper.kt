package com.practice.server.infrastructure.utils

import com.practice.network_entities.endpoints.api.service.Categories
import com.practice.network_entities.endpoints.api.service.ServiceList
import com.practice.network_entities.params.UserType
import com.practice.server.domain.dto.IndividualData
import com.practice.server.domain.dto.LegalData
import com.practice.server.domain.dto.ServiceData
import com.practice.server.domain.dto.UserData
import service.Service
import user.Individual
import user.Legal

object Mapper {
    fun mapUser(nickName: String, userType: String, uid: Long, password: ByteArray, salt: ByteArray) = UserData(
        nickName = nickName,
        userType = UserType.valueOf(userType),
        uid = uid,
        password = password,
        salt = salt,
    )

    fun mapIndividual(uid: Long, fullName: String) = IndividualData(uid, fullName)

    fun IndividualData.mapToDataLayer(uid: Long = this.uid) = Individual(
        uid = uid,
        fullName = fullName
    )

    fun mapLegal(uid: Long, organizationName: String) = LegalData(
        uid = uid,
        organizationName = organizationName
    )

    fun LegalData.mapToDataLayer(uid: Long = this.uid) = Legal(
        uid = uid,
        organizationName = organizationName,
    )

    fun Service.mapToDomainLayer(categories: List<Long>) = ServiceData(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        startTime = startTime,
        endTime = endTime,
        categories = categories
    )

    fun mapCategory(id: Long, name: String) = Categories.Category(
        name = name,
        id = id
    )

    fun mapServiceDetails(id: Long, title: String, description: String) = ServiceList.Service(
        id = id,
        title = title,
        description = description
    )
}