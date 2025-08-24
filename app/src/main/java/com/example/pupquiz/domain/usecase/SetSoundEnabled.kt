package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.sound.SoundPlayer
import javax.inject.Inject

class SetSoundEnabled @Inject constructor(
    private val soundPlayer: SoundPlayer
){
    operator fun invoke(enabled: Boolean) {
        soundPlayer.setSoundEnabled(enabled)
    }
}