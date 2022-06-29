package com.practice.server.domain.usecases.profile

import com.practice.network_entities.endpoints.api.profile.ProfileGet
import com.practice.server.domain.repository.UserRepository

class GetIndividualProfileUseCase(
    private val uid: Long,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ProfileGet.Response.IndividualResponse {
        val user = userRepository.getIndividual(uid)
        return ProfileGet.Response.IndividualResponse(user.fullName)
    }
}

