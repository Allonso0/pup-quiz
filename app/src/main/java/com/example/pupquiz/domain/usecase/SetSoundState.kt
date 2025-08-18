package com.example.pupquiz.domain.usecase

import com.example.pupquiz.domain.repository.SettingsRepository
import javax.inject.Inject

class SetSoundState @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(enabled: Boolean) = repository.setSoundState(enabled)
}