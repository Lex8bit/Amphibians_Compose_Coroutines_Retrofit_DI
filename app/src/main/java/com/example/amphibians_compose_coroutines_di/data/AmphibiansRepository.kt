package com.example.amphibians_compose_coroutines_di.data

import com.example.amphibians_compose_coroutines_di.network.Amphibians
import com.example.amphibians_compose_coroutines_di.network.AmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibiousInfo(): List<Amphibians>
}

class NetworkAmphibiousRepository(
    private val amphibiousApiService: AmphibiansApiService
) : AmphibiansRepository {
    override suspend fun getAmphibiousInfo(): List<Amphibians> = amphibiousApiService.getAmphibiousInfo()
}