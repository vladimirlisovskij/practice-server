package com.practice.server.di.modules

import com.practice.server.Database
import com.practice.server.di.scopes.AppScope
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {
    @AppScope
    @Provides
    fun provideDatabase() = Database(JdbcSqliteDriver("jdbc:sqlite:myServicesBd.sqlite3"))
}