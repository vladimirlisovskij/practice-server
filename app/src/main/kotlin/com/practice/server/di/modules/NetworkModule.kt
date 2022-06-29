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

@Module
object NetworkModule {
    @Provides
    @AppScope
    fun provideEngine(
        unAuthorizedRequestComponentFactory: UnAuthorizedRequestComponent.Factory,
        authorizedRequestComponentFactory: AuthorizedRequestComponent.Factory
    ): ApplicationEngine {
        return embeddedServer(Netty, port = 8080, host = "192.168.0.104") {
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