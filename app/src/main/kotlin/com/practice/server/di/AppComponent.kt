package com.practice.server.di

import com.practice.server.Database
import com.practice.server.di.modules.*
import com.practice.server.di.scopes.AppScope
import com.practice.server.di.subcomponent.request.AuthorizedRequestComponent
import com.practice.server.di.subcomponent.request.UnAuthorizedRequestComponent
import com.practice.server.di.subcomponent.request.modules.AppConfigModule
import dagger.BindsInstance
import dagger.Component
import io.ktor.server.engine.*

@AppScope
@Component(modules = [NetworkModule::class, DatabaseModule::class, AppConfigModule::class, RepositoryModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @DatabasePath databasePath: String,
            @BindsInstance @HostPort hostPort: Int,
            @BindsInstance @HostInterface hostInterface: String
        ): AppComponent
    }
    fun getAppEngine(): ApplicationEngine

    fun database(): Database

    fun getUnAuthorizedRequestComponent(): UnAuthorizedRequestComponent.Factory
    fun getAuthorizedRequestComponent(): AuthorizedRequestComponent.Factory
}