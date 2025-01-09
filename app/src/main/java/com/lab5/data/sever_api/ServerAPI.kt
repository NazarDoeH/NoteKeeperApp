package com.lab5.data.sever_api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerAPI
{
    // Метод для отримання статусу тривоги у вказаній області
    @GET("/v1/iot/active_air_raid_alerts/{uid}.json?token=b2f53a5173c46ab4987a0732b6e9b8424285c8b6ab2203")
    suspend fun getAirRaidStatus(
        @Path("uid") uid: Int // Унікальний ідентифікатор області
    ): String
}