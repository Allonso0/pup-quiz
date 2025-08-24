package com.example.pupquiz.data.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.pupquiz.R

class SoundManager(context: Context) {
    private val soundPool: SoundPool
    private val sounds = mutableMapOf<Int, Int>()

    init {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(attributes)
            .build()

        sounds[R.raw.click_sound] = soundPool.load(context, R.raw.click_sound, 1)
        sounds[R.raw.switch_sound] = soundPool.load(context, R.raw.switch_sound, 1)
        sounds[R.raw.game_over_sound] = soundPool.load(context, R.raw.game_over_sound, 1)
    }

    fun playSound(soundResId: Int) {
        val soundId = sounds[soundResId] ?: return
        soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    fun release() {
        soundPool.release()
    }
}