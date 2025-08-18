package com.example.pupquiz.di

import android.content.Context
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.data.repository.SettingsRepositoryImpl
import com.example.pupquiz.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesPreferencesManager(@ApplicationContext context: Context) : PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun providesSettingsRepository(prefs: PreferencesManager) : SettingsRepository {
        return SettingsRepositoryImpl(prefs)
    }
}