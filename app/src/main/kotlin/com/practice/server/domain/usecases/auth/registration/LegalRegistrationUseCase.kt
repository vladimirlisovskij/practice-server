package com.practice.server.domain.usecases.auth.registration

import com.practice.network_entities.endpoints.api.auth.Registration
import com.practice.network_entities.params.UserType
import com.practice.network_entities.response_status.ResponseStatus
import com.practice.server.domain.Messages
import com.practice.server.domain.dto.*
import com.practice.server.domain.repository.UserRepository
import com.practice.server.domain.utils.PasswordHashService

class LegalRegistrationUseCase(
    private val headers: DefaultHeaders,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        body: Registration.Body.Legal
    ): Long {
        if (userRepository.getUser(body.nickName, UserType.LEGAL) != null) {
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
                userType = UserType.LEGAL,
                uid = -1,
                password = hash,
                salt = salt
            )
        )

        userRepository.insertLegal(
            LegalData(
                uid = uid,
                organizationName = body.organizationName
            )
        )

        return uid
    }
}