package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.sound.SoundPlayer
import javax.inject.Inject

class PlayGameOverSound @Inject constructor(
    private val soundPlayer: SoundPlayer
) {
    operator fun invoke() {
        soundPlayer.playGameOverSound()
    }
}