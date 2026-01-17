package com.vs18.bundesliga.api

import com.vs18.bundesliga.model.*
import retrofit2.http.*

interface FootballService {

    @GET("fixtures")
    suspend fun getBundesligaResults(
        @Query("league") league: Int = 78,
        @Query("season") season: Int = 2023,
    ): FixtureResponse

    @GET("standings")
    suspend fun getBundesligaStandings(
        @Query("league") league: Int = 78,
        @Query("season") season: Int = 2023
    ): StandingsResponse
}