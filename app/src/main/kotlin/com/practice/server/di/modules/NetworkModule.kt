package com.practice.server.di.modules

import com.practice.server.di.scopes.AppScope
import com.practice.server.di.subcomponent.request.AuthorizedRequestComponent
import com.practice.server.di.subcomponent.request.UnAuthorizedRequestComponent
import com.practice.server.infrastructure.routing.Routing.configureRouting
import dagger.Module
import dagger.Provides
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HostInterface

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HostPort

@Module
object NetworkModule {
    @Provides
    @AppScope
    fun provideEngine(
        @HostInterface hostInterface: String,
        @HostPort hostPort: Int,
        unAuthorizedRequestComponentFactory: UnAuthorizedRequestComponent.Factory,
        authorizedRequestComponentFactory: AuthorizedRequestComponent.Factory
    ): ApplicationEngine {
        return embeddedServer(Netty, port = hostPort, host = hostInterface) {
            configureApplication()
            configureRouting(unAuthorizedRequestComponentFactory, authorizedRequestComponentFactory)
        }
    }

    private fun Application.configureApplication() {
        install(ContentNegotiation) {
            json()
        }
    }
}