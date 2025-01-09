package com.lab5.backend

enum class AlarmState(val id: Int) {
    Safe(0),
    PartiallySafe(1),
    Dangerous(2)
}

fun String.toAlarmState(): AlarmState {
    return when (this) {
        "A" -> AlarmState.Dangerous
        "P" -> AlarmState.PartiallySafe
        "N" -> AlarmState.Safe
        else -> AlarmState.Safe
    }
}