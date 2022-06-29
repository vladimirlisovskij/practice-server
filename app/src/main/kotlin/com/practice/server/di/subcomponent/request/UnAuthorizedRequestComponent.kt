package com.practice.server.di.subcomponent.request

import com.practice.server.di.scopes.RequestScope
import com.practice.server.di.subcomponent.request.modules.UnAuthorizedUseCaseModule
import com.practice.server.domain.dto.DefaultHeaders
import com.practice.server.domain.usecases.auth.GetUserTypeUseCase
import com.practice.server.domain.usecases.auth.login.LoginUseCase
import com.practice.server.domain.usecases.auth.registration.IndividualRegistrationUseCase
import com.practice.server.domain.usecases.auth.registration.LegalRegistrationUseCase
import dagger.BindsInstance
import dagger.Subcomponent

@RequestScope
@Subcomponent(modules = [UnAuthorizedUseCaseModule::class])
interface UnAuthorizedRequestComponent {
    fun getHeaders(): DefaultHeaders

    fun getLoginUseCase(): LoginUseCase

    fun getIndividualRegistrationUseCase(): IndividualRegistrationUseCase
    fun getLegalRegistrationUseCase(): LegalRegistrationUseCase

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance headers: DefaultHeaders
        ): UnAuthorizedRequestComponent
    }
}

