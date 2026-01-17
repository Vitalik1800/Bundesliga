package com.vs18.bundesliga.model

data class League(
    val id: Int,
    val name: String,
    val season: Int,
    val standings: List<List<TeamStanding>>
)
