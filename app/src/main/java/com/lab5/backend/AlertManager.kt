package com.lab5.backend

import com.lab5.data.sever_api.ServerAPI

import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class AlertManager(private val serverAPI: ServerAPI) {
    suspend fun checkAlert(uid: Int): String {
        return serverAPI.getAirRaidStatus(uid)
    }

    fun startTimer(uid: Int, onStatusUpdate: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                delay(TimeUnit.SECONDS.toMillis(11)) // чекаємо 11 секунд
                try {
                    val alertStatus = checkAlert(uid)
                    withContext(Dispatchers.Main) {
                        onStatusUpdate(alertStatus) // Оновлюємо статус в UI
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        onStatusUpdate("Error: ${e.message}")
                    }
                }
            }
        }
    }
}

