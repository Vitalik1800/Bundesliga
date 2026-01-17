package com.vs18.bundesliga.repository

import com.vs18.bundesliga.api.FootballApiModule
import kotlinx.coroutines.*

class FootballRepository {

    suspend fun getAllResults() = withContext(Dispatchers.IO) {
        runCatching {
            FootballApiModule.service.getBundesligaResults().response
        }
    }

    suspend fun getStandings() = withContext(Dispatchers.IO) {
        runCatching {
            FootballApiModule.service.getBundesligaStandings()
        }
    }

}