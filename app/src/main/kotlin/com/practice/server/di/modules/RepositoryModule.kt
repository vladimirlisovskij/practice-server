package com.practice.server.di.modules

import com.practice.server.di.scopes.AppScope
import com.practice.server.infrastructure.repository.UserRepositoryImpl
import com.practice.server.di.scopes.RequestScope
import com.practice.server.domain.repository.ServiceRepository
import com.practice.server.domain.repository.UserRepository
import com.practice.server.infrastructure.repository.ServiceRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @AppScope
    @Binds
    abstract fun bindUserRepository(repo: UserRepositoryImpl): UserRepository

    @AppScope
    @Binds
    abstract fun bindServiceRepository(repo: ServiceRepositoryImpl): ServiceRepository
}

