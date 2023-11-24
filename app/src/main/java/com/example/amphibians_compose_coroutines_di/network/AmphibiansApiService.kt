package com.example.amphibians_compose_coroutines_di.network

import retrofit2.http.GET

interface AmphibiansApiService {

    @GET("amphibians")
    suspend fun getAmphibiousInfo(): List<Amphibians>
}