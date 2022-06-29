package com.practice.server.domain.usecases.profile

import com.practice.network_entities.endpoints.api.profile.ProfileGet
import com.practice.server.domain.repository.UserRepository

class GetLegalProfileUseCase(
    private val uid: Long,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ProfileGet.Response.LegalResponse {
        val user = userRepository.getLegal(uid)
        return ProfileGet.Response.LegalResponse(user.organizationName)
    }
}