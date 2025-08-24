package com.example.pupquiz.domain.sound

interface SoundPlayer {
    fun playClickSound()
    fun playSwitchSound()
    fun playGameOverSound()
    fun isSoundEnabled(): Boolean
    fun setSoundEnabled(enabled: Boolean)
}