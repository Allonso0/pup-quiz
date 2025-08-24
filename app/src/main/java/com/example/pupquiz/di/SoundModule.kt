package com.example.pupquiz.di

import android.content.Context
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.data.sound.SoundManager
import com.example.pupquiz.data.sound.SoundPlayerImpl
import com.example.pupquiz.domain.sound.SoundPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoundModule {

    @Provides
    @Singleton
    fun provideSoundManager(@ApplicationContext context: Context): SoundManager {
        return SoundManager(context)
    }

    @Provides
    @Singleton
    fun provideSoundPlayer(
        soundManager: SoundManager,
        prefs: PreferencesManager
    ): SoundPlayer {
        return SoundPlayerImpl(soundManager, prefs)
    }
}