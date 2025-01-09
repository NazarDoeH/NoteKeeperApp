package com.lab5.backend

import com.lab5.data.sever_api.ServerAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlertManager : KoinComponent {
    private val serverAPI: ServerAPI by inject()

    suspend fun checkAlert(uid: Int): String {
        return serverAPI.getAirRaidStatus(uid)
    }
}