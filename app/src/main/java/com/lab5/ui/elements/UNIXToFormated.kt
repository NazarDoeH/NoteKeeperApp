package com.lab5.ui.elements

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun unixToFormattedTime(unixTime: Long): String {
    // Якщо ще не було оновлення то нема часу останнього оновлення
    if(unixTime <= 0) return "NaN"
    // Перетворюємо UNIX час на Instant
    val instant = Instant.ofEpochSecond(unixTime)

    // Форматуємо час в години та хвилини
    val formatter = DateTimeFormatter.ofPattern("HH:mm | dd-MM-yyyy")
        .withZone(ZoneId.systemDefault())

    // Отримуємо відформатовану строку
    return formatter.format(instant)
}