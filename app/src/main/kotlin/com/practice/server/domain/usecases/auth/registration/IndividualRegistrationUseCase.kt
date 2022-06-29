package com.practice.server.domain.usecases.auth.registration

import com.practice.network_entities.endpoints.api.auth.Registration
import com.practice.network_entities.params.UserType
import com.practice.network_entities.response_status.ResponseStatus
import com.practice.server.domain.Messages
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.dto.ErrorBodyException
import com.practice.server.domain.dto.IndividualData
import com.practice.server.domain.dto.UserData
import com.practice.server.domain.repository.UserRepository
import com.practice.server.domain.utils.PasswordHashService

class IndividualRegistrationUseCase(
    private val headers: DefaultHeaders,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        body: Registration.Body.Individual
    ): Long {
        if (userRepository.getUser(body.nickName, UserType.INDIVIDUAL) != null) {
            ErrorBodyException.throwSpecificError(
                ResponseStatus.BAD_REQUEST,
                Registration.Error.NICKNAME_ALREADY_USE,
                Messages.nicknameAlreadyUse.localise(headers.locale)
            )
        }

        val salt = PasswordHashService.createSalt()
        val hash = PasswordHashService.createPasswordHash(body.password, salt)
        val uid = userRepository.insertUser(
            UserData(
                nickName = body.nickName,
                userType = UserType.INDIVIDUAL,
                uid = -1,
                password = hash,
                salt = salt
            )
        )

        userRepository.insertIndividual(
            IndividualData(
                uid = uid,
                fullName = body.fullName
            )
        )

        return uid
    }
}

