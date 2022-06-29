package com.practice.server.di.subcomponent.request.modules

import com.practice.server.di.scopes.RequestScope
import com.practice.server.di.subcomponent.request.qualifiers.Qualifiers
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.repository.ServiceRepository
import com.practice.server.domain.repository.UserRepository
import com.practice.server.domain.usecases.auth.GetUserTypeUseCase
import com.practice.server.domain.usecases.profile.GetIndividualProfileUseCase
import com.practice.server.domain.usecases.profile.GetLegalProfileUseCase
import com.practice.server.domain.usecases.service.CreateServiceUseCase
import com.practice.server.domain.usecases.service.GetCategoriesUseCase
import com.practice.server.domain.usecases.service.GetServiceDetailsUseCase
import com.practice.server.domain.usecases.service.GetServiceListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [UnAuthorizedUseCaseModule::class])
object AuthorizedUseCaseModule {
    @RequestScope
    @Provides
    fun provideUserType(
        @Named(Qualifiers.UID) uid: Long,
        userRepository: UserRepository
    ) = GetUserTypeUseCase(
        uid,
        userRepository
    ).invoke()

    @RequestScope
    @Provides
    fun provideGetIndividualProfileUseCase(
        @Named(Qualifiers.UID) uid: Long,
        userRepository: UserRepository
    ) = GetIndividualProfileUseCase(
        uid,
        userRepository
    )

    @RequestScope
    @Provides
    fun provideGetLegalProfileUseCase(
        @Named(Qualifiers.UID) uid: Long,
        userRepository: UserRepository
    ) = GetLegalProfileUseCase(
        uid,
        userRepository
    )

    @RequestScope
    @Provides
    fun provideCreateServiceUseCase(
        @Named(Qualifiers.UID) uid: Long,
        serviceRepository: ServiceRepository
    ) = CreateServiceUseCase(
        uid,
        serviceRepository
    )

    @RequestScope
    @Provides
    fun provideGetCategoriesUseCase(
        serviceRepository: ServiceRepository
    ) = GetCategoriesUseCase(serviceRepository)

    @RequestScope
    @Provides
    fun provideGetServiceDetailsUseCase(
        defaultHeaders: DefaultHeaders,
        serviceRepository: ServiceRepository,
        userRepository: UserRepository
    ) = GetServiceDetailsUseCase(
        defaultHeaders,
        serviceRepository,
        userRepository
    )

    @RequestScope
    @Provides
    fun provideGetServiceListUseCase(
        @Named(Qualifiers.UID) uid: Long,
        serviceRepository: ServiceRepository
    ) = GetServiceListUseCase(
        uid,
        serviceRepository
    )
}

