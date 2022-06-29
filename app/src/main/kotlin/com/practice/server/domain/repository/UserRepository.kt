package com.practice.server.domain.repository

import com.practice.network_entities.params.UserType
import com.practice.server.domain.dto.IndividualData
import com.practice.server.domain.dto.LegalData
import com.practice.server.domain.dto.UserData

interface UserRepository {
    suspend fun insertUser(userData: UserData): Long
    fun getUser(nickName: String, userTypes: UserType): UserData?
    fun getUser(uid: Long): UserData?

    fun insertIndividual(individualData: IndividualData)
    fun getIndividual(uid: Long): IndividualData

    fun insertLegal(legalData: LegalData)
    fun getLegal(uid: Long): LegalData
}

