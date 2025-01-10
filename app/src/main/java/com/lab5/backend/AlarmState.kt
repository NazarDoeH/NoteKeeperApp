package com.lab5.backend

enum class AlarmState(val id: Int) {
    Safe(0),
    PartiallySafe(1),
    Dangerous(2)
}

fun String.toAlarmState(): String {
    return when (this) {
        "A" -> "Небезпека"
        "P" -> "Часткова небезпека в регіонах"
        "N" -> "Безпечно"
        else -> "Немає інформації"
    }
}