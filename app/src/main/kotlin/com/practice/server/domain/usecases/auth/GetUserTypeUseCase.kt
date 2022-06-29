package com.practice.server.domain.usecases.auth

import com.practice.server.domain.dto.ErrorBodyException
import com.practice.server.domain.repository.UserRepository

class GetUserTypeUseCase(
    private val uid: Long,
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUser(uid)?.userType
        ?: ErrorBodyException.throwUnauthorizedError()
}