package com.practice.server.infrastructure.repository

import com.practice.network_entities.params.UserType
import com.practice.server.Database
import com.practice.server.di.scopes.AppScope
import com.practice.server.domain.dto.IndividualData
import com.practice.server.domain.dto.LegalData
import com.practice.server.domain.dto.UserData
import com.practice.server.domain.repository.UserRepository
import com.practice.server.infrastructure.utils.Constants
import com.practice.server.infrastructure.utils.Mapper
import com.practice.server.infrastructure.utils.Mapper.mapToDataLayer
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@AppScope
class UserRepositoryImpl @Inject constructor(
    private val database: Database
) : UserRepository {
    private val insertUserMutex = Mutex()

    override suspend fun insertUser(userData: UserData): Long {
        return insertUserMutex.withLock {
            database.transactionWithResult {
                val curId = (
                        database.customSequenceQueries
                            .getTableKeyId(Constants.USER_TABLE)
                            .executeAsOneOrNull()
                            ?: 0L
                        ) + 1L

                database.userQueries.insertUser(
                    nickName = userData.nickName,
                    type = userData.userType.name,
                    password = userData.password,
                    salt = userData.salt,
                    uid = curId
                )

                database.customSequenceQueries.insertOrReplace(Constants.USER_TABLE, curId)
                curId
            }
        }
    }

    override fun getUser(nickName: String, userTypes: UserType): UserData? {
        return database.userQueries.getUserByNickname(nickName, userTypes.name, Mapper::mapUser).executeAsOneOrNull()
    }

    override fun getUser(uid: Long): UserData? {
        return database.userQueries.getUserByUid(uid, Mapper::mapUser).executeAsOneOrNull()
    }

    override fun insertIndividual(individualData: IndividualData) {
        database.individualQueries.insertIndividual(individualData.mapToDataLayer())
    }

    override fun getIndividual(uid: Long): IndividualData {
        return database.individualQueries.getIndividualByUid(uid, Mapper::mapIndividual).executeAsOne()
    }

    override fun insertLegal(legalData: LegalData) {
        database.legalQueries.insertLegal(legalData.mapToDataLayer())
    }

    override fun getLegal(uid: Long): LegalData {
        return database.legalQueries.getLegalByUid(uid, Mapper::mapLegal).executeAsOne()
    }
}

