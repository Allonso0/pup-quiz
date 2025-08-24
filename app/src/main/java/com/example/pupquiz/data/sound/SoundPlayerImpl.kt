package com.example.pupquiz.data.sound

import com.example.pupquiz.R
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.domain.sound.SoundPlayer
import javax.inject.Inject

class SoundPlayerImpl @Inject constructor(
    private val soundManager: SoundManager,
    private val prefs: PreferencesManager
) : SoundPlayer {

    override fun playClickSound() {
        if (prefs.isSoundEnabled) {
            soundManager.playSound(R.raw.click_sound)
        }
    }

    override fun playSwitchSound() {
        if (prefs.isSoundEnabled) {
            soundManager.playSound(R.raw.switch_sound)
        }
    }

    override fun playGameOverSound() {
        if (prefs.isSoundEnabled) {
            soundManager.playSound(R.raw.game_over_sound)
        }
    }

    override fun isSoundEnabled(): Boolean {
        return prefs.isSoundEnabled
    }

    override fun setSoundEnabled(enabled: Boolean) {
        prefs.isSoundEnabled = enabled
    }
}