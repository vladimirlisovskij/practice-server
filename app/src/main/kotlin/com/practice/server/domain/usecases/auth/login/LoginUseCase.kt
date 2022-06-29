package com.practice.server.domain.usecases.auth.login

import com.practice.network_entities.endpoints.api.auth.Login
import com.practice.network_entities.params.UserType
import com.practice.network_entities.response_status.ResponseStatus
import com.practice.server.domain.Messages
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.dto.ErrorBodyException
import com.practice.server.domain.repository.UserRepository
import com.practice.server.domain.utils.PasswordHashService

class LoginUseCase(
    private val headers: DefaultHeaders,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        body: Login.Body.LoginDetails,
        userType: UserType
    ): Long {
        val user = userRepository.getUser(body.nickName, userType)
            ?: ErrorBodyException.throwSpecificError(
                ResponseStatus.BAD_REQUEST,
                Login.Error.INVALID_AUTH_COMBINATION,
                Messages.invalidLoginPassword.localise(headers.locale)
            )

        val loginPasswordHash = PasswordHashService.createPasswordHash(body.password, user.salt)
        if (!user.password.contentEquals(loginPasswordHash)) {
            ErrorBodyException.throwSpecificError(
                ResponseStatus.BAD_REQUEST,
                Login.Error.INVALID_AUTH_COMBINATION,
                Messages.invalidLoginPassword.localise(headers.locale)
            )
        }

        return user.uid
    }
}


