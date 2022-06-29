package com.practice.server.di.subcomponent.request

import com.practice.network_entities.params.UserType
import com.practice.server.di.scopes.RequestScope
import com.practice.server.di.subcomponent.request.modules.AuthorizedUseCaseModule
import com.practice.server.di.subcomponent.request.qualifiers.Qualifiers
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.usecases.profile.GetIndividualProfileUseCase
import com.practice.server.domain.usecases.profile.GetLegalProfileUseCase
import com.practice.server.domain.usecases.service.CreateServiceUseCase
import com.practice.server.domain.usecases.service.GetCategoriesUseCase
import com.practice.server.domain.usecases.service.GetServiceDetailsUseCase
import com.practice.server.domain.usecases.service.GetServiceListUseCase
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@RequestScope
@Subcomponent(modules = [AuthorizedUseCaseModule::class])
interface AuthorizedRequestComponent : UnAuthorizedRequestComponent {
    @Named(Qualifiers.UID)
    fun getUid(): Long

    fun getUserType(): UserType

    fun getIndividualProfileUseCase(): GetIndividualProfileUseCase
    fun getLegalProfileUseCase(): GetLegalProfileUseCase

    fun getCategoriesUseCase(): GetCategoriesUseCase
    fun getCreateServiceUseCase(): CreateServiceUseCase
    fun getServiceDetailsUseCase(): GetServiceDetailsUseCase
    fun getServiceListUseCase(): GetServiceListUseCase

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance headers: DefaultHeaders,
            @BindsInstance @Named(Qualifiers.UID) uid: Long,
        ): AuthorizedRequestComponent
    }
}