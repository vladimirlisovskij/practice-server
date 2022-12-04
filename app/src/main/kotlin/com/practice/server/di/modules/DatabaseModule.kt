package com.practice.server.di.modules

import com.practice.server.Database
import com.practice.server.di.scopes.AppScope
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabasePath
@Module
object DatabaseModule {
    @AppScope
    @Provides
    fun provideDatabase(
        @DatabasePath databasePath: String
    ) = Database(JdbcSqliteDriver("jdbc:sqlite:$databasePath"))
}