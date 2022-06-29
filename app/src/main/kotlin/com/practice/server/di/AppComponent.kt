package com.practice.server.di

import com.practice.server.di.modules.DatabaseModule
import com.practice.server.di.modules.NetworkModule
import com.practice.server.di.modules.RepositoryModule
import com.practice.server.di.scopes.AppScope
import com.practice.server.di.subcomponent.request.AuthorizedRequestComponent
import com.practice.server.di.subcomponent.request.UnAuthorizedRequestComponent
import com.practice.server.di.subcomponent.request.modules.AppConfigModule
import dagger.Component
import io.ktor.server.engine.*

@AppScope
@Component(modules = [NetworkModule::class, DatabaseModule::class, AppConfigModule::class, RepositoryModule::class])
interface AppComponent {
    fun getAppEngine(): ApplicationEngine

    fun getUnAuthorizedRequestComponent(): UnAuthorizedRequestComponent.Factory
    fun getAuthorizedRequestComponent(): AuthorizedRequestComponent.Factory
}