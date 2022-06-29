package com.practice.server.di.subcomponent.request.modules

import com.practice.server.di.scopes.RequestScope
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.repository.UserRepository
import com.practice.server.domain.usecases.auth.login.LoginUseCase
import com.practice.server.domain.usecases.auth.registration.IndividualRegistrationUseCase
import com.practice.server.domain.usecases.auth.registration.LegalRegistrationUseCase
import dagger.Module
import dagger.Provides

@Module
object UnAuthorizedUseCaseModule {
    @RequestScope
    @Provides
    fun provideLoginUseCase(
        defaultHeaders: DefaultHeaders,
        userRepository: UserRepository
    ) = LoginUseCase(
        defaultHeaders,
        userRepository
    )

    @RequestScope
    @Provides
    fun provideIndividualRegistrationUseCase(
        defaultHeaders: DefaultHeaders,
        userRepository: UserRepository
    ) = IndividualRegistrationUseCase(
        defaultHeaders,
        userRepository
    )

    @RequestScope
    @Provides
    fun provideLegalRegistrationUseCase(
        defaultHeaders: DefaultHeaders,
        userRepository: UserRepository
    ) = LegalRegistrationUseCase(
        defaultHeaders,
        userRepository
    )
}

