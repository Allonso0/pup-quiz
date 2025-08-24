package com.example.pupquiz

import android.app.Application
import com.example.pupquiz.data.sound.SoundManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PupQuizApplication : Application() {
    lateinit var soundManager: SoundManager

    override fun onCreate() {
        super.onCreate()
        soundManager = SoundManager(this)
    }
}