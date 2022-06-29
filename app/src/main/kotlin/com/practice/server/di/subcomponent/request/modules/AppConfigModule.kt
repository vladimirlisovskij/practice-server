package com.practice.server.di.subcomponent.request.modules

import com.practice.server.di.scopes.AppScope
import com.typesafe.config.ConfigFactory
import dagger.Module
import dagger.Provides
import io.ktor.server.config.*

@Module
object AppConfigModule {
    @AppScope
    @Provides
    fun provideConfig() = HoconApplicationConfig(ConfigFactory.load())
}